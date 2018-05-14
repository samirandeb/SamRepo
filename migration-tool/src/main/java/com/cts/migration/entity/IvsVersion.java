package com.cts.migration.entity;

import java.util.Date;

public class IvsVersion {

	private String versionGuid;
	private String ruleGuid;
	private int versionNumber;
	private String ruleTypeCode;
	private String lastModifiedBy;
	private Date lastModifiedGMT;
	private String comments;
	private String label;

	public IvsVersion() {
	}

	public IvsVersion(String versionGuid, String ruleGuid, int versionNumber, String ruleTypeCode, String lastModifiedBy, Date lastModifiedGMT, String comments, String label) {
		super();
		this.versionGuid = versionGuid;
		this.ruleGuid = ruleGuid;
		this.versionNumber = versionNumber;
		this.ruleTypeCode = ruleTypeCode;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedGMT = lastModifiedGMT;
		this.comments = comments;
		this.label = label;
	}

	public String getVersionGuid() {
		return versionGuid;
	}

	public void setVersionGuid(String versionGuid) {
		this.versionGuid = versionGuid;
	}

	public String getRuleGuid() {
		return ruleGuid;
	}

	public void setRuleGuid(String ruleGuid) {
		this.ruleGuid = ruleGuid;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getRuleTypeCode() {
		return ruleTypeCode;
	}

	public void setRuleTypeCode(String ruleTypeCode) {
		this.ruleTypeCode = ruleTypeCode;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedGMT() {
		return lastModifiedGMT;
	}

	public void setLastModifiedGMT(Date lastModifiedGMT) {
		this.lastModifiedGMT = lastModifiedGMT;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getInsertQuery() {
		return "INSERT INTO IVSVERSION(VERSIONGUID,RULEGUID,VERSIONNUMBER,RULETYPECODE,LASTMODIFIEDBY,LASTMODIFIEDGMT,COMMENTS,LABEL) values(?,?,?,?,?,?,?,?)";
	}
}
