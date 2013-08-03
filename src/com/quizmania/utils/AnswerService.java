package com.quizmania.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.text.Editable;

import com.quizmania.entities.QuizElement;

public class AnswerService {
	
	Map<QuizElement,Set<String>> answers;
	private static AnswerService service;
	private AnswerService(){
	
			answers = new HashMap<QuizElement,Set<String>>();
		
	}
		
	public boolean isAwnsered(QuizElement element, String language){
		
		boolean answerForQuizElementIsInCurrentLanguage = answers.containsKey(element) && answers.get(element).contains(language);
		return answerForQuizElementIsInCurrentLanguage; 
	}
	

	public boolean tryToAnswer(QuizElement element, String userAnswer){
		List<String> correctAnswers = element.getLanguageToNamesMap().get(StaticGlobalVariables.language).getNames();
		
		
		boolean isACorrectAnswer = isACorrectAnswer(element, userAnswer, correctAnswers);
		
		if(isACorrectAnswer){
			placeAnswerInMapAnswer(element);
		}else{
			removeAnswerInMapAnser(element);
		}
		
		return isACorrectAnswer;
	}

	private void removeAnswerInMapAnser(QuizElement element) {
		// TODO Auto-generated method stub
		answers.remove(element);
	}

	private boolean isACorrectAnswer(QuizElement element, String userAnswer,
			List<String> correctAnswers) {
		userAnswer = userAnswer.trim();
		for(String correctAnswerInCurrentLanguage: correctAnswers){
			if(correctAnswerInCurrentLanguage.equalsIgnoreCase(userAnswer)){				
				return true;
			}
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
			
			System.out.println("Initialized AnswerService");
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