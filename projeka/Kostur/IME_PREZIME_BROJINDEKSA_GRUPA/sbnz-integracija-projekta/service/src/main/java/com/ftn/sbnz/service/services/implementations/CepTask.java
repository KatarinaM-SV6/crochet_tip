package com.ftn.sbnz.service.services.implementations;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.ftn.sbnz.model.events.Notification;
import com.ftn.sbnz.model.events.TimeEvent;

public class CepTask implements Runnable {
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private KieSession kieSession;
    private Integer userId;
    private SimpMessagingTemplate simpMessagingTemplate;

    public CepTask(KieSession kieSession, Integer userId, SimpMessagingTemplate simpMessagingTemplate) {
        this.kieSession = kieSession;
        this.userId = userId;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void run() {
        if (isRunning.get()) {
            Agenda agenda = kieSession.getAgenda();
            agenda.getAgendaGroup("cep-group").setFocus();
            Integer rulesFired = kieSession.fireAllRules();
    
            if (rulesFired != 0) {
                Collection<?> notifications = kieSession.getObjects(new ClassObjectFilter(Notification.class));
                Optional<Notification> latestNotification = notifications.stream()
                    .filter(obj -> obj instanceof Notification)
                    .map(obj -> (Notification) obj)
                    .max((n1, n2) -> n1.getExecutionTime().compareTo(n2.getExecutionTime()));
    
                // Return the latest notification or null if no notifications are found
                Notification notification = latestNotification.orElse(null);
                simpMessagingTemplate.convertAndSend("/topic/" + userId, notification.getNotificationText());

            }
        }
    }

    public void start() {
        isRunning.set(true);
        System.out.println("STARTED");
    }

    public void stop() {
        isRunning.set(false);
        Collection<?> notifications = kieSession.getObjects(new ClassObjectFilter(Notification.class));
        for (Object obj : notifications) {
            FactHandle handle = kieSession.getFactHandle(obj);
            if (handle != null) {
                kieSession.delete(handle);
            }
        }
        System.out.println("Stopped and deleted all notifications from KieSession.");
    }

    public boolean isRunning() {
        return isRunning.get();
    }
}
