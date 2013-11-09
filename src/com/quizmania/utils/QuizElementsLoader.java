package com.quizmania.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.quizmania.entities.Language;
import com.quizmania.entities.Level;
import com.quizmania.entities.QuizElement;
import com.quizmania.entities.QuizElementNames;

public class QuizElementsLoader {

	public static String QUIZ_ELEMENTS_FILE_NAME = "quiz_elements.json";
	public static List<QuizElement> loadElementsFromLevel(Level level,
			Context activity) throws IOException {

		StringBuilder fileContent = IOUtils.getAssetsFileContent(activity,QUIZ_ELEMENTS_FILE_NAME);		
		List<QuizElement> levelElements = getQuizLevelElements(level,
				fileContent);
		
		return levelElements;

	}

	private static List<QuizElement> getQuizLevelElements(Level level,
			StringBuilder fileContent) {
		
		List<QuizElement> allQuizElements= getAllQuizElementsFromFileContent(fileContent);		
		List<QuizElement> filteredByLevelList = filterQuizElementsByLevel(level,allQuizElements);
		
		return filteredByLevelList ;
						
	}

	

	private static List<QuizElement> filterQuizElementsByLevel(Level level,
			List<QuizElement> allQuizElements) {
		
		List<QuizElement> filteredLevels = new ArrayList<QuizElement>();
		for(QuizElement element : allQuizElements){
			if(element.getLevels().contains(level.getName()))
				filteredLevels.add(element);
		}
		return filteredLevels;
		
	}

	private static List<QuizElement> getAllQuizElementsFromFileContent(StringBuilder fileContent) {
		List<QuizElement> allQuizElements = new ArrayList<QuizElement>();
		List<JsonElement> quizElementJsonElements = JsonUtils.jsonArrayToList(fileContent.toString());
		Gson gson = new Gson();
		for(JsonElement quizElementJsonElement : quizElementJsonElements){		
			QuizElement quizElement = gson.fromJson(quizElementJsonElement, QuizElement.class);		
			quizElement.setLanguageToNamesMap(parseLanguageToNamesMap(gson, quizElementJsonElement));
			System.out.println("Good parsing!" + quizElement);
			allQuizElements.add(quizElement);		
		}
		return allQuizElements;
	}

	private static Map<Language,QuizElementNames> parseLanguageToNamesMap(Gson gson,
			 JsonElement quizElementJsonElement) {
		JsonObject languageToMapJsonObject = getLanguateToNamesMapJsonObject(quizElementJsonElement);
		Set<Entry<String,JsonElement>> languagueToNamesUnparsedMap = languageToMapJsonObject.entrySet();
		
		
		Map<Language, QuizElementNames> languageToNamesMap = createJavaMapFromJsonEntries(
				gson, languagueToNamesUnparsedMap);
		
		return languageToNamesMap;
	}

	private static Map<Language, QuizElementNames> createJavaMapFromJsonEntries(
			Gson gson,
			Set<Entry<String, JsonElement>> languagueToNamesUnparsedMap) {
		Map<Language,QuizElementNames> languageToNamesMap = new HashMap<Language, QuizElementNames>();
		for(Entry<String,JsonElement> languageToNameEntry : languagueToNamesUnparsedMap){
			
			Language language =  new Language(languageToNameEntry.getKey());
			QuizElementNames quizElementNames = gson.fromJson(languageToNameEntry.getValue(), QuizElementNames.class);
			languageToNamesMap.put(language, quizElementNames);
		}
		return languageToNamesMap;
	}

	private static JsonObject getLanguateToNamesMapJsonObject(
			JsonElement quizElementJsonElement) {
		JsonObject quizElementJsonObject = quizElementJsonElement.getAsJsonObject();
		JsonObject languageToMapJsonObject = quizElementJsonObject.getAsJsonObject("languageToNamesMap");
		return languageToMapJsonObject;
	}


}
