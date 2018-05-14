package com.cts.migration.model.ui;

public class OipaRegion {

	private String regionName;
	private String regionDescription;
	private boolean nonIvsRegion;

	public String getRegionName() {
		return regionName;
	}

	public boolean isNonIvsRegion() {
		return nonIvsRegion;
	}

	public void setNonIvsRegion(boolean nonIvsRegion) {
		this.nonIvsRegion = nonIvsRegion;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionDescription() {
		return regionDescription;
	}

	public void setRegionDescription(String regionDescription) {
		this.regionDescription = regionDescription;
	}

	public OipaRegion() {

	}

	public OipaRegion(String regionName, String regionDescription) {
		super();
		this.regionName = regionName;
		this.regionDescription = regionDescription;
	}

	public String toString() {
		return regionName;
	}
	
	

}
