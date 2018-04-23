package com.sam.model;

import java.util.ArrayList;
import java.util.List;

import com.sam.assessment.entity.AssessmentResponse;
import com.sam.assessment.entity.QuestionMaster;

public class ApplicationAssessment {
	private String applicationName;
	private List<QuestionGroup> questionGruopList = new ArrayList<>();

	public String getApplicationName() {
		return applicationName;
	}


	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}


	public void updateAssessmentByQuestion(List<QuestionMaster> questions){
		
		if(questions!=null){
			questionGruopList = new ArrayList<QuestionGroup>();
			for (QuestionMaster question : questions) {
				
				QuestionGroup qg = new QuestionGroup();
				qg.setGroupName(question.getInfoType());
				if(!questionGruopList.contains(qg)){
					questionGruopList.add(qg);
				}
				qg = questionGruopList.get(questionGruopList.indexOf(qg));
				AssessmentResponse response = new AssessmentResponse();
				response.setQuestion(question);
				response.setQuestionId(question.getId());
				qg.getResponses().add(response);
			}
			
			
		}
	}
	
	public void updateAssessmentByResponse(List<AssessmentResponse> responses){
		
		if(responses!=null){
			questionGruopList = new ArrayList<QuestionGroup>();
			for (AssessmentResponse response : responses) {
				QuestionGroup qg = new QuestionGroup();
				qg.setGroupName(response.getQuestion().getInfoType());
				if(!questionGruopList.contains(qg)){
					questionGruopList.add(qg);
				}
				qg = questionGruopList.get(questionGruopList.indexOf(qg));
				qg.getResponses().add(response);
			}
			
			
		}
	}

	public List<QuestionGroup> getQuestionGruopList() {
		return questionGruopList;
	}

	public void setQuestionGruopList(List<QuestionGroup> questionGruopList) {
		this.questionGruopList = questionGruopList;
	}
}