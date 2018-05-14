package com.cts.migration.model;

public class RuleComparison {
	
	private String ruleName;
	private String ruleGuid;
	private String ruleType;
	
	private String destinationName;
	private String destinationXml;
	private String destinationVersion;
	
	private String sourceName;
	private String sourceXml;
	private String sourceVersion;
	
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleGuid() {
		return ruleGuid;
	}
	public void setRuleGuid(String ruleGuid) {
		this.ruleGuid = ruleGuid;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getDestinationXml() {
		return destinationXml;
	}
	public void setDestinationXml(String destinationXml) {
		this.destinationXml = destinationXml;
	}
	public String getDestinationVersion() {
		return destinationVersion;
	}
	public void setDestinationVersion(String destinationVersion) {
		this.destinationVersion = destinationVersion;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getSourceXml() {
		return sourceXml;
	}
	public void setSourceXml(String sourceXml) {
		this.sourceXml = sourceXml;
	}
	public String getSourceVersion() {
		return sourceVersion;
	}
	public void setSourceVersion(String sourceVersion) {
		this.sourceVersion = sourceVersion;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	
	
}
