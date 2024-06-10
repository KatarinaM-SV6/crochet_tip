package com.ftn.sbnz.service.services.implementations;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.TimeEvent;
import com.ftn.sbnz.model.models.ActionType;
import com.ftn.sbnz.model.models.CrochetPeriod;
import com.ftn.sbnz.model.models.CrochetType;
import com.ftn.sbnz.model.models.Difficulty;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.Pattern;
import com.ftn.sbnz.model.models.Preference;
import com.ftn.sbnz.model.models.Recommendation;
import com.ftn.sbnz.model.models.Stat;
import com.ftn.sbnz.model.utils.KnowledgeSessionHelper;
import com.ftn.sbnz.service.model.StatsDTO;
import com.ftn.sbnz.service.utils.PatternUtils;

@Service
public class KieSessionService {

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private PatternService patternService;

    @Autowired
    private PatternUtils patternUtils;

    private  String ksessionName = "mainKsession";

    private CepTask cepTask;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private  ConcurrentHashMap<Integer, KieSession> userSessions = new ConcurrentHashMap<>();

    public void createUserSession(Korisnik user) {
        if (userSessions.containsKey(user.getId())) {
            // Return the existing session
            return;
        }

        KieSession kieSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, ksessionName);
        List<CrochetType> crochetTypes = patternUtils.getCrochetTypes();
		List<Pattern> patterns = patternService.loadAllPatterns();

        crochetTypes.forEach((type) -> kieSession.insert(type));
		patterns.forEach((pattern) -> kieSession.insert(pattern));
        
        userSessions.put(user.getId(), kieSession);
        return;
    }

    public KieSession getUserSession(Integer userId) {
        return userSessions.get(userId);
    }

    public TimeEvent startCrocheting(Integer userId) {
		KieSession kieSession = getUserSession(userId); 
        TimeEvent e1 = new TimeEvent(userId, ActionType.START);
        kieSession.insert(e1);
        Collection<?> timeEvents = kieSession.getObjects(new ClassObjectFilter(TimeEvent.class));
        
        if (cepTask == null) {
            this.cepTask = new CepTask(kieSession, userId);
            this.cepTask.start();
            scheduler.scheduleAtFixedRate(cepTask, 0,1, TimeUnit.SECONDS);
        } else if (!cepTask.isRunning()){
            this.cepTask.start();
            scheduler.scheduleAtFixedRate(cepTask, 0,1, TimeUnit.SECONDS);
        }

        if (!timeEvents.isEmpty()) {
            return (TimeEvent) timeEvents.iterator().next();
        }

        return null;
    }

    public CrochetPeriod stopCrocheting(Integer userId) {
		KieSession kieSession = getUserSession(userId); 
        TimeEvent e1 = new TimeEvent(userId, ActionType.STOP);
        kieSession.insert(e1);
        kieSession.fireAllRules(new RuleNameEqualsAgendaFilter("Time Difference Between Stop and Start"));
        Collection<?> timeEvents = kieSession.getObjects(new ClassObjectFilter(CrochetPeriod.class));
        cepTask.stop();

        if (!timeEvents.isEmpty()) {
            return (CrochetPeriod) timeEvents.iterator().next();
        }

        return null;
    }

    public Recommendation findRecommendation(Integer userId) {
        KieSession kieSession = getUserSession(userId);
        Agenda agenda = kieSession.getAgenda();
        agenda.getAgendaGroup("rec-group").setFocus();
        kieSession.fireAllRules();

        Collection<?> recommendations = kieSession.getObjects(new ClassObjectFilter(Recommendation.class));
        if (recommendations.isEmpty()) {
            return null; // Or throw an exception or handle it as per your requirement
        }

        return (Recommendation) recommendations.iterator().next();
    }

    public Recommendation getRecommendation(Integer userId, String difficulty) {
		KieSession kieSession = getUserSession(userId);
        kieSession.insert(new Preference(Difficulty.valueOf(difficulty)));

        return findRecommendation(userId);
    }

    public Recommendation rejectRecommendation(Integer userId, Recommendation rec) {
		KieSession kieSession = getUserSession(userId);
        Collection<?> recommendations = kieSession.getObjects(new ClassObjectFilter(Recommendation.class));
        Recommendation foundRec = null;
        FactHandle factHandle = null;
        for (Object obj : recommendations) {
            if (obj.equals(rec)) { // Ensure this check matches your Recommendation's equals method
                foundRec = (Recommendation) obj;
                factHandle = kieSession.getFactHandle(foundRec);
                break;
            }
        }

        rec.setAccepted(false);

        kieSession.update(factHandle, rec);

        return findRecommendation(userId);
    }

    public Recommendation acceptRecommendation(Integer userId, Recommendation rec) {
		KieSession kieSession = getUserSession(userId);

        
        Collection<?> recommendations = kieSession.getObjects(new ClassObjectFilter(Recommendation.class));
        if (recommendations.isEmpty()) {
            return null; // Or throw an exception or handle it as per your requirement
        }

        for (Object obj : recommendations) {
            FactHandle handle = kieSession.getFactHandle(obj);
            if (handle != null) {
                kieSession.delete(handle);
            }
        }

        Collection<?> preferences = kieSession.getObjects(new ClassObjectFilter(Preference.class));
        if (preferences.isEmpty()) {
            return null; // Or throw an exception or handle it as per your requirement
        }

        for (Object obj : preferences) {
            FactHandle handle = kieSession.getFactHandle(obj);
            if (handle != null) {
                kieSession.delete(handle);
            }
        }

        return rec;
    }

    public StatsDTO getStats(Integer userId) {
        createCrochetPeriodsForUser(userId);
		KieSession kieSession = getUserSession(userId); 
        kieSession.fireAllRules();

        Collection<?> stats = kieSession.getObjects(new ClassObjectFilter(Stat.class));
        StatsDTO dto = new StatsDTO();

        for (Object obj : stats) {
            System.out.println(obj);
            Stat stat = (Stat) obj;
            switch (stat.getName()) {
                case "daily":
                    dto.setDaily(stat.getValue());
                    break;
                case "weekly":
                    dto.setWeekly(stat.getValue());
                    break;
                case "monthly":
                    dto.setMonthly(stat.getValue());
                    break;
                case "done":
                    dto.setDone(stat.getValue());
                    break;
                case "n":
                    dto.setN(stat.getValue());
                    break;
                default:
                    break;
            }
            FactHandle handle = kieSession.getFactHandle(obj);
            if (handle != null) {
                kieSession.delete(handle);
            }
        }

        return dto;
    }

    public void createCrochetPeriodsForUser(Integer userId) {
		KieSession kieSession = getUserSession(userId); 

        CrochetPeriod cp1 = new CrochetPeriod(Duration.ofMinutes(30), LocalDate.of(2024, 6, 1));
        CrochetPeriod cp2 = new CrochetPeriod(Duration.ofMinutes(30), LocalDate.of(2024, 5, 15));
        CrochetPeriod cp3 = new CrochetPeriod(Duration.ofMinutes(30), LocalDate.of(2024, 6, 10));
        
        kieSession.insert(cp1);
        kieSession.insert(cp2);
        kieSession.insert(cp3);
    }
}
