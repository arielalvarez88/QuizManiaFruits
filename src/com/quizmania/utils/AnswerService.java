package com.quizmania.utils;

import java.io.IOException;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.quizmania.entities.Language;
import com.quizmania.entities.Level;
import com.quizmania.entities.NameHints;
import com.quizmania.entities.QuizElement;

public class AnswerService extends SDCardSavableEntity implements Serializable{
	
	Map<QuizElement,Set<Language>> answers;
	Map<QuizElement,Map<Language,NameHints>> revealedHints;
	Set<Level> levelsCompleted;
	private static AnswerService answerSingletonInstance;

	public static final String ANSWERS_FILE_PATH = Environment.getExternalStorageDirectory() + "/Android/data/" + StaticGlobalVariables.packageName + "/answers.quizmania";
	private AnswerService(){	
			answers = new HashMap<QuizElement,Set<Language>>();
			revealedHints = new HashMap<QuizElement, Map<Language,NameHints>>();
			levelsCompleted = new HashSet<Level>();
			
	}
	
	
	
	public int countAnswersForLevel(Activity activity,Level level){
		int answerInLevelCounter = 0;
		Language currentLanguage = UserConfig.getInstance(activity).getLanguage();
		//TODO sOnly in QquizmaniaFruits
		currentLanguage = new Language(level.getName());
		for(QuizElement quizElement : answers.keySet()){
			if(quizElement.getLevels().contains(level.getName()) && answers.get(quizElement).contains(currentLanguage))
				answerInLevelCounter++;
		}
		return answerInLevelCounter;
	}
	
	
	public NameHints getRevealedHintsForQuizElement(QuizElement element){
		NameHints revealedHint = null;
		if(revealedHints.containsKey(element)  && revealedHints.get(element).containsKey(UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage())){
			revealedHint = revealedHints.get(element).get(UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage());
		}
		
		return revealedHint; 
	}
		
	public boolean areAllLettersRevealed(QuizElement element){
		NameHints revealedHintsForElement = getRevealedHintsForQuizElement(element);
		int lettersRevealed = revealedHintsForElement == null ? 0 : revealedHintsForElement.getLettersRevealed().size();
		int lettersInElement=0;
		char[] allLetters = element.getLanguageToNamesMap().get(UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage()).getNames().get(0).toCharArray();
		for(char letter : allLetters){
			if(letter != StaticGlobalVariables.BLANK_SPACE)
				lettersInElement ++ ;
		}
		
		return lettersRevealed >= lettersInElement;
	}
	
	public void revealAllLetters(QuizElement element){
		String elementNameInCurrentLanguage = element.getLanguageToNamesMap().get(UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage()).getNames().get(0);
		char[] answerLetters = elementNameInCurrentLanguage.toCharArray();
		
		NameHints allHints = new NameHints();
		int letterIndex = 0;
		for(char letter : answerLetters){
			allHints.revealLetter(letterIndex, letter);
			letterIndex++;
		}		
		Language currentLanguage = UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage();
		
		if(!revealedHints.containsKey(element)){
			HashMap languageToHints = new HashMap<Language, NameHints>();
			revealedHints.put(element, languageToHints);
		}
		
					
		revealedHints.get(element).put(currentLanguage, allHints);
	}
	
	public NameHints revealRandomLetter(QuizElement element, Activity androidContext){
		if(isAwnsered(element, UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage())){
			return null;
		}
		NameHints hintToReturn;
		Language language = UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage();
		boolean isNewHint = !revealedHints.containsKey(element) || !revealedHints.get(element).containsKey(language);
		String elementName = element.getLanguageToNamesMap().get(UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage()).getNames().get(0);
		
		hintToReturn = isNewHint ? new NameHints() : revealedHints.get(element).get(language);
		int randomLetterIndex; 	
		do{
			randomLetterIndex = (int) (Math.random() * (elementName.length()) ); 
													
		}while(hintToReturn.hasLetterRevealed(randomLetterIndex) || elementName.charAt(randomLetterIndex) == StaticGlobalVariables.BLANK_SPACE);
		hintToReturn.getLettersRevealed().put(randomLetterIndex, elementName.charAt(randomLetterIndex));
		Log.d("*************", "randomLetter: " + elementName.charAt(randomLetterIndex));
		saveAndConsumeHint(element, hintToReturn, androidContext);
		
		return hintToReturn;
	}

	

