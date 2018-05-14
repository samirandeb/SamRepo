package com.sam.projtrac.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.sam.projtrac.dto.DecisionDTO;
import com.sam.projtrac.entity.ProductDetails;
import com.sam.projtrac.entity.ProductMaster;
import com.sam.projtrac.repo.ProductDetailsRepository;
import com.sam.projtrac.repo.ProductMasterRepository;
import com.sam.projtrac.service.ProjTracService;

@Controller
public class IndexController {

	@Autowired
	private ProjTracService service;
	
	@Autowired
	private ProductDetailsRepository prdDtlsRepo;
	
	@Autowired
	private ProductMasterRepository prdMstrRepo;

	//Suji start add		
	@RequestMapping("/")
	public String homePage(Model model) {
		model.addAttribute("currentModule","home");
		//model.addAttribute("fragmentName", "fragments/home");
		//model.addAttribute("fragmentName", "fragments/appInventory");
		model.addAttribute("fragmentName", "fragments/dashBoard");
        return "index";
	}
	
	@RequestMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("currentModule","dashboard");
		model.addAttribute("fragmentName", "fragments/dashboard");
        return "index";
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/dashboard/{appId}")
	@ResponseBody
	public List getAllAppsCurrencyDetails() {
		List invData = service.getAllAppsCurrencyDetails();
		System.out.println("CAllng service"+invData);
		return invData;
	}
	//Suji end add	
	
	@RequestMapping("/appStore")
	public String appStore(Model model) {
		model.addAttribute("currentModule","appStore");
		model.addAttribute("fragmentName", "fragments/appStore");
		return "index";
	}
	
	@RequestMapping("/appSoftware")
	public String appSoftware(Model model) {
		model.addAttribute("currentModule","appSoftware");
		model.addAttribute("fragmentName", "fragments/appSoftware");
		return "index";
	}
	
	@RequestMapping("/appInventory")
	public String appInventory(Model model) {
		model.addAttribute("currentModule","appDependency");
		model.addAttribute("fragmentName", "fragments/appInventory");
		
		return "index";
	}
	
	@RequestMapping("/appDependency")
	public String appDependencyMenu(Model model) {
		model.addAttribute("currentModule","appDependency");
		model.addAttribute("fragmentName", "fragments/appDependency");
		return "index";
	}
	
