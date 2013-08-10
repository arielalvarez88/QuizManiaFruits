package com.quizmania.utils;

import java.util.ArrayList;
import java.util.List;

import com.quizmania.entities.QuizElement;

public class StaticGlobalVariables {
		
	public static String packageName;
	public static String language;
	public static final String LANGUAGE_ATTRIBUTE_NAME = "language";
	public static final String LEVEL_ATTRIBUTE_NAME = "level";
	private static List<QuizElement> levelElements;
	public static List<QuizElement> getLevelElements() {
		return levelElements;
	}
	public static void setLevelElements(List<QuizElement> elements) {
		levelElements = elements;
	}

}
