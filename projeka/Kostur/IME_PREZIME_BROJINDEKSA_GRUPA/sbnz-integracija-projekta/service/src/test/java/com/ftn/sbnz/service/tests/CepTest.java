package com.ftn.sbnz.service.tests;

import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.drools.core.time.SessionPseudoClock;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.ftn.sbnz.model.events.TimeEvent;
import com.ftn.sbnz.model.models.ActionType;
import com.ftn.sbnz.model.utils.KnowledgeSessionHelper;

public class CepTest {

    protected final String ksessionName = "cepKsession";

    @Test
    public void testInsertCrochetPeriod() {
    	KieContainer kc = KnowledgeSessionHelper.createRuleBase();
        KieSession ksession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, ksessionName);
        SessionPseudoClock clock = ksession.getSessionClock();
        
        TimeEvent e1 = new TimeEvent(1, ActionType.START);
        ksession.insert(e1);

        clock.advanceTime(20, TimeUnit.MINUTES);
        //so the rule should fire for only one of the orders

        TimeEvent e2 = new TimeEvent(1, ActionType.STOP);
        e2.setExecutionTime(new Date(new Date().getTime() + 20L*60*1000));
        ksession.insert(e2);

        int firedRules = ksession.fireAllRules(new RuleNameEqualsAgendaFilter("Take a break"));
        System.out.println(firedRules);
    
        // MatcherAssert.assertThat(order2.getDiscount().getPercentage(), notNullValue());
        // MatcherAssert.assertThat(order2.getDiscount().getPercentage(), equalTo(0.05));
    }

    @Test
    public void testFirstBreak() {
    	KieContainer kc = KnowledgeSessionHelper.createRuleBase();
        KieSession ksession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, ksessionName);
        SessionPseudoClock clock = ksession.getSessionClock();
        
        TimeEvent e1 = new TimeEvent(1, ActionType.START);
        ksession.insert(e1);

        clock.advanceTime(1, TimeUnit.HOURS);
        //so the rule should fire for only one of the orders

        // TimeEvent e2 = new TimeEvent(1, ActionType.STOP);
        // e2.setExecutionTime(new Date(new Date().getTime() + 1*60*60*1000));
        // ksession.insert(e2);

        int firedRules = ksession.fireAllRules(new RuleNameEqualsAgendaFilter("Take a break"));
        System.out.println(firedRules);
    
        // MatcherAssert.assertThat(order2.getDiscount().getPercentage(), notNullValue());
        MatcherAssert.assertThat(firedRules, equalTo(0));

        clock.advanceTime(2, TimeUnit.HOURS);
        int firedRules2 = ksession.fireAllRules(new RuleNameEqualsAgendaFilter("Take a break"));
    
        // MatcherAssert.assertThat(order2.getDiscount().getPercentage(), notNullValue());
        MatcherAssert.assertThat(firedRules2, equalTo(1));
    }

    @Test
    public void testFirstBreakFalse() {
    	KieContainer kc = KnowledgeSessionHelper.createRuleBase();
        KieSession ksession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, ksessionName);
        SessionPseudoClock clock = ksession.getSessionClock();
        
        TimeEvent e1 = new TimeEvent(1, ActionType.START);
        ksession.insert(e1);

        clock.advanceTime(4, TimeUnit.HOURS);

        int firedRules = ksession.fireAllRules(new RuleNameEqualsAgendaFilter("Take a break"));
        System.out.println(firedRules);
    
        // MatcherAssert.assertThat(order2.getDiscount().getPercentage(), notNullValue());
        MatcherAssert.assertThat(firedRules, equalTo(1));


        int firedRules2 = ksession.fireAllRules(new RuleNameEqualsAgendaFilter("Take a break"));
    
        MatcherAssert.assertThat(firedRules2, equalTo(0));
    }

    @Test
    public void testSecondBreak() {
    	KieContainer kc = KnowledgeSessionHelper.createRuleBase();
        KieSession ksession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, ksessionName);
        SessionPseudoClock clock = ksession.getSessionClock();
        
        TimeEvent e1 = new TimeEvent(1, ActionType.START);
        ksession.insert(e1);

        clock.advanceTime(2, TimeUnit.HOURS);
        clock.advanceTime(2, TimeUnit.MINUTES);

        int firedRules = ksession.fireAllRules();
        System.out.println(firedRules);
    
        MatcherAssert.assertThat(firedRules, equalTo(1));

        clock.advanceTime(1, TimeUnit.HOURS);

        int firedRules2 = ksession.fireAllRules();
    
        MatcherAssert.assertThat(firedRules2, equalTo(1));
    }
}
