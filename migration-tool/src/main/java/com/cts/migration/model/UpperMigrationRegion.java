package com.cts.migration.model;

import java.util.List;

public class UpperMigrationRegion {

	private String name;
	private String description;
	private List<String> dependentOn;
	private String flagColumn;
	private String selectQuery;
	private String historyQuery;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getDependentOn() {
		return dependentOn;
	}

	public void setDependentOn(List<String> dependentOn) {
		this.dependentOn = dependentOn;
	}

	public String getFlagColumn() {
		return flagColumn;
	}

	public void setFlagColumn(String flagColumn) {
		this.flagColumn = flagColumn;
	}

	public String getSelectQuery() {
		return selectQuery;
	}

	public void setSelectQuery(String selectQuery) {
		this.selectQuery = selectQuery;
	}

	public String getHistoryQuery() {
		return historyQuery;
	}

	public void setHistoryQuery(String historyQuery) {
		this.historyQuery = historyQuery;
	}

}
