package com.ftn.sbnz.service.tests;

import java.util.Arrays;

import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.ftn.sbnz.model.models.Color;
import com.ftn.sbnz.model.models.CrochetType;
import com.ftn.sbnz.model.models.Difficulty;
import com.ftn.sbnz.model.models.Pattern;
import com.ftn.sbnz.model.models.Preference;
import com.ftn.sbnz.model.models.Recommendation;
import com.ftn.sbnz.model.models.WoolSize;
import com.ftn.sbnz.model.models.Yarn;
import com.ftn.sbnz.model.models.YarnType;
import com.ftn.sbnz.model.utils.KnowledgeSessionHelper;

public class BackwardTest {

    protected final String ksessionName = "bwKsession";

    @Test
    public void testInsertCrochetPeriod() {
    	KieContainer kc = KnowledgeSessionHelper.createRuleBase();
        KieSession ksession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, ksessionName);
        
        CrochetType typeRoot = new CrochetType("Crochet", "", false);
        CrochetType type1 = new CrochetType("Amigurumi", "Crochet", true);
        CrochetType type2 = new CrochetType("Micro crochet", "Crochet", false);
        CrochetType type3 = new CrochetType("Animals", "Amigurumi", false);
        CrochetType type4 = new CrochetType("Toys", "Amigurumi", true);
        
        ksession.insert(typeRoot);
        ksession.insert(type2);
        ksession.insert(type1);
        ksession.insert(type3);
        ksession.insert(type4);

        int firedRules = ksession.fireAllRules();
        System.out.println(firedRules);
    
        // MatcherAssert.assertThat(order2.getDiscount().getPercentage(), notNullValue());
        // MatcherAssert.assertThat(order2.getDiscount().getPercentage(), equalTo(0.05));
    }

    @Test
    public void testChainForwardAndBackward() {
    	KieContainer kc = KnowledgeSessionHelper.createRuleBase();
        KieSession ksession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, ksessionName);

        CrochetType ctRoot = new CrochetType("Crochet", "", false);
        CrochetType ct11 = new CrochetType("Amigurumi", "Crochet", false);
        CrochetType ct12 = new CrochetType("Clothes", "Crochet", false);
        CrochetType ct21 = new CrochetType("Animals", "Amigurumi", false);
        CrochetType ct22 = new CrochetType("WinterClothes", "Clothes", false);

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
            Arrays.asList("sewing needle", "scissors"),
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
            Arrays.asList("sewing needle", "scissors"),
            false,
            ct22
        );

        ksession.insert(ctRoot);
        ksession.insert(ct11);
        ksession.insert(ct12);
        ksession.insert(ct21);
        ksession.insert(ct22);
        ksession.insert(p1);
        ksession.insert(p2);

        Preference preference = new Preference(Difficulty.ADVANCED, ct22);
        Recommendation rec = new Recommendation(p2);
        System.out.println(rec);
        ksession.insert(preference);

        int firedRules = ksession.fireAllRules();
        System.out.println(firedRules);
    }

    @Test
    public void testFindRec() {

    }
}

