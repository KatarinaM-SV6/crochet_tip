package com.ftn.sbnz.service.tests;

import org.drools.core.base.RuleNameEqualsAgendaFilter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import com.ftn.sbnz.model.utils.KnowledgeSessionHelper;
import com.ftn.sbnz.service.model.StatsDTO;
import com.ftn.sbnz.model.models.CrochetPeriod;
import com.ftn.sbnz.model.models.CrochetType;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.Pattern;
import com.ftn.sbnz.model.models.Stat;
import com.ftn.sbnz.model.models.Difficulty;
import com.ftn.sbnz.model.models.Yarn;
import com.ftn.sbnz.model.models.Color;
import com.ftn.sbnz.model.models.YarnType;
import com.ftn.sbnz.model.models.WoolSize;
import java.time.Duration;
import java.time.LocalDate;


public class AccumulateTest {

    protected final String ksessionName = "fwKsession";

    @Test
    public void testInsertModifyAndDelete() {
    	KieContainer kc = KnowledgeSessionHelper.createRuleBase();
        KieSession ksession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, ksessionName);
        
        
        Korisnik user = new Korisnik(1, "user1", "email", "password");
        ksession.insert(user);
        CrochetPeriod period1 = new CrochetPeriod(Duration.ofMinutes(20), LocalDate.now().minusDays(5)); 
        System.out.println("first " + LocalDate.now().minusDays(1));
        Duration d = Duration.ZERO;
        d = d.plus(period1.duration);
        System.out.println("second " + d.toMinutes());

        CrochetPeriod period2 = new CrochetPeriod(Duration.ofMinutes(2), LocalDate.now()); 
        
        ksession.insert(period1);
        ksession.insert(period2);
        //so the rule should fire for only one of the orders
        int firedRules = ksession.fireAllRules(new RuleNameEqualsAgendaFilter("Find hours crocheted past month"));
        
    
        // MatcherAssert.assertThat(order2.getDiscount().getPercentage(), notNullValue());
        // MatcherAssert.assertThat(order2.getDiscount().getPercentage(), equalTo(0.05));
    }
    
    @Test
    public void testPatternsFinished() {
    	KieContainer kc = KnowledgeSessionHelper.createRuleBase();
        KieSession ksession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, ksessionName);
        
        CrochetType ctRoot = new CrochetType("Crochet", null, false);
        CrochetType ct11 = new CrochetType("Amigurumi", "Crochet", false);
        CrochetType ct12 = new CrochetType("Clothes", "Crochet", false);
        CrochetType ct21 = new CrochetType("Animals", "Amigurumi", false);
        CrochetType ct22 = new CrochetType("WinterClothes", "Clothes", true);

        
        Pattern p1 = new Pattern(
            1, 
            "Ducky",
            Difficulty.BEGGINER, 
            6.0, 
            Arrays.asList(
                new Yarn(YarnType.ACRYLLIC, WoolSize.BULKY, 100, Color.YELLOW), 
                new Yarn(YarnType.ACRYLLIC, WoolSize.BULKY, 50, Color.ORANGE)
                ), 
            "image", 
            new HashSet<>(Arrays.asList("sewing needle", "scissors")),
            true,
            ct21
        );

        Pattern p2 = new Pattern(
            1, 
            "Shrek Shirt",
            Difficulty.ADVANCED, 
            6.0, 
            Arrays.asList(
                new Yarn(YarnType.ACRYLLIC, WoolSize.BULKY, 200, Color.GREEN_DARK), 
                new Yarn(YarnType.ACRYLLIC, WoolSize.BULKY, 100, Color.GREEN_LIGHT),
                new Yarn(YarnType.COTTON, WoolSize.BULKY, 100, Color.BROWN)
                ), 
            "image", 
            new HashSet<>(Arrays.asList("sewing needle", "scissors")),
            false,
            ct22
        );

        ksession.insert(p1);
        ksession.insert(p2);
        //so the rule should fire for only one of the orders
        int firedRules = ksession.fireAllRules(new RuleNameEqualsAgendaFilter("Find amount of finished patterns"));

        Collection<?> stats = ksession.getObjects(new ClassObjectFilter(Stat.class));
        for (Object obj : stats) {
            System.out.println(obj);
        }
        
    
        // MatcherAssert.assertThat(order2.getDiscount().getPercentage(), notNullValue());
        // MatcherAssert.assertThat(order2.getDiscount().getPercentage(), equalTo(0.05));
    }

}
