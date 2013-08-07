package com.quizmania.activities;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizmaniafruits.R;
import com.quizmania.utils.Languages;
import com.quizmania.utils.StaticGlobalVariables;

public class LevelChooser extends Activity {

	Map<Integer,String> viewIdToLanguageMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_level);
		initializeViewToLanguageMap();
		
	}
	
	

	
	private void initializeViewToLanguageMap() {
		viewIdToLanguageMap = new HashMap<Integer, String>();
		
		viewIdToLanguageMap.put(R.id.englishLevelButton,Languages.ENGLISH.getStringReperesentation());
		viewIdToLanguageMap.put(R.id.spanishLevelButton,Languages.SPANISH.getStringReperesentation());
		viewIdToLanguageMap.put(R.id.portugueseLevelButton,Languages.PORTUGUESE.getStringReperesentation());
		viewIdToLanguageMap.put(R.id.frenchLevelButton,Languages.FRENCH.getStringReperesentation());
		
		
	}



	public void goToLevel(View clickedButton) {
		Intent intent = new Intent(this, ElementList.class);
		;
		String level = viewIdToLanguageMap.get(clickedButton.getId());
		
		intent.putExtra(StaticGlobalVariables.LEVEL_ATTRIBUTE_NAME, level);
		startActivity(intent);
		
	}
}
