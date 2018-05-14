package com.cts.migration.entity;

import java.util.List;

import com.cts.migration.model.Table;

public class IvsRule {
	private String versionGuid;
	private List<IvsVersionField> fields;

	public IvsRule(String versionGuid, List<IvsVersionField> fields) {
		this.versionGuid = versionGuid;
		this.fields = fields;
	}

	public String getVersionGuid() {
		return versionGuid;
	}

	public void setVersionGuid(String versionGuid) {
		this.versionGuid = versionGuid;
	}

	public List<IvsVersionField> getFields() {
		return fields;
	}

	public void setFields(List<IvsVersionField> fields) {
		this.fields = fields;
	}

	public Object getValue(String fieldName) {
		for (IvsVersionField field : fields) {
			if (field.getFieldName().equalsIgnoreCase(fieldName))
				return field.getValue();
		}
		return null;
	}

	public Object[] getUpdateParams(Table table) {
		Object[] params = new Object[table.getUpdateColumns().size()+1];
		int i = 0;
		for (String column : table.getUpdateColumns()) {
			params[i++] = getValue(column);
		}
		params[i++] = getValue(table.getIdColumn());
		return params;
	}

}
