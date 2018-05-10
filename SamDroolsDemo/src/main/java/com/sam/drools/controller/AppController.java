package com.sam.drools.controller;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sam.drools.model.ApplicationForm;
import com.sam.drools.service.RuleService;

@RestController
public class AppController {
	
	@Autowired
	private RuleService ruleService;
	
	@RequestMapping(value = "/cardApply", method = RequestMethod.POST, produces = "application/json")
	public ApplicationForm getQuestions(@RequestBody(required = true) ApplicationForm appForm) {
		//System.out.println("Input data :"+appForm.getName()+appForm.getAge()+appForm.getSalary());
		Integer crdScore = new Random().nextInt(900-600)+600;
		appForm.setCreditScore(crdScore);
		ruleService.apply(appForm);
		appForm.setAppNumber(UUID.randomUUID().toString());
		return appForm;
	}

}
