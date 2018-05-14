package com.sam.projtrac.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sam.projtrac.dao.ProjTracDAO;
import com.sam.projtrac.entity.Application;
import com.sam.projtrac.entity.ApplicationDetails;
import com.sam.projtrac.entity.ProductAudit;
import com.sam.projtrac.entity.ProductDetails;
import com.sam.projtrac.entity.ProductMaster;
import com.sam.projtrac.repo.ApplicationDetailsRepo;
import com.sam.projtrac.repo.ApplicationRepository;
import com.sam.projtrac.repo.ProductAuditRepository;
import com.sam.projtrac.repo.ProductDetailsRepository;
import com.sam.projtrac.repo.ProductMasterRepository;

@Service
@Transactional
public class ProjTracService {
	
	@Autowired
	ProjTracDAO dao;
	
	@Autowired
	ApplicationRepository appRepo;
	
	@Autowired
	ApplicationDetailsRepo appDetlRepo;
	
	@Autowired
	ProductMasterRepository prdMstrRepo;
	
	@Autowired
	ProductDetailsRepository prdDtlsRepo;
	
	@Autowired
	ProductAuditRepository prdAuditRepo;
	
	public boolean insertApplication(Map<String, String> paramMap){
		//String os = paramMap.get("os");
		String os[] = paramMap.get("os").split(",");
		String pl[] = paramMap.get("pl").split(",");
		String db[] = paramMap.get("db").split(",");
		String as[] = paramMap.get("as").split(",");
		String middleware[] = paramMap.get("middleware").split(",");
		String devops[] = paramMap.get("devops").split(",");
		String framework[] = paramMap.get("framework").split(",");
		List<List<String>> listDetlTable = new ArrayList<List<String>>();
		List<String> data = new ArrayList<String>();
		
		//data.add("os");
		//data.add(os);
		//listDetlTable.add(data);
		generateList("os", os, listDetlTable);
		generateList("pl", pl, listDetlTable);
		generateList("db", db, listDetlTable);
		generateList("as", as, listDetlTable);
		generateList("middleware", middleware, listDetlTable);
		generateList("devops", devops, listDetlTable);
		generateList("framework", framework, listDetlTable);
		if(paramMap.get("appid") != null && !paramMap.get("appid").toString().trim().equals("0000")) {
			int id = Integer.parseInt(paramMap.get("appid").trim().toString());
			dao.updateApplication(paramMap);
			dao.insertApplicationDetl(id, listDetlTable);
		}else {
		int id = dao.insertApplication(paramMap);
		dao.insertApplicationDetl(id, listDetlTable);
		}
		//SELECT prod_disp_name||'('||version ||')' as disp_name FROM SOFTWARE
		return true;
	}
	
	public void generateList(String type, String dataArr[], List<List<String>> listDetlTable){
		ArrayList<String> data = null;
		for(String s: dataArr){
			data = new ArrayList<String>();
			data.add(type);
			data.add(s);
			if(s !=null && s.length()>0 && !"null".equals(s)) {
				System.out.println("sssssss----"+s);
			listDetlTable.add(data);
			}
			}
	}
	
/*	public void evaluate(String appIds){
		for(String appId: appIds.split(",")){
			dao.evaluate(appId);
		}
	}*/
	
	public List  getAppInventoryData(String prodId) {
		List invData = dao.getAppInventoryData(prodId);
		return invData;
		
	}
	
	public List getAppInventoryExistData(Map<String, String> paramMap) {
		List invData = dao.getAppInventoryExistData(paramMap);
		return invData;
	}
	
	public List getAllAppsCurrencyDetails() {
		List invData = dao.getAllAppsCurrencyDetails();
		return invData;
	}
	
	public List getApplicationDetails(int appId) {
		List<Map<String, Object>> appDtlData = dao.getApplicationDetails(appId);
		for (Map<String, Object> map : appDtlData) {
			String currVer = map.get("CURRVER")!=null?map.get("CURRVER").toString():"NA";
		//	String target = map.get("TARGET")!=null?map.get("TARGET").toString():"NA";
			String n = map.get("ITT_N")!=null?map.get("ITT_N").toString():"NA";
			String n_1 = map.get("ITT_N_1")!=null?map.get("ITT_N_1").toString():"NA";
			if (n.equalsIgnoreCase(currVer) || n_1.contains(currVer)) {
				map.put("category", "LB");
			} else {
				map.put("category", "VU");
			}

			if(map.get("COMMENT")==null)
				map.put("COMMENT","");
			//make the uniwue list
			Set<String> set= new HashSet<String>();
			String allVer = map.get("all_ver")!=null?map.get("all_ver").toString():"";
				set.addAll(Arrays.asList(allVer.split(",")));

			map.put("combo", set);
		}
		return appDtlData;

	}

	@Transactional
	public void saveDecision(List<Map<String, Object>> decisions) {
		String app_id="";
		for(Map<String, Object> map: decisions){
			app_id = ""+ map.get("app_id");
			String app_detl_id=""+ map.get("app_dtls_id");
			if(app_detl_id.contains(".")){
				app_detl_id = app_detl_id.substring(0, app_detl_id.indexOf("."));
			}
			ApplicationDetails appDetl = appDetlRepo.findOne(Integer.parseInt(app_detl_id));
			appDetl.setTarget(""+map.get("tarVer"));
			appDetl.setComment(""+map.get("COMMENT"));
			appDetlRepo.save(appDetl);
		}
		if(app_id.contains(".")){
			app_id = app_id.substring(0, app_id.indexOf("."));
		}
		Application app = appRepo.findOne( Integer.parseInt(app_id));
		app.setStatus("Recommended");
		appRepo.save(app);
		
	}
	public String getCurrencyDetails(int appId) {
		String currency = null;
		List<Map<String, Object>> appDtlData = dao.getApplicationDetails(appId);
		for (Map<String, Object> map : appDtlData) {
			String currVer = map.get("CURRVER")!=null?map.get("CURRVER").toString():"NA";
			String n = map.get("ITT_N")!=null?map.get("ITT_N").toString():"NA";
			String n_1 = map.get("ITT_N_1")!=null?map.get("ITT_N_1").toString():"NA";
			if (n.equalsIgnoreCase(currVer) || n_1.contains(currVer)) {
				currency="LB";
			} else {
				currency="VU";
				break;
			}
		}
		
		return currency;

	}
	
	@Transactional
	public void approveProdust(Integer prdId) {
		
		Date now = new Date();
		
		ProductMaster master = prdMstrRepo.findOne(prdId);
		master.setStatus("Approved");
		master.setApproveDate(now);
		prdMstrRepo.save(master);
		
		List<ProductDetails> prdDtlsList= prdDtlsRepo.findByPrdId(prdId);
		
		ProductAudit prdAudit;
		for(ProductDetails p:prdDtlsList) {
			prdAudit =  new ProductAudit();
			prdAudit.setPrdId(prdId);
			prdAudit.setDiposition(p.getVoyaProdLfCyclDispo());
			prdAudit.setVersion(p.getVersion());
			prdAudit.setVoyaN(p.getVoyaN());
			prdAudit.setVoyaN_1(p.getVoyaN_1());
			prdAudit.setApproveDate(now);
			
			prdAuditRepo.save(prdAudit);
		}
	}
}
