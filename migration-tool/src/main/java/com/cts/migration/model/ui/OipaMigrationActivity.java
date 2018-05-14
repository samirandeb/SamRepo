package com.cts.migration.model.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.cts.migration.entity.IvsVersion;
import com.cts.migration.model.Table;

public class OipaMigrationActivity {

	private String user;

	private String inputType;
	private OipaRegion sourceRegion;
	private OipaRegion destinationRegion;
	private Table selectedRuleTable;

	private List<OipaRule> rulesForMigration = new ArrayList<>();
	private HashSet<String> selectedRuleGuidList = new HashSet<>();
	private List<OipaRule> rules = new ArrayList<>();
	private List<IvsVersion> versions = new ArrayList<IvsVersion>();
	private List<OipaMigrationAudit> auditList = new ArrayList<OipaMigrationAudit>();


	private String auditId;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public OipaRegion getSourceRegion() {
		return sourceRegion;
	}

	public void setSourceRegion(OipaRegion sourceRegion) {
		this.sourceRegion = sourceRegion;
	}

	public OipaRegion getDestinationRegion() {
		return destinationRegion;
	}

	public void setDestinationRegion(OipaRegion destinationRegion) {
		this.destinationRegion = destinationRegion;
	}

	public Table getSelectedRuleTable() {
		return selectedRuleTable;
	}

	public void setSelectedRuleTable(Table selectedRuleTable) {
		this.selectedRuleTable = selectedRuleTable;
	}

	public List<OipaRule> getRulesForMigration() {
		return rulesForMigration;
	}

	// public OipaRule getRuleForMigration(String ruleGuid) {
	// for(OipaRule rule:rulesForMigration)
	// if(ruleGuid.equals(rule.getRuleGuid()))
	// return rule;
	// return null;
	// }

	public void setRulesForMigration(List<OipaRule> rulesForMigration) {
		this.rulesForMigration = rulesForMigration;
	}

	public HashSet<String> getSelectedRuleGuidList() {
		return selectedRuleGuidList;
	}

	public void setSelectedRuleGuidList(HashSet<String> selectedRuleGuidList) {
		this.selectedRuleGuidList = selectedRuleGuidList;
	}

	public List<OipaRule> getRules() {
		return rules;
	}

	public void setRules(List<OipaRule> rules) {
		this.rules = rules;
	}

	public List<IvsVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<IvsVersion> versions) {
		this.versions = versions;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public List<OipaMigrationAudit> getAuditList() {
		return auditList;
	}

	public void setAuditList(List<OipaMigrationAudit> auditList) {
		this.auditList = auditList;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public void updateRulesForMigration(Collection<String> rulesToBeAdded) {
//		rulesForMigration.clear();
		for (String ruleGuid : rulesToBeAdded) {
			OipaRule rule = rules.get(rules.indexOf(new OipaRule(ruleGuid)));
			if (rule != null && !rulesForMigration.contains(rule)) {
				rulesForMigration.add(rule);
			}
		}

	}

	public void printDetail() {
		//System.out.println("Source Region - " + (sourceRegion != null ? sourceRegion.getRegionName() : "NULL"));
		//System.out.println("Destination Region - " + (destinationRegion != null ? destinationRegion.getRegionName() : "NULL"));
		//System.out.println("Rules- " + rulesForMigration);
		//System.out.println("RulesList- " + selectedRuleGuidList);

	}

}
