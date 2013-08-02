package com.quizmania.entities;

import java.io.Serializable;
import java.util.List;

public class QuizElementNames implements Serializable{


	private List<String> names;
	

	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	@Override
	public String toString() {
		return "QuilElementNames [names=" + names + "]";
	}
	
}
