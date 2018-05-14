package com.cts.migration.model.ui;

public class OipaRule {

	private String ruleType;
	private String ruleName;
	private String ruleGuid;
	private String versionGuid;
	private String ivsVersion;
	private String updatedBy;
	private String updatedOn;

	//more information
	private String highestVersion;
	private String note;
	
	// over ride information
	private String companyGuid;
	private String companyName;
	private String planGuid;
	private String planName;
	private String transactionGuid;

	private String transactionName;
	private String clientGuid;
	private String clientName;
	private String policyGuid;
	private String policyNumber;
	private String segmentGuid;
	private String segmentName;
	private String activityGuid;

	public OipaRule(String ruleGuid) {
		this.ruleGuid = ruleGuid;
	}

	public OipaRule() {
		// this.ruleGuid = ruleGuid;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

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

	public String getVersionGuid() {
		return versionGuid;
	}

	public void setVersionGuid(String versionGuid) {
		this.versionGuid = versionGuid;
	}

	public String getCompanyGuid() {
		return companyGuid;
	}

	public void setCompanyGuid(String companyGuid) {
		this.companyGuid = companyGuid;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPlanGuid() {
		return planGuid;
	}

	public void setPlanGuid(String planGuid) {
		this.planGuid = planGuid;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getTransactionGuid() {
		return transactionGuid;
	}

	public void setTransactionGuid(String transactionGuid) {
		this.transactionGuid = transactionGuid;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public String getClientGuid() {
		return clientGuid;
	}

	public void setClientGuid(String clientGuid) {
		this.clientGuid = clientGuid;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getPolicyGuid() {
		return policyGuid;
	}

	public void setPolicyGuid(String policyGuid) {
		this.policyGuid = policyGuid;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getSegmentGuid() {
		return segmentGuid;
	}

	public void setSegmentGuid(String segmentGuid) {
		this.segmentGuid = segmentGuid;
	}

	public String getSegmentName() {
		return segmentName;
	}

	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}

	public String getActivityGuid() {
		return activityGuid;
	}

	public void setActivityGuid(String activityGuid) {
		this.activityGuid = activityGuid;
	}

	public String getIvsVersion() {
		return ivsVersion;
	}

	public void setIvsVersion(String ivsVersion) {
		this.ivsVersion = ivsVersion;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getHighestVersion() {
		return highestVersion;
	}

	public void setHighestVersion(String highestVersion) {
		this.highestVersion = highestVersion;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ruleGuid == null) ? 0 : ruleGuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OipaRule other = (OipaRule) obj;
		if (ruleGuid == null) {
			if (other.ruleGuid != null)
				return false;
		} else if (!ruleGuid.equals(other.ruleGuid))
			return false;
		return true;
	}

}
