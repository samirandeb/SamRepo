package com.cts.migration.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.cts.migration.model.Table;

public class QueryGenerator implements IQuery {

	private Table table;

	private Map<String, Object> columnValueMap = new LinkedHashMap<String, Object>();

	public QueryGenerator(Table table, IvsRule rule) {
		this.table = table;
		for (IvsVersionField field : rule.getFields()) {
			columnValueMap.put(field.getFieldName(), field.getValue());
		}
	}

	public Map<String, Object> getColumnValueMap() {
		return columnValueMap;
	}

	public void setColumnValueMap(Map<String, Object> columnValueMap) {
		this.columnValueMap = columnValueMap;
	}

	public Object[] getColumns() {
		return (Object[]) columnValueMap.keySet().toArray();
	}

	public Object[] getData() {
		return (Object[]) columnValueMap.values().toArray();
	}

	@Override
	public String getInsertQuery() {
		return INSERT + INTO + table.getTableName() + SPACE + PARENTHESIS_OPEN + stringifyColumns(getColumns()) + PARENTHESIS_CLOSE + VALUES + PARENTHESIS_OPEN + stringifyParams(getColumns().length) + PARENTHESIS_CLOSE;
	}

	@Override
	public String getUpdateByIdQuery() {
		return UPDATE + table.getTableName() + SPACE + SET + stringifyUpdateParams((String[])table.getUpdateColumns().toArray()) + WHERE + table.getIdColumn() + SPACE + EQUALS + PARAM;
	}

	@Override
	public String getDeletByIdQuery() {
		return DELETE + FROM + table.getTableName() + SPACE + WHERE + table.getIdColumn() + SPACE + EQUALS + PARAM;
	}

	public String stringifyColumns(Object[] columns) {
		String str = "";
		for (int i = 0; i < columns.length; i++) {
			if (i != columns.length - 1) {
				str = str + columns[i] + COMMA;
			} else {
				str = str + columns[i];
			}
		}
		return str;
	}

	public String stringifyParams(int paramCount) {
		String str = "";
		for (int i = 0; i < paramCount; i++) {
			str = str + PARAM + COMMA;
		}
		return str.substring(0, str.lastIndexOf(COMMA) - 1);
	}

	public String stringifyUpdateParams(String[] columns) {
		String str = "";
		for (int i = 0; i < columns.length; i++) {
			if (i != columns.length - 1) {
				str = str + columns[i] + EQUALS + PARAM + COMMA;
			} else {
				str = str + columns[i] + EQUALS + PARAM;
			}
		}
		return str;
	}
}
