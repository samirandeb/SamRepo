package com.sam.model;

import java.util.Map;

public class TechnologyHeatmap {

	private String applicationName;
	private String type;
	private Map<String, Long> heatmap;
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Long> getHeatmap() {
		return heatmap;
	}
	public void setHeatmap(Map<String, Long> heatmap) {
		this.heatmap = heatmap;
	}
	
	
}
