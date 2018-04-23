package com.sam.assessment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations = "classpath:scoreCardQuestion.yml", prefix = "scoreCardQuestion")
public class ScoreCardQuestionMaster {

	private List<Long> plQuestions;
	private List<Long> dbQuestions;
	private List<Long> serverQuestions;
	private List<Long> osQuestions;
	private List<Long> jmsQuestions;
	private List<Long> presentationLayerQuestions;
	private List<Long> jsQuestions;
	private List<Long> dataAccessQuestions;
	private List<Long> applicationFrameworkQuestions;
	private List<Long> loggingQuestions;
	private List<Long> booleanQuestions;

	public List<Long> getBooleanQuestions() {
		return booleanQuestions;
	}

	public void setBooleanQuestions(List<Long> booleanQuestions) {
		this.booleanQuestions = booleanQuestions;
	}

	private List<Long> allQuestions;

	public List<Long> getPlQuestions() {
		return plQuestions;
	}

	public void setPlQuestions(List<Long> plQuestions) {
		this.plQuestions = plQuestions;
	}

	public List<Long> getDbQuestions() {
		return dbQuestions;
	}

	public void setDbQuestions(List<Long> dbQuestions) {
		this.dbQuestions = dbQuestions;
	}

	public List<Long> getServerQuestions() {
		return serverQuestions;
	}

	public void setServerQuestions(List<Long> serverQuestions) {
		this.serverQuestions = serverQuestions;
	}

	public List<Long> getOsQuestions() {
		return osQuestions;
	}

	public void setOsQuestions(List<Long> osQuestions) {
		this.osQuestions = osQuestions;
	}

	public List<Long> getJmsQuestions() {
		return jmsQuestions;
	}

	public void setJmsQuestions(List<Long> jmsQuestions) {
		this.jmsQuestions = jmsQuestions;
	}

	public List<Long> getPresentationLayerQuestions() {
		return presentationLayerQuestions;
	}

	public void setPresentationLayerQuestions(List<Long> presentationLayerQuestions) {
		this.presentationLayerQuestions = presentationLayerQuestions;
	}

	public List<Long> getJsQuestions() {
		return jsQuestions;
	}

	public void setJsQuestions(List<Long> jsQuestions) {
		this.jsQuestions = jsQuestions;
	}

	public List<Long> getDataAccessQuestions() {
		return dataAccessQuestions;
	}

	public void setDataAccessQuestions(List<Long> dataAccessQuestions) {
		this.dataAccessQuestions = dataAccessQuestions;
	}

	public List<Long> getApplicationFrameworkQuestions() {
		return applicationFrameworkQuestions;
	}

	public void setApplicationFrameworkQuestions(List<Long> applicationFrameworkQuestions) {
		this.applicationFrameworkQuestions = applicationFrameworkQuestions;
	}

	public List<Long> getLoggingQuestions() {
		return loggingQuestions;
	}

	public void setLoggingQuestions(List<Long> loggingQuestions) {
		this.loggingQuestions = loggingQuestions;
	}

	public List<Long> getAllQuestions() {
		return allQuestions;
	}

	public void setAllQuestions(List<Long> allQuestions) {
		this.allQuestions = allQuestions;
	}

	@PostConstruct
	void setUp() {
		allQuestions = new ArrayList<Long>();
		if (plQuestions != null) {
			allQuestions.addAll(plQuestions);
		}

		if (dbQuestions != null) {
			allQuestions.addAll(dbQuestions);
		}

		if (serverQuestions != null) {
			allQuestions.addAll(serverQuestions);
		}

		if (osQuestions != null) {
			allQuestions.addAll(osQuestions);
		}

		if (jmsQuestions != null) {
			allQuestions.addAll(jmsQuestions);
		}

		if (presentationLayerQuestions != null) {
			allQuestions.addAll(presentationLayerQuestions);
		}

		if (jsQuestions != null) {
			allQuestions.addAll(jsQuestions);
		}

		if (dataAccessQuestions != null) {
			allQuestions.addAll(dataAccessQuestions);
		}

		if (applicationFrameworkQuestions != null) {
			allQuestions.addAll(applicationFrameworkQuestions);
		}

		if (loggingQuestions != null) {
			allQuestions.addAll(loggingQuestions);
		}

		System.out.println("Size of all question : " + allQuestions.size());
	}

}
