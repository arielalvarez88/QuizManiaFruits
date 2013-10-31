package com.quizmania.activities;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizmania.entities.Language;
import com.quizmania.entities.Level;
import com.quizmania.entities.QuizElement;
import com.quizmania.fruits.R;
import com.quizmania.utils.AnswerService;
import com.quizmania.utils.QuizElementUtil;
import com.quizmania.utils.QuizElementsLoader;
import com.quizmania.utils.QuizManiaActivity;
import com.quizmania.utils.StaticGlobalVariables;
import com.quizmania.utils.UserConfig;
import com.quizmania.utils.ViewUtils;

public class ElementList extends ActionBarActivity implements QuizManiaActivity{

	
	Map<QuizElement,View> quizElementToViewMap;
	Level level;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template);
		StaticGlobalVariables.currentActivity = this;
		quizElementToViewMap = new HashMap<QuizElement, View>();
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		ViewUtils.inflateContentInTemplate(this, R.layout.activity_element_list);
		level = (Level) getIntent().getSerializableExtra(StaticGlobalVariables.LEVEL_ATTRIBUTE_NAME);
		
		//In QuizMania Fruits, the language is the level.
		Language language = new Language(level.getName());
		
		UserConfig.getInstance(this).setLanguage(language);
		
		StaticGlobalVariables.setLevelElements(initializeQuizElements()) ;		
		createView();
		showOrHideNamesDependingIfElementIsAnswered();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	
	private void createView() {
		ViewGroup scrollView = (ViewGroup) findViewById(R.id.quizElementsList);
		scrollView.removeAllViews();
		StringBuilder iconNameStringBuilder = new StringBuilder();
		
		for(final QuizElement element: StaticGlobalVariables.getLevelElements()){
			View elementVisualRepresentation = getElementView(element,iconNameStringBuilder);
			quizElementToViewMap.put(element, elementVisualRepresentation);
			elementVisualRepresentation.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					changeToSliderView(element);
					
				}
			});
			
			scrollView.addView(elementVisualRepresentation);
			
		}
		
		
		
	}


	protected void changeToSliderView(QuizElement element) {
		Intent intent = new Intent(this, QuizLevelPager.class);
		intent.putExtra("clickedElement", element);
		
		startActivity(intent);
		
	}
	
	
	@Override
	public void onResume(){
		super.onResume();
		createView();
		showOrHideNamesDependingIfElementIsAnswered();
	}


	private View getElementView(QuizElement element, StringBuilder iconNameStringBuilder) {
		View elementlRepresentation = getLayoutInflater().inflate(R.layout.quiz_list_element, null);
		
		TextView quizElementNameHolder = (TextView)elementlRepresentation.findViewById(R.id.listElementText);
		String elementPrincipalName= element.getLanguageToNamesMap().get(UserConfig.getInstance(this).getLanguage()).getNames().get(0);
		quizElementNameHolder.setText(elementPrincipalName);
					
		ImageView quizElementImageHolder = (ImageView)elementlRepresentation.findViewById(R.id.listElementImage);
		
		iconNameStringBuilder.append("ic_").append(element.getImageName());
		quizElementImageHolder.setImageResource(QuizElementUtil.getResourceIdFromResourceName(this.getResources(), iconNameStringBuilder.toString()));
		iconNameStringBuilder.setLength(0);
		return elementlRepresentation;
	}


	private void showOrHideNamesDependingIfElementIsAnswered() {
		
		for(QuizElement element : quizElementToViewMap.keySet()){
			boolean isFruitAnswered = AnswerService.getAnswerService().isAwnsered(element, UserConfig.getInstance(this).getLanguage());
			int fruitNameVisibility = isFruitAnswered? View.VISIBLE : View.INVISIBLE;
			
			quizElementToViewMap.get(element).findViewById(R.id.listElementText).setVisibility(fruitNameVisibility);
			
		}
		
	}


	private List<QuizElement> initializeQuizElements() {
		try {
			return QuizElementsLoader
					.loadElementsFromLevel(level, this);			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;

	}


	@Override
	public void navigateBack(View view) {
		super.onBackPressed();
		
	}
	

}
