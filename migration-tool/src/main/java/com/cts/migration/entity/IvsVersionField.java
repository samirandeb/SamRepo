package com.cts.migration.entity;

import java.util.Date;

public class IvsVersionField {

	private String versionGuid;
	private String fieldName;
	private String fieldTypeCode;
	private Date dateValue;
	private String textValue;
	private int intValue;
	private double floatValue;
	private String xmlDataValue;

	public IvsVersionField() {
	}

	public IvsVersionField(String versionGuid, String fieldName, String fieldTypeCode, Date dateValue, String textValue, int intValue, double floatValue, String xmlDataValue) {
		super();
		this.versionGuid = versionGuid;
		this.fieldName = fieldName;
		this.fieldTypeCode = fieldTypeCode;
		this.dateValue = dateValue;
		this.textValue = textValue;
		this.intValue = intValue;
		this.floatValue = floatValue;
		this.xmlDataValue = xmlDataValue;
	}

	public String getVersionGuid() {
		return versionGuid;
	}

	public void setVersionGuid(String versionGuid) {
		this.versionGuid = versionGuid;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTypeCode() {
		return fieldTypeCode;
	}

	public void setFieldTypeCode(String fieldTypeCode) {
		this.fieldTypeCode = fieldTypeCode;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public double getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(double floatValue) {
		this.floatValue = floatValue;
	}

	public String getXmlDataValue() {
		return xmlDataValue;
	}

	public void setXmlDataValue(String xmlDataValue) {
		this.xmlDataValue = xmlDataValue;
	}

	public Object getValue() {
		switch (fieldTypeCode) {
		case "01":
			return dateValue;
		case "02":
			return textValue;
		case "03":
			return intValue;
		case "04":
			return floatValue;
		case "05":
			return xmlDataValue;
		default:
			return null;
		}
	}

}
