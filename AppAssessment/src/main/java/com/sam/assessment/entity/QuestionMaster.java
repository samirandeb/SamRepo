package com.sam.assessment.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "question_master")
public class QuestionMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String appType;
	private String infoType;
	private String question;
	private String questionHint;
	private String respType;
	@Column(length = 20000)
	private String respDropdownValue;
	private String versionType;
	@Column(length = 1000)
	private String versionDropdownValue;
	@Column(length = 1000)
	private Long iteration;

	@Transient
	private String response;


	@Transient
	private Map<String,String> valueMap = new HashMap<>();

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionHint() {
		return questionHint;
	}

	public void setQuestionHint(String questionHint) {
		this.questionHint = questionHint;
	}

	public String getRespDropdownValue() {
		return respDropdownValue;
	}

	public void setRespDropdownValue(String respDropdownValue) {
		this.respDropdownValue = respDropdownValue;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	public String getVersionDropdownValue() {
		return versionDropdownValue;
	}

	public void setVersionDropdownValue(String versionDropdownValue) {
		this.versionDropdownValue = versionDropdownValue;
	}


	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getRespType() {
		return respType;
	}

	public void setRespType(String respType) {
		this.respType = respType;
	}

	public Long getId() {
		return id;
	}

	public Long getIteration() {
		return iteration;
	}

	public void setIteration(Long iteration) {
		this.iteration = iteration;
	}

	public Map<String, String> getValueMap() {
		return valueMap;
	}

	public void updateValueMap(){
		valueMap = new LinkedHashMap<>();
		List<String> ddValues = respDropdownValue!=null ? Arrays.asList(respDropdownValue.split(";")):new ArrayList<String>();
		for(String key : ddValues){
			valueMap.put(key, "");
		}
		if(versionDropdownValue!=null && !"".equalsIgnoreCase(versionDropdownValue)){

			String[] optionArray = versionDropdownValue.split(";");
			for(String option : optionArray){
				String[] mapStrings = option.split("~");
				valueMap.put(mapStrings[0], mapStrings[1]);
			}
		}
	}
}
