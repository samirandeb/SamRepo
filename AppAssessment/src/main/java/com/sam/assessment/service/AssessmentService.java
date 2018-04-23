package com.sam.assessment.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sam.assessment.HeatmapView;
import com.sam.assessment.ScoreCardQuestionMaster;
import com.sam.assessment.dao.AssessmentDao;
import com.sam.assessment.dao.AssessmentResponseDao;
import com.sam.assessment.dao.ScoreMasterDao;
import com.sam.assessment.entity.AssessmentResponse;
import com.sam.assessment.entity.QuestionMaster;
import com.sam.assessment.entity.ScoreMaster;
import com.sam.model.Application;
import com.sam.model.ScoreCard;
import com.sam.model.ScoreCardAppGroup;
import com.sam.model.TechnologyHeatmap;

@Service
@Transactional
public class AssessmentService {

	@Autowired
	private AssessmentDao assessmentDao;

	@Autowired
	private AssessmentResponseDao assessmentResponseDao;

	@Autowired
	private ScoreMasterDao scoreMasterDao;

	private List<ScoreMaster> scoreMaster;

	@Autowired
	private ScoreCardQuestionMaster scoreCardQuestionMaster;
	
	@Autowired
	private HeatmapView heatmapView;

	public List<QuestionMaster> getAllQuestion(String appType) {
		return assessmentDao.getAllQuestion(appType);
	}

	public List<ScoreMaster> getScoreMaster() {
		if (scoreMaster == null) {
			scoreMaster = scoreMasterDao.getAllScoreMaster();
		}
		return scoreMaster;
	}

	public void saveQuestion(QuestionMaster question) {
		assessmentDao.saveQuestion(question);
	}

	public List<AssessmentResponse> getApplicationDetail(String appName) {
		return assessmentResponseDao.getAllResponse(appName);
	}

	public void saveAssessmentResponse(AssessmentResponse response) {
		assessmentResponseDao.save(response);
	}

	public List<Application> getAllApplicationName() {
		return assessmentResponseDao.getDistinctApplication();
	}

	
	public List<TechnologyHeatmap> getHeatmap() {
		List<TechnologyHeatmap> heatmapList = assessmentResponseDao.getAppListTechnologyHeatmap();
		for (TechnologyHeatmap techHeatmap : heatmapList) {
			assessmentResponseDao.getTechnologyHeatmap(techHeatmap, heatmapView.getAllQuestions());
			//heatmapList.add(techHeatmap);
		}
		return heatmapList;
	}

	public List<ScoreCard> getCurrencyScore() {
		List<ScoreCard> scores = assessmentResponseDao.getAppListScoreCard();
		for (ScoreCard scoreCard : scores) {
			scoreCard.setPlScore(assessmentResponseDao.getCurrencyScore(scoreCard, scoreCardQuestionMaster.getPlQuestions()));
			scoreCard.setAppServerScore(assessmentResponseDao.getCurrencyScore(scoreCard, scoreCardQuestionMaster.getServerQuestions()));
			scoreCard.setDbScore(assessmentResponseDao.getCurrencyScore(scoreCard, scoreCardQuestionMaster.getDbQuestions()));
			scoreCard.setApplicationFrameworkScore(assessmentResponseDao.getCurrencyScore(scoreCard, scoreCardQuestionMaster.getApplicationFrameworkQuestions()));
			scoreCard.setPresentationLayerScore(assessmentResponseDao.getCurrencyScore(scoreCard, scoreCardQuestionMaster.getPresentationLayerQuestions()));
			scoreCard.setDataAccessScore(assessmentResponseDao.getCurrencyScore(scoreCard, scoreCardQuestionMaster.getDataAccessQuestions()));
			if("COTS".equals(scoreCard.getType()) || "MW".equals(scoreCard.getType()))
			{
				scoreCard.setApplicationFrameworkScore(assessmentResponseDao.getCurrencyScoreBoolean(scoreCard, scoreCardQuestionMaster.getBooleanQuestions()));
			}
		if(scoreCard.getPlScore()> 0 && scoreCard.getAppServerScore() > 0 && scoreCard.getDbScore()> 0 && scoreCard.getApplicationFrameworkScore()> 0 && scoreCard.getPresentationLayerScore()> 0 && scoreCard.getDataAccessScore() > 0)
			{
				scoreCard.setApplicationScore((scoreCard.getPlScore()+scoreCard.getAppServerScore()+scoreCard.getDbScore()+scoreCard.getApplicationFrameworkScore()+scoreCard.getPresentationLayerScore()+scoreCard.getDataAccessScore())/6);
			}
			else
				scoreCard.setApplicationScore(-1);
		}
		return scores;
	}
	
	public List<ScoreCardAppGroup> getAppGroupScore(){
		
		List<ScoreCardAppGroup> result = new ArrayList<>();
		List<ScoreCard> scores = getCurrencyScore();
		if(scores!=null){
			for(ScoreCard item : scores){
				
				item.setApplicationScore(Math.round((item.getPlScore()+item.getAppServerScore()+item.getDbScore()+item.getApplicationFrameworkScore()+item.getPresentationLayerScore()+item.getDataAccessScore())/6));
				
				ScoreCardAppGroup appGroup = new ScoreCardAppGroup();
				appGroup.setApplicationGroup(item.getType());
				if(!result.contains(appGroup)){
					result.add(appGroup);
				}
				appGroup = result.get(result.indexOf(appGroup));
				
				appGroup.getScoreCards().add(item);
			}
		}
		return result;
	}

	public List<AssessmentResponse> getAllQuestion(String appName, List<Long> questions) {
		List<AssessmentResponse> response = new ArrayList<>();
		response = assessmentResponseDao.getAllResponse(appName, questions);
		return response;
	}

}
