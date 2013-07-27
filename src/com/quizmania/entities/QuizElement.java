package com.quizmania.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class QuizElement implements Serializable {
	
	@Override
	public String toString() {
		return "QuizElement [image=" + imageName + ", languagueToNameMap="
				+ languagueToNameMap + ", levels=" + levels + "]";
	}

	String imageName;
	Map<String,String> languagueToNameMap;
	private List<String> levels;
	
	
	
	public String  getImageName() {
		return imageName;
	}
	public void setImageName(String image) {
		this.imageName = image;
	}
	public Map<String, String> getLanguagueToNameMap() {
		return languagueToNameMap;
	}
	public void setLanguagueToNameMap(Map<String, String> languagueToNameMap) {
		this.languagueToNameMap = languagueToNameMap;
	}

	public List<String> getLevels() {
		
		return this.levels;
	}

	public void setLevels(List<String> levels) {
		this.levels = levels;
	}
	
	@Override
	public boolean equals(Object toCompare){
		if(!(toCompare instanceof QuizElement))
			return false;
		
		QuizElement toCompareCasted = (QuizElement) toCompare;
		
		return toCompareCasted.getImageName().equals(this.imageName);
		
	}
	
	

}
