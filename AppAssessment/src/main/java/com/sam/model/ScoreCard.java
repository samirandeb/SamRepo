package com.sam.model;

public class ScoreCard {

	public static final String OS = "OS";
	public static final String APP_SERVER = "AppServer";
	public static final String DB = "DB";
	public static final String PL = "PL";
	public static final String JMS = "JMS";
	public static final String PRESENTATION_LAYER = "PLF";
	public static final String JS = "JS";
	public static final String DATA_ACCESS = "DA";
	public static final String APPLICATION_FRAMEWORK = "AF";
	public static final String LOG = "LOG";

	private String applicationName;
	private String type;

	private double osScore;
	private double appServerScore;
	private double dbScore;
	private double plScore;
	private double jmsScore;
	private double presentationLayerScore;
	private double jsScore;
	private double dataAccessScore;
	private double applicationFrameworkScore;
	private double logScore;

	private double applicationScore;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getOsScore() {
		return osScore;
	}

	public void setOsScore(double osScore) {
		this.osScore = osScore;
	}

	public double getAppServerScore() {
		return appServerScore;
	}

	public void setAppServerScore(double appServerScore) {
		this.appServerScore = appServerScore;
	}

	public double getDbScore() {
		return dbScore;
	}

	public void setDbScore(double dbScore) {
		this.dbScore = dbScore;
	}

	public double getPlScore() {
		return plScore;
	}

	public void setPlScore(double plScore) {
		this.plScore = plScore;
	}

	public double getJmsScore() {
		return jmsScore;
	}

	public void setJmsScore(double jmsScore) {
		this.jmsScore = jmsScore;
	}

	public double getPresentationLayerScore() {
		return presentationLayerScore;
	}

	public void setPresentationLayerScore(double presentationLayerScore) {
		this.presentationLayerScore = presentationLayerScore;
	}

	public double getJsScore() {
		return jsScore;
	}

	public void setJsScore(double jsScore) {
		this.jsScore = jsScore;
	}

	public double getDataAccessScore() {
		return dataAccessScore;
	}

	public void setDataAccessScore(double dataAccessScore) {
		this.dataAccessScore = dataAccessScore;
	}

	public double getApplicationFrameworkScore() {
		return applicationFrameworkScore;
	}

	public void setApplicationFrameworkScore(double applicationFrameworkScore) {
		this.applicationFrameworkScore = applicationFrameworkScore;
	}

	public double getLogScore() {
		return logScore;
	}

	public void setLogScore(double logScore) {
		this.logScore = logScore;
	}

	public double getApplicationScore() {
		return applicationScore;
	}

	public void setApplicationScore(double applicationScore) {
		this.applicationScore = applicationScore;
	}

}
