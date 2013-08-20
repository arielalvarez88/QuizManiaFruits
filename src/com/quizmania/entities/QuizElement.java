package com.quizmania.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QuizElement implements Serializable {

	@Override
	public String toString() {
		return "QuizElement [image=" + imageName + ", languagueToNameMap="
				+ languageToNamesMap + ", levels=" + levels + "]";
	}

	String imageName;
	transient Map<Language, QuizElementNames> languageToNamesMap;
	private List<String> levels;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String image) {
		this.imageName = image;
	}

	public List<String> getLevels() {

		return this.levels;
	}



	public Map<Language, QuizElementNames> getLanguageToNamesMap() {
		return languageToNamesMap;
	}

	public void setLanguageToNamesMap(
			Map<Language, QuizElementNames> languageToNamesMap) {
		this.languageToNamesMap = languageToNamesMap;
	}

	public void setLevels(List<String> levels) {
		this.levels = levels;
	}

	@Override
	public boolean equals(Object toCompare) {
		if (!(toCompare instanceof QuizElement))
			return false;

		QuizElement toCompareCasted = (QuizElement) toCompare;

		return toCompareCasted.getImageName().equals(this.imageName);

	}
	
	@Override
	public int hashCode(){
		return imageName.hashCode();
	}

}
