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
@Table(name = "product_master")
public class ProductMaster implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="prd_Id")
	private Integer PrdId;
	
	@Column(name="road_map")
	private String roadmap;
	
	@Column(name="mgmt_grp")
	private String mgmtGrp;
	
	@Column(name="manufacturer")
	private String manufacturer; 
	
	@Column(name="prod_disp_name")
	private String prodDispName;
	
	@Column(name="status")
	private String status;
	
	private Date approveDate;

	

	/**
	 * @return the prdId
	 */
	public Integer getPrdId() {
		return PrdId;
	}

	/**
	 * @param prdId the prdId to set
	 */
	public void setPrdId(Integer prdId) {
		this.PrdId = prdId;
	}

	/**
	 * @return the roadmap
	 */
	public String getRoadmap() {
		return roadmap;
	}

	/**
	 * @param roadmap the roadmap to set
	 */
	public void setRoadmap(String roadmap) {
		this.roadmap = roadmap;
	}

	/**
	 * @return the mgmtGrp
	 */
	public String getMgmtGrp() {
		return mgmtGrp;
	}

	/**
	 * @param mgmtGrp the mgmtGrp to set
	 */
	public void setMgmtGrp(String mgmtGrp) {
		this.mgmtGrp = mgmtGrp;
	}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the prodDispName
	 */
	public String getProdDispName() {
		return prodDispName;
	}

	/**
	 * @param prodDispName the prodDispName to set
	 */
	public void setProdDispName(String prodDispName) {
		this.prodDispName = prodDispName;
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

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	

}
