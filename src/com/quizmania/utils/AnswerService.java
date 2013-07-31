package com.quizmania.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.text.Editable;

import com.quizmania.entities.QuizElement;

public class AnswerService {
	
	Map<QuizElement,Set<String>> answers;
	private static AnswerService service;
	private AnswerService(){
		
	}
	
	public void initiazlize(){
		if(answers ==null){
			answers = new HashMap<QuizElement,Set<String>>();
		}
	}
	
	public boolean isAwnsered(QuizElement element, String language){
		
		boolean answerForQuizElementIsInCurrentLanguage = answers.containsKey(element) && answers.get(element).contains(language);
		return answerForQuizElementIsInCurrentLanguage; 
	}
	

	public boolean setAnswer(QuizElement element, String userAnswer){
		String correctAnswerInCurrentLanguage = element.getLanguagueToNameMap().get(StaticGlobalVariables.language);
		if(correctAnswerInCurrentLanguage.equals(userAnswer)){
			placeAnswerInMapAnswer(element);
			return true;
		}
		
		return false;
		
	}
	
	private void placeAnswerInMapAnswer(QuizElement element) {
		if(!answers.containsKey(element)){
			answers.put(element,new HashSet<String>());
		}
				
		answers.get(element).add(StaticGlobalVariables.language);
	}
	
	
	public static AnswerService getAnswerService(){
		
		if(service == null && isAnswerServiceInSDCard()){
			initializeFromMemory();			
		}
		if(service == null && !isAnswerServiceInSDCard()){
			service = new AnswerService();
		}
		
		
		return service;
		
		
	}
	



	private static void initializeFromMemory() {
		// TODO Auto-generated method stub
		
	}

	private static boolean isAnswerServiceInSDCard() {
		// TODO Auto-generated method stub
		return false;
	}
}