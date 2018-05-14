package com.sam.projtrac.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_details")
public class ApplicationDetails  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="app_dtls__id")
	private int appDtlsId;
	
	@Column(name="app_id")
	private int appId;
	

	private String type;
	
	@Column(name="prd_id")
	private int softId;

	private String target;
	
	private String comment;

	public int getAppDtlsId() {
		return appDtlsId;
	}

	public void setAppDtlsId(int appDtlsId) {
		this.appDtlsId = appDtlsId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSoftId() {
		return softId;
	}

	public void setSoftId(int softId) {
		this.softId = softId;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}