package com.quizmania.utils;

import java.io.IOException;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.os.Environment;
import android.util.Log;

import com.quizmania.entities.QuizElement;

public class AnswerService extends SDCardSavableEntity implements Serializable{
	
	Map<QuizElement,Set<String>> answers;
	private static AnswerService answerSingletonInstance;
	private static String answersFilePath = Environment.getExternalStorageDirectory() + "/Android/data/" + StaticGlobalVariables.packageName + "/answers.quizmania";
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
		
		
		if(answerSingletonInstance == null){
			
			answerSingletonInstance = IOUtils.isFileSavedInSDCard(answersFilePath) ? initializeFromMemory() : new AnswerService();
			
		}
		
		
		return answerSingletonInstance;
		
		
	}
	



	private static AnswerService initializeFromMemory() {
		// TODO Auto-generated method stub
		Log.d(AnswerService.class.toString(), "Initializaing from memory!");
		try {
			
			answerSingletonInstance = (AnswerService) IOUtils.loadFromSDCard(answersFilePath);
			
			
		} catch (OptionalDataException e) {
			e.printStackTrace();
			answerSingletonInstance = new AnswerService();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			answerSingletonInstance = new AnswerService();
		} catch (IOException e) {
			e.printStackTrace();
			answerSingletonInstance = new AnswerService();
		}
		return answerSingletonInstance;
		
		
	}



	@Override
	public String getUserConfigFilePath() {
		
		return answersFilePath;
	}
	
	
}