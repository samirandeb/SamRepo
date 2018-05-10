package com.sam.drools.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sam.drools.model.ApplicationForm;

@Service
public class RuleService {
	
	@Autowired
	private KieContainer kieContainer;

	public ApplicationForm apply(ApplicationForm applicationForm) {
		//get the stateful session
		KieSession kieSession = kieContainer.newKieSession("rulesSession");
		kieSession.insert(applicationForm);
		kieSession.fireAllRules();
		kieSession.dispose();
		return applicationForm;
	}

}
