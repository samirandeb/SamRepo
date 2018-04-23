package com.sam.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sam.assessment.ExportQuestionMaster;
import com.sam.assessment.entity.AssessmentResponse;
import com.sam.assessment.service.AssessmentService;
import com.sam.model.Application;
import com.sam.model.ApplicationAssessment;
import com.sam.model.QuestionGroup;
import com.sam.model.ScoreCard;
import com.sam.model.ScoreCardAppGroup;
import com.sam.model.TechnologyHeatmap;

@Controller
public class IndexController {

	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
	private ExportQuestionMaster exportQuestionMaster;

	@RequestMapping("/")
	public String homePage(Model model) {

//		List<Application> apps = assessmentService.getAllApplicationName();
//		for (Application app : apps)
//			System.out.println("Row " + app.getName() + "\t" + app.getAnswered());
//		model.addAttribute("apps", apps);
		model.addAttribute("subTitle", ">> Home");
		return "index";
	}

	@RequestMapping("/applications")
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
	}
}
