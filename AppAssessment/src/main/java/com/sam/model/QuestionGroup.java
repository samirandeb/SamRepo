package com.sam.model;

import java.util.ArrayList;
import java.util.List;

import com.sam.assessment.entity.AssessmentResponse;

public class QuestionGroup {
	private String groupName;
	private List<AssessmentResponse> responses = new ArrayList<AssessmentResponse>();

	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<AssessmentResponse> getResponses() {
		return responses;
	}
	public void setResponses(List<AssessmentResponse> responses) {
		this.responses = responses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
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
		QuestionGroup other = (QuestionGroup) obj;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		return true;
	}



}
