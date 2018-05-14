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
@Table(name = "product_history")
public class ProductHistory implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="prdhst_Id")
	private Integer prdhistId;
	
	@Column(name="prdtl_Id")
	private Integer prdtlId;
	
	@Column(name="comments")
	private String comments;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_dt")
	private Date createdDt;

	/**
	 * @return the prdhistId
	 */
	public Integer getPrdhistId() {
		return prdhistId;
	}

	/**
	 * @param prdhistId the prdhistId to set
	 */
	public void setPrdhistId(Integer prdhistId) {
		this.prdhistId = prdhistId;
	}

	/**
	 * @return the prdtlId
	 */
	public Integer getPrdtlId() {
		return prdtlId;
	}

	/**
	 * @param prdtlId the prdtlId to set
	 */
	public void setPrdtlId(Integer prdtlId) {
		this.prdtlId = prdtlId;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDt
	 */
	public Date getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt the createdDt to set
	 */
	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

}
