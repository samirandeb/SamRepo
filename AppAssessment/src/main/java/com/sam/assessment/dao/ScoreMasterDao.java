package com.sam.assessment.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.sam.assessment.entity.ScoreMaster;

@Repository
public class ScoreMasterDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void create(ScoreMaster sMaster) {
		entityManager.persist(sMaster);
	}

	public void update(ScoreMaster sMaster) {
		entityManager.merge(sMaster);
	}

	@SuppressWarnings("unchecked")
	public List<ScoreMaster> getAllScoreMaster()
	{
		List<ScoreMaster> scores = new ArrayList<ScoreMaster>();
		List<Object[]> rows = entityManager.createQuery("SELECT name ,type ,version ,factor  FROM ScoreMaster").getResultList();
		for(Object[] row:rows)
		{
			ScoreMaster score = new ScoreMaster();
			score.setName(row[0].toString());
			score.setType(row[1].toString());
			score.setVersion(row[2].toString());
			score.setFactor(Double.parseDouble(row[3].toString()));
			scores.add(score);
		}
		return scores;
	}
}
