package com.sam.projtrac.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "product_audit")
public class ProductAudit implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="prdadt_id")
	private Integer PrdadtID;
	
	@Column(name="prd_Id")
	private Integer prdId;
	
	private String version;
	
	@Column(name="disposition")
	private String diposition;
	
	@Column(name="voya_n")
	private String voyaN;
	
	@Column(name="voya_n_1")
	private String voyaN_1;
	
	private Date approveDate;

	public Integer getPrdadtID() {
		return PrdadtID;
	}

	public void setPrdadtID(Integer prdadtID) {
		PrdadtID = prdadtID;
	}

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDiposition() {
		return diposition;
	}

	public void setDiposition(String diposition) {
		this.diposition = diposition;
	}

	public String getVoyaN() {
		return voyaN;
	}

	public void setVoyaN(String voyaN) {
		this.voyaN = voyaN;
	}

	public String getVoyaN_1() {
		return voyaN_1;
	}

	public void setVoyaN_1(String voyaN_1) {
		this.voyaN_1 = voyaN_1;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	
}
