package com.sam.model;

import java.util.ArrayList;
import java.util.List;

public class ScoreCardAppGroup {

	private String applicationGroup;
	private List<ScoreCard> scoreCards = new ArrayList<>();
	
	public String getApplicationGroup() {
		return applicationGroup;
	}
	public void setApplicationGroup(String applicationGroup) {
		this.applicationGroup = applicationGroup;
	}
	public List<ScoreCard> getScoreCards() {
		return scoreCards;
	}
	public void setScoreCards(List<ScoreCard> scoreCards) {
		this.scoreCards = scoreCards;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicationGroup == null) ? 0 : applicationGroup.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreCardAppGroup other = (ScoreCardAppGroup) obj;
		if (applicationGroup == null) {
			if (other.applicationGroup != null)
				return false;
		} else if (!applicationGroup.equals(other.applicationGroup))
			return false;
		return true;
	}
	
	
	
	
}
