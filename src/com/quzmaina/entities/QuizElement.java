package com.quzmaina.entities;

import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlSerializer;

import android.widget.ImageView;


public class QuizElement {
	
	@Override
	public String toString() {
		return "QuizElement [image=" + imageName + ", languagueToNameMap="
				+ languagueToNameMap + ", levels=" + levels + "]";
	}

	String imageName;
	Map<String,String> languagueToNameMap;
	private List<String> levels;
	
	
	
	public String  getImage() {
		return imageName;
	}
	public void setImage(String image) {
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
	
	

}
