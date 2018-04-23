package com.sam.assessment.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.sam.assessment.entity.AssessmentResponse;
import com.sam.model.Application;
import com.sam.model.ScoreCard;
import com.sam.model.TechnologyHeatmap;

@Repository
public class AssessmentResponseDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void create(AssessmentResponse response) {
		entityManager.persist(response);
	}

	public void update(AssessmentResponse qMaster) {
		entityManager.merge(qMaster);
	}

	@SuppressWarnings("unchecked")
	public List<AssessmentResponse> getAllResponse(String appName) {
		return getAllResponse(appName, null);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AssessmentResponse> getAllResponse(List<Long> questionIds) {
		return getAllResponse(null,questionIds);
	}
	

	@SuppressWarnings("unchecked")
	public List<AssessmentResponse> getAllResponse(String appName,List<Long> qids) {
		Query q = null;
		if((null != appName && !"".equalsIgnoreCase(appName)) && (null!= qids && !qids.isEmpty()) ){
			q = entityManager.createQuery("SELECT t FROM AssessmentResponse t, QuestionMaster q where t.questionId = q.id and t.application= :appName and t.questionId in (:qids) order by q.iteration, q.id").setParameter("appName", appName).setParameter("qids", qids);
		}
		else if(null != appName && !"".equalsIgnoreCase(appName)){
			q = entityManager.createQuery("SELECT t FROM AssessmentResponse t, QuestionMaster q where t.questionId = q.id and t.application= :appName order by q.iteration, q.id").setParameter("appName", appName);
		}
		else if(null!= qids && !qids.isEmpty()){
			q = entityManager.createQuery("SELECT t FROM AssessmentResponse t, QuestionMaster q where  t.questionId = q.id and t.questionId in (:qids) order by t.application, q.iteration, q.id").setParameter("qids", qids);
		}
		else{
			q = entityManager.createQuery("SELECT t FROM AssessmentResponse t, QuestionMaster q where t.questionId = q.id order by t.application, q.iteration, q.id");
		}
		
		List<AssessmentResponse> results = q.getResultList();
		for (AssessmentResponse r : results) {
			r.getQuestion().updateValueMap();
		}
		return results;
	}

	public void save(AssessmentResponse response) {
		entityManager.merge(response);
		//entityManager.persist(question);
	}

	public AssessmentResponse getById(long id) {
		AssessmentResponse result = entityManager.find(AssessmentResponse.class, id);
		result.getQuestion().updateValueMap();
		return result;
	}

	public void delete(long id) {
		AssessmentResponse qMaster = getById(id);
		if (qMaster != null) {
			entityManager.remove(qMaster);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Application> getDistinctApplication(){
		List<Application> result = new ArrayList<>();
		List<Object[]> resultSet = entityManager.createNativeQuery("SELECT A.APPLICATION, ANSWERED, QUESTIONS, c.app_type FROM (SELECT APPLICATION,count(*) ANSWERED FROM ASSESSMENT_RESPONSE WHERE RESPONSE IS NOT ''  group by application) A JOIN   (SELECT APPLICATION,count(*)  QUESTIONS  FROM ASSESSMENT_RESPONSE  group by application)  B ON A.APPLICATION=B.APPLICATION join (SELECT DISTINCT  APPLICATION, APP_TYPE FROM ASSESSMENT_RESPONSE AR JOIN QUESTION_MASTER QM ON AR.QUESTION_ID =QM.ID) C on a.application = c.application").getResultList();
		if(resultSet!=null){
			for (Object[] objectArray : resultSet) {
				result.add(new Application(objectArray[0].toString(), Integer.parseInt(objectArray[1].toString()), Integer.parseInt(objectArray[2].toString()), objectArray[3].toString()));
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<ScoreCard> getAppListScoreCard() {
		List<ScoreCard> scores = new ArrayList<ScoreCard>();
		List<Object[]> resultSet = entityManager.createNativeQuery("SELECT DISTINCT  APPLICATION, APP_TYPE FROM ASSESSMENT_RESPONSE AR JOIN QUESTION_MASTER QM ON AR.QUESTION_ID =QM.ID").getResultList();
		for(Object[] row : resultSet){
			ScoreCard score = new ScoreCard();
			score.setApplicationName(row[0].toString());
			score.setType(row[1].toString());
			scores.add(score);
		}
		return scores;
	}

	@SuppressWarnings("unchecked")
	public List<TechnologyHeatmap> getAppListTechnologyHeatmap() {
		List<TechnologyHeatmap> heatmapList = new ArrayList<TechnologyHeatmap>();
		List<Object[]> resultSet = entityManager.createNativeQuery("SELECT DISTINCT  APPLICATION, APP_TYPE FROM ASSESSMENT_RESPONSE AR JOIN QUESTION_MASTER QM ON AR.QUESTION_ID =QM.ID").getResultList();
		for(Object[] row : resultSet){
			TechnologyHeatmap heatmap = new TechnologyHeatmap();
			heatmap.setApplicationName(row[0].toString());
			heatmap.setType(row[1].toString());
			heatmapList.add(heatmap);
		}
		return heatmapList;
	}
	
	@SuppressWarnings("unchecked")
	public TechnologyHeatmap getTechnologyHeatmap(TechnologyHeatmap techHeatmap, List<Long> qList){
		List<Object[]> resultSet = entityManager.createNativeQuery("SELECT t.application,t.response, t.version source_version, s.factor, s.version target_version FROM Assessment_Response t  join score_master s where t.response = s.name and t.application= :appName and t.question_Id in (:qids)").setParameter("appName", techHeatmap.getApplicationName()).setParameter("qids", qList).getResultList();
		Map<String, Long> heatmap = new HashMap<String, Long>();
		
		if(resultSet!=null){
			for (Object[] objectArray : resultSet) {
				String response = objectArray[1].toString();
				double sourceVersion = 0;
				double factor = 0;
				double targetVersion = 0;

				if(!"".equals(objectArray[2].toString()))
					sourceVersion = Double.parseDouble(objectArray[2].toString());
				if(!"".equals(objectArray[3].toString()))
					factor = Double.parseDouble(objectArray[3].toString());
				if(!"".equals(objectArray[4].toString()))
					targetVersion = Double.parseDouble(objectArray[4].toString());
				
				heatmap.put(response, Math.round((targetVersion-sourceVersion)*factor));
			}
		}
		techHeatmap.setHeatmap(heatmap);
		return techHeatmap;
	}
	
	@SuppressWarnings("unchecked")
	public long getCurrencyScore(ScoreCard scoreCard, List<Long> qList){
		long[] scores = new long[10];
		int i= 0;
		List<Object[]> resultSet = entityManager.createNativeQuery("SELECT t.application,t.response, t.version source_version, s.factor, s.version target_version FROM Assessment_Response t  join score_master s where t.response = s.name and t.application= :appName and t.question_Id in (:qids)").setParameter("appName", scoreCard.getApplicationName()).setParameter("qids", qList).getResultList();

		if(resultSet!=null){
			for (Object[] objectArray : resultSet) {
				double sourceVersion = 0;
				double factor = 0;
				double targetVersion = 0;

				if(!"".equals(objectArray[2].toString()))
					sourceVersion = Double.parseDouble(objectArray[2].toString());
				if(!"".equals(objectArray[3].toString()))
					factor = Double.parseDouble(objectArray[3].toString());
				if(!"".equals(objectArray[4].toString()))
					targetVersion = Double.parseDouble(objectArray[4].toString());
				
				scores[i] = Math.round((targetVersion-sourceVersion)*factor);
				i++;
			}
		}
		return Arrays.stream(scores).max().getAsLong();
	}
		
	@SuppressWarnings("unchecked")
	public long getCurrencyScoreBoolean(ScoreCard scoreCard, List<Long> qList){
		long[] scores = new long[10];
		int i= 0;

		List<Object[]> resultSet = entityManager.createNativeQuery("SELECT t.application,t.response, t.version source_version FROM Assessment_Response t   where t.application= :appName and t.question_Id in (:qids)").setParameter("appName", scoreCard.getApplicationName()).setParameter("qids", qList).getResultList();
		if(resultSet!=null){
			for (Object[] objectArray : resultSet) {
				if("Yes".equalsIgnoreCase(objectArray[1].toString()))
				{
					scores[i] = 90;
					i++;
				}
			}
		}
		return Arrays.stream(scores).max().getAsLong();
	}
}
