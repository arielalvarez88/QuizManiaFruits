package com.quizmania.entities;

import java.io.Serializable;

public class Answer implements Serializable{

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

	@Override
	public String toString() {
		return "Answer [language=" + language + ", response=" + response + "]";
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
