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
@Table(name = "product_details")
public class ProductDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="prdtl_Id")
	private Integer PrdtlId;
	
	@Column(name="prd_Id")
	private Integer prdId;
	
	@Column(name="ind_prod_name")
	private String indProdName; 
	
	private String version;
	
	@Column(name="disposal")
	private String voyaProdLfCyclDispo;
	
	private Date emerge;
	
	@Column(name="select_dt")
	private Date selectDt;
	
	@Column(name="ins_std")
	private Date insStd;
	
	private Date contain;
	
	@Column(name="voya_de_supp_dt")
	private Date voyaDeSuppDt;
	
	private Date retire;
	
	@Column(name="vend_n")
	private String vendN;
	
	@Column(name="vend_supp_alrt")
	private String vendSuppAlrt;
	
	@Column(name="ga_dt")
	private Date gaDt;
	
	@Column(name="end_life_dt")
	private Date endLifeDt;
	
	@Column(name="end_life_supp_lvl")
	private String endLifeSuppLvl;
	
	@Column(name="obs_dt")
	private Date obsDt;
	
	@Column(name="obs_supp_lvl")
	private String obsSuppLvl;
	
	@Column(name="update_by")
	private String updateBy;
	
	@Column(name="update_dt")
	private Date updateDt;
	
	@Column(name="voya_n")
	private String voyaN;
	
	@Column(name="voya_n_1")
	private String voyaN_1;

	/**
	 * @return the prdtlId
	 */
	public Integer getPrdtlId() {
		return PrdtlId;
	}

	/**
	 * @param prdtlId the prdtlId to set
	 */
	public void setPrdtlId(Integer prdtlId) {
		this.PrdtlId = prdtlId;
	}

	/**
	 * @return the prdId
	 */
	public Integer getPrdId() {
		return prdId;
	}

	/**
	 * @param prdId the prdId to set
	 */
	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	/**
	 * @return the indProdName
	 */
	public String getIndProdName() {
		return indProdName;
	}

	/**
	 * @param indProdName the indProdName to set
	 */
	public void setIndProdName(String indProdName) {
		this.indProdName = indProdName;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the voyaProdLfCyclDispo
	 */
	public String getVoyaProdLfCyclDispo() {
		return voyaProdLfCyclDispo;
	}

	/**
	 * @param voyaProdLfCyclDispo the voyaProdLfCyclDispo to set
	 */
	public void setVoyaProdLfCyclDispo(String voyaProdLfCyclDispo) {
		this.voyaProdLfCyclDispo = voyaProdLfCyclDispo;
	}

	/**
	 * @return the emerge
	 */
	public Date getEmerge() {
		return emerge;
	}

	/**
	 * @param emerge the emerge to set
	 */
	public void setEmerge(Date emerge) {
		this.emerge = emerge;
	}

	/**
	 * @return the selectDt
	 */
	public Date getSelectDt() {
		return selectDt;
	}

	/**
	 * @param selectDt the selectDt to set
	 */
	public void setSelectDt(Date selectDt) {
		this.selectDt = selectDt;
	}

	/**
	 * @return the insStd
	 */
	public Date getInsStd() {
		return insStd;
	}

	/**
	 * @param insStd the insStd to set
	 */
	public void setInsStd(Date insStd) {
		this.insStd = insStd;
	}

	/**
	 * @return the contain
	 */
	public Date getContain() {
		return contain;
	}

	/**
	 * @param contain the contain to set
	 */
	public void setContain(Date contain) {
		this.contain = contain;
	}

	/**
	 * @return the voyaDeSuppDt
	 */
	public Date getVoyaDeSuppDt() {
		return voyaDeSuppDt;
	}

	/**
	 * @param voyaDeSuppDt the voyaDeSuppDt to set
	 */
	public void setVoyaDeSuppDt(Date voyaDeSuppDt) {
		this.voyaDeSuppDt = voyaDeSuppDt;
	}

	/**
	 * @return the retire
	 */
	public Date getRetire() {
		return retire;
	}

	/**
	 * @param retire the retire to set
	 */
	public void setRetire(Date retire) {
		this.retire = retire;
	}

	/**
	 * @return the vendN
	 */
	public String getVendN() {
		return vendN;
	}

	/**
	 * @param vendN the vendN to set
	 */
	public void setVendN(String vendN) {
		this.vendN = vendN;
	}

	/**
	 * @return the vendSuppAlrt
	 */
	public String getVendSuppAlrt() {
		return vendSuppAlrt;
	}

	/**
	 * @param vendSuppAlrt the vendSuppAlrt to set
	 */
	public void setVendSuppAlrt(String vendSuppAlrt) {
		this.vendSuppAlrt = vendSuppAlrt;
	}

	/**
	 * @return the gaDt
	 */
	public Date getGaDt() {
		return gaDt;
	}

	/**
	 * @param gaDt the gaDt to set
	 */
	public void setGaDt(Date gaDt) {
		this.gaDt = gaDt;
	}

	/**
	 * @return the endLifeDt
	 */
	public Date getEndLifeDt() {
		return endLifeDt;
	}

	/**
	 * @param endLifeDt the endLifeDt to set
	 */
	public void setEndLifeDt(Date endLifeDt) {
		this.endLifeDt = endLifeDt;
	}

	/**
	 * @return the endLifeSuppLvl
	 */
	public String getEndLifeSuppLvl() {
		return endLifeSuppLvl;
	}

	/**
	 * @param endLifeSuppLvl the endLifeSuppLvl to set
	 */
	public void setEndLifeSuppLvl(String endLifeSuppLvl) {
		this.endLifeSuppLvl = endLifeSuppLvl;
	}

	/**
	 * @return the obsDt
	 */
	public Date getObsDt() {
		return obsDt;
	}

	/**
	 * @param obsDt the obsDt to set
	 */
	public void setObsDt(Date obsDt) {
		this.obsDt = obsDt;
	}

	/**
	 * @return the obsSuppLvl
	 */
	public String getObsSuppLvl() {
		return obsSuppLvl;
	}

	/**
	 * @param obsSuppLvl the obsSuppLvl to set
	 */
	public void setObsSuppLvl(String obsSuppLvl) {
		this.obsSuppLvl = obsSuppLvl;
	}

	/**
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * @return the updateDt
	 */
	public Date getUpdateDt() {
		return updateDt;
	}

	/**
	 * @param updateDt the updateDt to set
	 */
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	/**
	 * @return the voyaN
	 */
	public String getVoyaN() {
		return voyaN;
	}

	/**
	 * @param voyaN the voyaN to set
	 */
	public void setVoyaN(String voyaN) {
		this.voyaN = voyaN;
	}

	/**
	 * @return the voyaN_1
	 */
	public String getVoyaN_1() {
		return voyaN_1;
	}

	/**
	 * @param voyaN_1 the voyaN_1 to set
	 */
	public void setVoyaN_1(String voyaN_1) {
		this.voyaN_1 = voyaN_1;
	}
	
	
}
