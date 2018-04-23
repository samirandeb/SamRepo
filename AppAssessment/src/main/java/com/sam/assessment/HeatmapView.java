package com.sam.assessment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations = "classpath:scoreCardQuestion.yml", prefix = "heatmapQuestions")
public class HeatmapView {

	private List<Long> javaQuestions;
	private List<Long> dbQuestions;
	private List<Long> msQuestions;
	private List<Long> mwQuestions;
	
	private List<Long> allQuestions;
	
	public List<Long> getJavaQuestions() {
		return javaQuestions;
	}

	public void setJavaQuestions(List<Long> javaQuestions) {
		this.javaQuestions = javaQuestions;
	}

	public List<Long> getAllQuestions() {
		return allQuestions;
	}

	public void setAllQuestions(List<Long> allQuestions) {
		this.allQuestions = allQuestions;
	}
	
	
	public List<Long> getDbQuestions() {
		return dbQuestions;
	}

	public void setDbQuestions(List<Long> dbQuestions) {
		this.dbQuestions = dbQuestions;
	}

	public List<Long> getMsQuestions() {
		return msQuestions;
	}

	public void setMsQuestions(List<Long> msQuestions) {
		this.msQuestions = msQuestions;
	}

	public List<Long> getMwQuestions() {
		return mwQuestions;
	}

	public void setMwQuestions(List<Long> mwQuestions) {
		this.mwQuestions = mwQuestions;
	}

	@PostConstruct
	void setUp() {
		allQuestions = new ArrayList<Long>();
		if (javaQuestions != null) {
			allQuestions.addAll(javaQuestions);
		}

		if (dbQuestions != null) {
			allQuestions.addAll(dbQuestions);
		}
		if (msQuestions != null) {
			allQuestions.addAll(msQuestions);
		}
		if (mwQuestions != null) {
			allQuestions.addAll(mwQuestions);
		}
	}
}
