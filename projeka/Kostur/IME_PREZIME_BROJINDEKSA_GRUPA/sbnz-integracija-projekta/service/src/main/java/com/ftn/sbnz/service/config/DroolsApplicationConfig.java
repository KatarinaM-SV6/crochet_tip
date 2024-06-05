package com.ftn.sbnz.service.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ftn.sbnz.model.utils.KnowledgeSessionHelper;

@Configuration
public class DroolsApplicationConfig {

  // private static final KieServices kieServices = KieServices.Factory.get();
  // private static final String RULES_CUSTOMER_RULES_DRL = "rules/loan_rate.drl";
  protected final String ksessionName = "fwKsession";

  @Bean
  public KieContainer kieContainer() {
    KieContainer kieContainer = KnowledgeSessionHelper.createRuleBase();
    return kieContainer;
  }

  @Bean
  public KieSession kieSession(KieContainer kieContainer) {
    KieSession ksession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, ksessionName);
    return ksession;
  }
}