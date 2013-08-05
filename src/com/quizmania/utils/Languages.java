package com.quizmania.utils;

public enum Languages {
	
	ENGLISH("english"),
	PORTUGUESE("portuguese"),
	SPANISH("spanish"),
	FRENCH("french");

	private String stringReperesentation;
	
	public String getStringReperesentation() {
		return stringReperesentation;
	}

	public void setStringReperesentation(String stringReperesentation) {
		this.stringReperesentation = stringReperesentation;
	}

	private Languages(String stringReperesentation){
		
	}
}
