package com.sam.model;

public class Application {

	String name;
	String type;
	int answered;
	int questions;

	public Application(String name, int answered, int questions, String type) {
		this.name = name;
		this.answered = answered;
		this.questions = questions;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAnswered() {
		return answered;
	}

	public void setAnswered(int answered) {
		this.answered = answered;
	}

	public int getQuestions() {
		return questions;
	}

	public void setQuestions(int questions) {
		this.questions = questions;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}
