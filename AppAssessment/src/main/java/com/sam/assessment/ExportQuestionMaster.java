package com.sam.assessment;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations = "classpath:scoreCardQuestion.yml", prefix = "exportQuestionMaster")
public class ExportQuestionMaster {
	
	private List<Long> questions;

	public List<Long> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Long> questions) {
		this.questions = questions;
	}
	
	

}
