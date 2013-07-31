package com.quizmania.entities;

public class Answer {

	public String language;
	public String response;

	public String getLanguage() {
		return language;
	}
	
	public Answer(String language, String response) {
		super();
		this.language = language;
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
