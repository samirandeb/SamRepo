package com.sam.assessment.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.sam.assessment.entity.QuestionMaster;

@Repository
public class AssessmentDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void create(QuestionMaster qMaster) {
		entityManager.persist(qMaster);
	}

	public void update(QuestionMaster qMaster) {
		entityManager.merge(qMaster);
	}

	@SuppressWarnings("unchecked")
	public List<QuestionMaster> getAllQuestion(String appType) {
		List<QuestionMaster> results = entityManager
				.createQuery("SELECT t FROM QuestionMaster t where t.appType = :appType order by t.iteration, t.id")
				.setParameter("appType", appType).getResultList();
		for (QuestionMaster q : results) {
			q.updateValueMap();
		}
		return results;
	}

	public void saveQuestion(QuestionMaster question) {
		entityManager.merge(question);
		// entityManager.persist(question);
	}

	public QuestionMaster getQuestionById(long id) {
		QuestionMaster q = entityManager.find(QuestionMaster.class, id);
		q.updateValueMap();

		return q;
	}

	public void delete(long id) {
		QuestionMaster qMaster = getQuestionById(id);
		if (qMaster != null) {
			entityManager.remove(qMaster);
		}
	}
}