	@RequestMapping("/appDependency/{appName}")
	public String appDependency(@PathVariable("appName") String appName, Model model) {
		//System.out.println("%%%%%%%%%%%%%% APP NAME ::::"+appName);
		model.addAttribute("currentModule","appDependency");
		model.addAttribute("fragmentName", "fragments/appDependency");
		model.addAttribute("appName", appName);
		return "index";
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/appInvData/{prodId}")
	@ResponseBody
	public List getAppInventoryData(@PathVariable("prodId") String prodId) {
		List invData = service.getAppInventoryData(prodId);
		//System.out.println(invData);
		return invData;
	}
	
	@RequestMapping("/addApplication")
	public String addApplication(Model model) {
		model.addAttribute("currentModule","addApplication");
		model.addAttribute("fragmentName", "fragments/addApplication");
		return "index";
	}
	
	@RequestMapping("/decision")
	public String decision(Model model) {
		model.addAttribute("currentModule","decision");
		model.addAttribute("fragmentName", "fragments/decision");
		return "index";
	}
	
	@RequestMapping("/insertApplication/{appName}/{lob}/{scope}/{technology}/{targetDC}/{os}/{pl}/{db}/{as}/{middleware}/{devops}/{framework}/{appid}")
	@ResponseBody
	public String insertApplication(@PathVariable("appName") String appName, @PathVariable("lob") String lob, @PathVariable("scope") String scope, @PathVariable("technology") String technology, @PathVariable("targetDC") String targetDC, @PathVariable("os") String os, @PathVariable("pl") String pl, @PathVariable("db") String db, @PathVariable("as") String as, @PathVariable("middleware") String middleware, @PathVariable("devops") String devops, @PathVariable("framework") String framework,@PathVariable("appid") String appid) {
		Map<String, String> paramMap = new HashMap<String, String>();
		/* JdbcTemplate template = new JdbcTemplate(ProjtracConnection.getDataSource());
		        // int row = template.update(insertSql, params, types);
		 SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("name", appName)
					.addValue("lob", lob);
		         KeyHolder keyHolder = new GeneratedKeyHolder();
		         template.update("insert into application(name, lob) values(:name, :lob)", parameters, keyHolder);
		         int keyVal =  keyHolder.getKey().intValue();
		         System.out.println(" row inserted.:"+keyVal);*/
		paramMap.put("name", appName);
		paramMap.put("lob", lob);
		paramMap.put("scope", scope);
		paramMap.put("technology", technology);
		paramMap.put("targetDC", targetDC);
		paramMap.put("os", os);
		paramMap.put("pl", pl);
		paramMap.put("db", db);
		paramMap.put("as", as);
		paramMap.put("middleware", middleware);
		paramMap.put("devops", devops);
		paramMap.put("framework", framework);
		paramMap.put("appid", appid);
		boolean status = service.insertApplication(paramMap );

		return "index";
	}
	
/*	@RequestMapping("/evaluate/{appIds}")
	@ResponseBody
	public String evaluate(@PathVariable("appIds") String appIds) {
		Map<String, String> paramMap = new HashMap<String, String>();
		service.evaluate(appIds);

		return "index";
	}*/

	/*@RequestMapping("/applications")
	public String applications(Model model) {
		List<Application> apps = assessmentService.getAllApplicationName();
		List<AssessmentResponse> exportResponses = assessmentService.getAllQuestion(null,exportQuestionMaster.getQuestions());
		model.addAttribute("apps", apps);
		model.addAttribute("exportResponses", exportResponses);
		model.addAttribute("subTitle", ">> Dashboard");
		return "applications";
	}

	@RequestMapping("/assessment/{appType}")
	public String newAssessment(Model model, @ModelAttribute ApplicationAssessment qList, @PathVariable("appType") String appType) {

		qList.updateAssessmentByQuestion((assessmentService.getAllQuestion(appType)));
		model.addAttribute("questionResponse", qList);
		model.addAttribute("subTitle", ">> "+appType+" Assessment");
		return "assessment";
	}

	@RequestMapping("/application/{appName}")
	public String assessmentDetail(Model model, @ModelAttribute ApplicationAssessment qList, @PathVariable("appName") String appName) {

		qList = new ApplicationAssessment();
		qList.updateAssessmentByResponse(assessmentService.getApplicationDetail(appName));
		qList.setApplicationName(appName);
		model.addAttribute("questionResponse", qList);
		model.addAttribute("subTitle", ">> "+appName);
		return "assessment";
	}

	@RequestMapping("/save")
	public @ResponseBody String save(Model model, @ModelAttribute ApplicationAssessment qList) {
		for (QuestionGroup qg : qList.getQuestionGruopList()) {
			for (AssessmentResponse response : qg.getResponses()) {
				
				response.setApplication(qList.getApplicationName());
				response.setQuestionId(response.getQuestion().getId());
				response.setResponse(response.getResponse());
				assessmentService.saveAssessmentResponse(response);
			}
		}
		return "saved";
	}

	@RequestMapping("/scorecard")
	public String scorecard(Model model) {

		//List<ScoreCard> scores = assessmentService.getScoreCard();
		
		List<ScoreCard> scores = assessmentService.getCurrencyScore();
		model.addAttribute("scores", scores);
		model.addAttribute("subTitle", ">> Score Card");
		return "scorecard";
	}
	
	@RequestMapping("/heatmap")
	public String heatmap(Model model) {
		List<TechnologyHeatmap> heatmapList = assessmentService.getHeatmap();
		model.addAttribute("heatmap", heatmapList);
		model.addAttribute("subTitle", ">> Heatmap");
		return "heatmap";
	}
	
//	@RequestMapping("/heat")
//	public String heat(Model model) {
////		List<TechnologyHeatmap> heatmapList = assessmentService.getHeatmap();
////		model.addAttribute("heatmap", heatmapList);
//		return "heat";
//	}
	
	@RequestMapping("/appGroupScore")
	public @ResponseBody List<ScoreCardAppGroup> appGroupScore() {
		List<ScoreCardAppGroup> scores = assessmentService.getAppGroupScore();
		return scores;
	}*/
	// @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	// @RequestMapping(method=RequestMethod.PUT, value="{id}")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@ResponseBody
	  public String update(@PathVariable Integer id, @RequestBody ProductDetails prdDtls) {
		 ProductDetails update = (ProductDetails) prdDtlsRepo.findOne(id);
	    update.setIndProdName(prdDtls.getIndProdName());
	    update.setVersion(prdDtls.getVersion());
	    //System.out.println(prdDtls.getVoyaProdLfCyclDispo());
	    update.setVoyaProdLfCyclDispo(prdDtls.getVoyaProdLfCyclDispo());
	    update.setEmerge(prdDtls.getEmerge());
	    update.setSelectDt(prdDtls.getSelectDt());
	    update.setInsStd(prdDtls.getInsStd());
	    update.setContain(prdDtls.getContain());
	    update.setVoyaDeSuppDt(prdDtls.getVoyaDeSuppDt());
	    update.setRetire(prdDtls.getRetire());
	    update.setVendN(prdDtls.getVendN());
	    update.setVendSuppAlrt(prdDtls.getVendSuppAlrt());
	    update.setGaDt(prdDtls.getGaDt());
	    update.setEndLifeDt(prdDtls.getEndLifeDt());
	    update.setEndLifeSuppLvl(prdDtls.getEndLifeSuppLvl());
	    update.setObsDt(prdDtls.getObsDt());
	    update.setObsSuppLvl(prdDtls.getObsSuppLvl());
	    
	   // System.out.println(comments);
	    prdDtlsRepo.save(update);
	   // return repo.save(update);
	    return "index";
	  }
	
	@RequestMapping(value = "/updateVoyaN/{prid}/{prdtlId}/{prdtlIds}", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String updateVoyaN(@PathVariable Integer prid, @PathVariable Integer prdtlId, @PathVariable Set<Integer> prdtlIds) {
	    
		// ProductDetails update = (ProductDetails) prdDtlsRepo.findOne(prdtlId);
		ProductMaster master = prdMstrRepo.findOne(prid);
		master.setStatus("Recommended");
		    prdMstrRepo.save(master);
		    
		   List<ProductDetails>  list = prdDtlsRepo.findByPrdId(prid);
		   for(ProductDetails prdDtlsObj: list){
			   //Set Voya N
		   if(prdDtlsObj.getPrdtlId().equals(prdtlId)){
			   prdDtlsObj.setVoyaN("Y");
		   } else{
			   prdDtlsObj.setVoyaN("N");
		   }
		   
		   //set Voya N-1
		   if(prdtlIds.contains(prdDtlsObj.getPrdtlId())){
			   prdDtlsObj.setVoyaN_1("Y");
		   } else{
			   prdDtlsObj.setVoyaN_1("N");
			   }
			   prdDtlsRepo.save(prdDtlsObj);
		   }
		  //  prdDtlsRepo.save(update);
		   // return repo.save(update);
		return "index";
	}

		
	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/appInvExistData/{appId}")
	@ResponseBody
	public List getAppInvExistData(@PathVariable("appId") String appId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", appId);
		List invData = service.getAppInventoryExistData(paramMap);
		System.out.println(invData);
		return invData;
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/appDtlsData/{appId}")
	@ResponseBody
	public List getApplicationDetails(@PathVariable Integer appId) {
		List invData = service.getApplicationDetails(appId);
		System.out.println(invData);
		return invData;
	}
	
	@RequestMapping(value = "/decision", method = RequestMethod.POST)
	@ResponseBody
	public String saveDecision(@RequestBody String json) {
		Gson gson= new Gson();
		//JSONPObject
		 List<Map<String,Object>> decisions = gson.fromJson(json, List.class);
		 service.saveDecision(decisions);
		//System.out.println(decisions);
	    return "index";
	}
		
	@RequestMapping("/report")
	public String l(Model model) {
		model.addAttribute("currentModule","report");
		model.addAttribute("fragmentName", "fragments/report");
		return "index";
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/appDtlscurr/{appId}")
	@ResponseBody
	public String getCurrencyDetails(@PathVariable Integer appId) {
		String invData = service.getCurrencyDetails(appId);
		System.out.println(invData);
		return invData;
	}
		
	@RequestMapping(value = "/approvePrd/{prdId}", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String approveProduct(@PathVariable Integer prdId) {

		service.approveProdust(prdId);
		return "index";
	}
}
