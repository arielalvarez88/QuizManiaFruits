package com.quizmania.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonUtils {
	
	public  static List<JsonElement> jsonArrayToList(String jsonArray){		
		JsonElement element = new JsonParser().parse(jsonArray.toString());
		List<JsonElement> elements = new ArrayList<JsonElement>();
		Gson gson = new Gson();
		JsonArray values = element.getAsJsonArray();
		Iterator<JsonElement> iterator = values.iterator();

		while (iterator.hasNext()) {
			JsonElement quizElementJsonElement = iterator.next();
			elements.add(quizElementJsonElement);
		}
		
		return elements;
	}
	
	public static List transform(List<JsonElement> elements, Class targetClass){
		ArrayList returnList = new ArrayList();
		Gson gson = new Gson();
		for(JsonElement element : elements){			
			returnList.add(gson.fromJson(element, targetClass));
		}
		return returnList;
	}
	
	
	

}
