package com.cts.migration.model;

import java.util.List;

public class Table {

	private String ruleType;
	private String tableName;
	private String idColumn;
	private List<String> updateColumns;
	private List<String> visibleColumns;
	private String selectQuery;
	private String updateQuery;
	private List<Filter> filters;
	

	public Table() {

	}

	public Table(String ruleType, String tableName, String idColumn, List<String> updateColumns) {
		this.ruleType = ruleType;
		this.tableName = tableName;
		this.idColumn = idColumn;
		this.updateColumns = updateColumns;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getIdColumn() {
		return idColumn;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	public List<String> getUpdateColumns() {
		return updateColumns;
	}

	public void setUpdateColumns(List<String> updateColumns) {
		this.updateColumns = updateColumns;
	}

	public List<String> getVisibleColumns() {
		return visibleColumns;
	}

	public void setVisibleColumns(List<String> visibleColumns) {
		this.visibleColumns = visibleColumns;
	}

	public String getSelectQuery() {
		return selectQuery;
	}

	public void setSelectQuery(String selectQuery) {
		this.selectQuery = selectQuery;
	}

	public String getUpdateQuery() {
		return updateQuery;
	}

	public String getEffectiveQuery(){
		StringBuilder strb = new StringBuilder(selectQuery);
		if(filters!=null){
			for(Filter filter : filters){
				if(filter.getValue()!= null && !"".equalsIgnoreCase(filter.getValue().toString())){
					strb.append(" AND ").append(filter.getColumn()).append("=").append("'").append(filter.getValue()).append("'");
				}
			}
		}
		//System.out.println("Effective Query : "+strb.toString());
		return strb.toString();
	}
	public void setUpdateQuery(String updateQuery) {
		this.updateQuery = updateQuery;
	}
	
	

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public String toString() {
		return ruleType + "_" + tableName + "_" + idColumn + "_" + visibleColumns + "_" + updateColumns+"_"+filters;
	}
}
