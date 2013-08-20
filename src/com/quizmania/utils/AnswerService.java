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

import com.quizmania.entities.Hint;
import com.quizmania.entities.Language;
import com.quizmania.entities.QuizElement;

public class AnswerService extends SDCardSavableEntity implements Serializable{
	
	Map<QuizElement,Set<Language>> answers;
	Map<QuizElement,Map<Language,Hint>> revealedHints;
	private static AnswerService answerSingletonInstance;
	public static final String ANSWERS_FILE_PATH = Environment.getExternalStorageDirectory() + "/Android/data/" + StaticGlobalVariables.packageName + "/answers.quizmania";
	private AnswerService(){	
			answers = new HashMap<QuizElement,Set<Language>>();
			revealedHints = new HashMap<QuizElement, Map<Language,Hint>>();
	}
		
	public Hint revealRandomLetter(QuizElement element, Language language){
		
		Hint hintToReturn; 
		boolean isNewHint = !revealedHints.containsKey(element) || !revealedHints.get(element).containsKey(language);
		String elementName = element.getLanguageToNamesMap().get(UserConfig.getInstance().getLanguage()).getNames().get(0);
		hintToReturn = isNewHint ? new Hint() : revealedHints.get(element).get(language);
		int randomLetterIndex; 	
		do{
			randomLetterIndex = (int) (Math.random() * (elementName.length() +1) ); 
													
		}while(hintToReturn.hasLetterRevealed(randomLetterIndex));
		hintToReturn.getLettersRevealed().put(randomLetterIndex, elementName.charAt(randomLetterIndex));
		return hintToReturn;
							
	}
	public boolean isAwnsered(QuizElement element, Language language){
		
		boolean answerForQuizElementIsInCurrentLanguage = answers.containsKey(element) && answers.get(element).contains(language);
		return answerForQuizElementIsInCurrentLanguage; 
	}
	
	public boolean tryToAnswer(QuizElement element, String userAnswer){
		List<String> correctAnswers = element.getLanguageToNamesMap().get(UserConfig.getInstance().getLanguage()).getNames();
		
		
		boolean isACorrectAnswer = isACorrectAnswer(element, userAnswer, correctAnswers);
		
		if(isACorrectAnswer){
			placeAnswerInMapAnswer(element);
		}else{
			answers.remove(element);
		}
		
		return isACorrectAnswer;
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
			answers.put(element,new HashSet<Language>());
		}
				
		answers.get(element).add(UserConfig.getInstance().getLanguage());
	}
	
	
	public static AnswerService getAnswerService(){
		
		
		if(answerSingletonInstance == null){
			
			answerSingletonInstance = IOUtils.isFileSavedInSDCard(ANSWERS_FILE_PATH) ? initializeFromMemory() : new AnswerService();
			
		}
		
		
		return answerSingletonInstance;
		
		
	}
	



	private static AnswerService initializeFromMemory() {
		
		Log.d(AnswerService.class.toString(), "Initializaing from memory!");
		try {
			
			answerSingletonInstance = (AnswerService) IOUtils.loadFromSDCard(ANSWERS_FILE_PATH);
			
			
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
		
		return ANSWERS_FILE_PATH;
	}

	public void reset() {
		this.answerSingletonInstance = new AnswerService();
		
	}
	
	
}