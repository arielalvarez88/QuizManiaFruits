package com.quizmania.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.quizmania.entities.QuizElement;

public class QuizElementsLoader {

	public static List<QuizElement> loadElementsFromLevel(String level,
			Context activity) throws IOException {

		StringBuilder fileContent = getQuizElementsDeclarationsFileContent(activity);		
		List<QuizElement> levelElements = getQuizLevelElements(level,
				fileContent);
		return levelElements;

	}

	private static List<QuizElement> getQuizLevelElements(String level,
			StringBuilder fileContent) {
		
		List<QuizElement> allQuizElements= getAllQuizElementsAsJsons(fileContent);
		fillAutocompleteSuggestionsWithAllElements(allQuizElements);
		List<QuizElement> filteredByLevelList = filterQuizElementsByLevel(level,allQuizElements);
		
		return filteredByLevelList ;
						
	}

	

	private static void fillAutocompleteSuggestionsWithAllElements(
			List<QuizElement> allQuizElements) {
		
			StaticGlobalVariables.quizSuggestions = new ArrayList<String>();
			for(QuizElement quizElement : allQuizElements){
				StaticGlobalVariables.quizSuggestions.add(quizElement.getLanguagueToNameMap().get(StaticGlobalVariables.language));	
			}
			Collections.sort(StaticGlobalVariables.quizSuggestions);
			
			
		
		
	}

	private static List<QuizElement> filterQuizElementsByLevel(String level,
			List<QuizElement> allQuizElements) {
		
		List<QuizElement> filteredLevels = new ArrayList<QuizElement>();
		for(QuizElement element : allQuizElements){
			if(element.getLevels().contains(level))
				filteredLevels.add(element);
		}
		return filteredLevels;
		
	}

	private static List<QuizElement> getAllQuizElementsAsJsons(StringBuilder fileContent) {
		List<QuizElement> allQuizElementsAsJsons = new ArrayList<QuizElement>();
		JsonElement element = new JsonParser().parse(fileContent.toString());
		Gson gson = new Gson();
		JsonArray values = element.getAsJsonArray();
		Iterator iterator = values.iterator();

		while (iterator.hasNext()) {
			
			QuizElement quizElement= gson.fromJson((JsonObject)iterator.next(),QuizElement.class);
			System.out.println("Good parsing!" + quizElement);
			allQuizElementsAsJsons.add(quizElement);		
		}
		return allQuizElementsAsJsons;
	}

	private static StringBuilder getQuizElementsDeclarationsFileContent(
			Context activity) throws IOException {

		AssetManager assetManager = activity.getAssets();
		InputStream stream = assetManager.open("quiz_elements.json");
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		StringBuilder fileContent = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			fileContent.append(line);
		}
		reader.close();
		return fileContent;
	}
}
