package com.quizmania.entities;

import java.io.Serializable;

public class Language implements Serializable{

	public String languageName;
		
	public static final String ENGLISH = "english";
	public static final String PORTUGUESE = "portuguese";
	public static final String SPANISH= "spanish";
	public static final String FRENCH= "french";

	public Language(String languageName){
		this.languageName = languageName;
	}
	
	@Override
	public String toString() {
		return "Language [languageName=" + languageName + "]";
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	
	public boolean equals(Object compare){
		if(!(compare instanceof Language))
			return false;
		Language compareCasted = (Language) compare;
		return compareCasted.getLanguageName().equals(this.languageName);
	}
	public int hashCode(){
		return languageName.hashCode();
	}
			

}