	public void markAsAnswered(QuizElement element,Activity androidContext) {
		String correctAnswer = element.getLanguageToNamesMap().get(UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage()).getNames().get(0);
		tryToAnswer(element, correctAnswer);
		saveToSDCard(androidContext);
		
	}


	private void saveAndConsumeHint(QuizElement element, NameHints hintToSave,Activity androidContext) {
		boolean isNewHint = !revealedHints.containsKey(element);
		Language language = UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage();
		if(!isNewHint){
			
			revealedHints.get(element).put(language,hintToSave);
			Log.d("*************", "oldHint: " + hintToSave);
		}else{
			
			HashMap<Language,NameHints> languageToHint = new HashMap<Language, NameHints>();
			languageToHint.put(language, hintToSave);			
			revealedHints.put(element, languageToHint);
			Log.d("*************", "newHint: " + hintToSave);
		}
				
		int hintsLeft = UserConfig.getInstance(StaticGlobalVariables.currentActivity).getHintsLeft();
		UserConfig.getInstance(StaticGlobalVariables.currentActivity).setHintsLeft(hintsLeft-1);
		UserConfig.getInstance(StaticGlobalVariables.currentActivity).saveToSDCard(androidContext);
		Log.d("*************", "hints Left after consumption: " + UserConfig.getInstance(StaticGlobalVariables.currentActivity).getHintsLeft());
		
			
	}
	public boolean isAwnsered(QuizElement element, Language language){
		
		boolean answerForQuizElementIsInCurrentLanguage = answers.containsKey(element) && answers.get(element).contains(language);
		return answerForQuizElementIsInCurrentLanguage; 
	}
	
	
	
	public boolean tryToAnswer(QuizElement element, String userAnswer){
		
		Language language = UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage();

		List<String> correctAnswers = element.getLanguageToNamesMap().get(language).getNames();
		
		
		boolean isACorrectAnswer = isACorrectAnswer(element, userAnswer, correctAnswers);
		
		if(isACorrectAnswer){
			placeAnswerInMapAnswer(element);
			if(checkIfCurrentLevelCompleted()){				
				levelsCompleted.add(StaticGlobalVariables.currentLevel);
			}
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
				
		answers.get(element).add(UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage());
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
			Log.d("QuizMania", answerSingletonInstance.getRevealedHints().toString());
			
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



	public Map<QuizElement, Map<Language, NameHints>> getRevealedHints() {
		return revealedHints;
	}


	public void setRevealedHints(
			Map<QuizElement, Map<Language, NameHints>> revealedHints) {
		this.revealedHints = revealedHints;
	}


	@Override
	public String getUserConfigFilePath() {
		
		return ANSWERS_FILE_PATH;
	}

	public void reset() {
		this.answerSingletonInstance = new AnswerService();		
	}

	public boolean checkIfCurrentLevelCompleted() {
		
		List<QuizElement> currentLevelElements = StaticGlobalVariables.getLevelElements();
		Language currentLanguage = UserConfig.getInstance(StaticGlobalVariables.currentActivity).getLanguage();
		int levelAnsweredElements = 0;
		for(QuizElement levelElement : currentLevelElements){
			if(answers.containsKey(levelElement) && answers.get(levelElement).contains(currentLanguage))
				levelAnsweredElements++;
		}
		return levelAnsweredElements == currentLevelElements.size();
	}
	
	public boolean checkIfLevelIsCompleted(Activity activity,Level level){
		
		return countAnswersForLevel(activity,level) == level.getLevelsQuizElementsCount();
	}
	
	public boolean isLevelComplete(Level currentLevel) {
		
		return levelsCompleted.contains(currentLevel);
	}
	
	
}