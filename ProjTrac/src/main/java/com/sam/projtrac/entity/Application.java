package com.sam.projtrac.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "application")
public class Application  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="app_id")
	private int AppId;
	

	private String name;
	
	private String lob;
	
	private String status;
	
	private String scope;
	
	private String technology;
	
	private String targetDC;
	
	private String roadmap;
	
	
	public int getAppId() {
		return AppId;
	}

	public void setAppId(int appId) {
		this.AppId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getTargetDC() {
		return targetDC;
	}

	public void setTargetDC(String targetDC) {
		this.targetDC = targetDC;
	}

	public String getRoadmap() {
		return roadmap;
	}

	public void setRoadmap(String roadmap) {
		this.roadmap = roadmap;
	}
	
	

}