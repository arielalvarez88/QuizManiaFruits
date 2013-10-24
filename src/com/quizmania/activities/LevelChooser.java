package com.quizmania.activities;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.quizmania.entities.Language;
import com.quizmania.entities.Level;
import com.quizmania.fruits.R;
import com.quizmania.utils.AnswerService;
import com.quizmania.utils.IOUtils;
import com.quizmania.utils.JsonUtils;
import com.quizmania.utils.QuizElementsLoader;
import com.quizmania.utils.QuizManiaActivity;
import com.quizmania.utils.StaticGlobalVariables;
import com.quizmania.utils.UserConfig;
import com.quizmania.utils.ViewUtils;

public class LevelChooser extends ActionBarActivity implements QuizManiaActivity, OnClickListener{

	Map<String,Level> languageLabelToLanguageObjectMap;
	ProgressDialog loadingDialog;
	private List<Level> levels;
	private static String levelsFileName = "levels.json";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.template);
		
		StaticGlobalVariables.currentActivity = this;
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		ViewUtils.inflateContentInTemplate(this, R.layout.activity_choose_level);
		try {
			initializeViewWithLevels();
		} catch (IOException e) {
		
		
		}
		initializeLoadingDialog();

        
    }
	
	

	private void initializeViewWithLevels() throws IOException {
		levels = getLevelsFromFile();
		languageLabelToLanguageObjectMap= new HashMap<String, Level>();
		for(Level level : levels){
			languageLabelToLanguageObjectMap.put(level.getLabel(), level);	
		}
				
		constructAndInflateListWithLevels(levels);
	}



	private void constructAndInflateListWithLevels(List<Level> levels) {
		
		ViewGroup levelsHolder = (ViewGroup)findViewById(R.id.levelsHolder);
		levelsHolder.removeAllViews();
		for(Level level : levels){			
			View levelListItem = generateLevelListItemView(level);
			
			levelsHolder.addView(levelListItem);
			levelListItem.setOnClickListener(this);
		}
		

	}



	private View generateLevelListItemView(Level level) {
		View levelListItem = getLayoutInflater().inflate(R.layout.level_list_item, null);			
		TextView levelNameTextView = (TextView)levelListItem.findViewById(R.id.levelName);
		TextView levelAnswerCountView = (TextView)levelListItem.findViewById(R.id.levelAnswerCount);
		levelAnswerCountView.setText(getLevelAnswersVsTotal(level));
		levelNameTextView.setText(level.getLabel());
		
		if(!AnswerService.getAnswerService().checkIfLevelIsCompleted(this,level)){
			levelListItem.findViewById(R.id.levelTrofee).setVisibility(View.INVISIBLE);
		}
		return levelListItem;
	}



	private CharSequence getLevelAnswersVsTotal(Level level) {
		int answersInThisLevel = AnswerService.getAnswerService().countAnswersForLevel(this,level);
		
		return answersInThisLevel + "/" +level.getLevelsQuizElementsCount();
	}



	private List<Level> getLevelsFromFile() throws IOException {
		StringBuilder builder = IOUtils.getAssetsFileContent(this, levelsFileName);		
		List<JsonElement> levelsElements = JsonUtils.jsonArrayToList(builder.toString());		
		levels = (List<Level>) JsonUtils.transform(levelsElements, Level.class);
		countAndSetLevelQuizElements(levels);
		return levels;
	}



	private void countAndSetLevelQuizElements(List<Level> levels) {
		StringBuilder quizElementsFile = null;
		try {
			quizElementsFile = IOUtils.getAssetsFileContent(this, QuizElementsLoader.QUIZ_ELEMENTS_FILE_NAME);
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
		Log.d(StaticGlobalVariables.packageName, "Elements file content: " + quizElementsFile.toString() );
		for(Level level : levels){
			Pattern levelNamePattern = Pattern.compile(level.getName());
			Matcher levelNameMatcher = levelNamePattern.matcher(quizElementsFile.toString());
			int levelsQuizElementsCount = 0;
			while(levelNameMatcher.find()){
				levelsQuizElementsCount++;
			}
			level.setLevelsQuizElementsCount(levelsQuizElementsCount);
			Log.d(StaticGlobalVariables.packageName, "Counts for level: " + level.getName() + levelsQuizElementsCount);			
		}

	}

	private void initializeLoadingDialog() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setTitle(getString(R.string.loadingHeader));
        loadingDialog.setMessage(getString(R.string.loadingText));
		
	}



	public void goToLevel(View clickedButton) {
		Intent intent = new Intent(this, ElementList.class);		
		Log.d(StaticGlobalVariables.packageName, "clicked Level id: " + clickedButton.hashCode());
		TextView levelLabelView = (TextView)clickedButton.findViewById(R.id.levelName);
		String levelLabel = (String) levelLabelView.getText();
		Level level = 	(Level) languageLabelToLanguageObjectMap.get(levelLabel);		
		StaticGlobalVariables.currentLevel = level;
		//Only for QuizManiaFruits
		UserConfig.getInstance(this).setLanguage(new Language(level.getName()));
		intent.putExtra(StaticGlobalVariables.LEVEL_ATTRIBUTE_NAME, level);
		loadingDialog.show();		
		startActivity(intent);
				
	}

	@Override
	public void onResume(){
		super.onResume();
		loadingDialog.dismiss();
		constructAndInflateListWithLevels(levels);
	}
	
	@Override
	public void navigateBack(View view) {
		super.onBackPressed();
		
	}



	@Override
	public void onClick(View v) {
		goToLevel(v);
		
	}



}
