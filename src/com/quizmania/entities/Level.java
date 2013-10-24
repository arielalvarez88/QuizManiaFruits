package com.quizmania.entities;

import java.io.Serializable;

import android.R.bool;

public class Level implements Serializable{
	@Override
	public String toString() {
		return "Level [name=" + name + ", label=" + label + "]";
	}

	private String name;
	private String label;
	private int levelsQuizElementsCount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLabel() {
		return label;
	}	
	public void setLabel(String label) {
		this.label = label;
	}

	public int getLevelsQuizElementsCount() {
		return levelsQuizElementsCount;
	}
	public void setLevelsQuizElementsCount(int levelsQuizElementsCount) {
		this.levelsQuizElementsCount = levelsQuizElementsCount;
	}
	
	
	@Override
	public boolean equals(Object o){
		if(! (o instanceof Level) )
			return false;
		Level casted  = (Level) o;
		return casted.getName().equals(this.name);
		
	}
	
	@Override
	public int hashCode(){
		return name.hashCode();
	}
	
}
