package com.quizmania.activities;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.quizmania.entities.Language;
import com.quizmania.fruits.R;
import com.quizmania.utils.QuizManiaActivity;
import com.quizmania.utils.StaticGlobalVariables;
import com.quizmania.utils.ViewUtils;

public class LevelChooser extends ActionBarActivity implements QuizManiaActivity{

	Map<Integer,String> viewIdToLanguageMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template);
		StaticGlobalVariables.currentActivity = this;
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		ViewUtils.inflateContentInTemplate(this, R.layout.activity_choose_level);
		initializeViewToLanguageMap();
		
		
	}
	
	

	
	private void initializeViewToLanguageMap() {
		viewIdToLanguageMap = new HashMap<Integer, String>();
		
		viewIdToLanguageMap.put(R.id.englishLevelButton,Language.ENGLISH);
		viewIdToLanguageMap.put(R.id.spanishLevelButton,Language.SPANISH);
		viewIdToLanguageMap.put(R.id.portugueseLevelButton,Language.PORTUGUESE);
		viewIdToLanguageMap.put(R.id.frenchLevelButton,Language.FRENCH);
		
		
	}



	public void goToLevel(View clickedButton) {
		Intent intent = new Intent(this, ElementList.class);
		;
		String level = viewIdToLanguageMap.get(clickedButton.getId());
		StaticGlobalVariables.currentLevel = level;
		intent.putExtra(StaticGlobalVariables.LEVEL_ATTRIBUTE_NAME, level);
		startActivity(intent);
		
	}




	@Override
	public void navigateBack(View view) {
		super.onBackPressed();
		
	}
}
