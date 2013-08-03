package com.quizmania.activities;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizmaniafruits.R;
import com.quizmania.entities.QuizElement;
import com.quizmania.utils.AnswerService;
import com.quizmania.utils.QuizElementUtil;
import com.quizmania.utils.QuizElementsLoader;
import com.quizmania.utils.StaticGlobalVariables;

public class ElementList extends Activity {

	
	Map<QuizElement,View> quizElementToViewMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		quizElementToViewMap = new HashMap<QuizElement, View>();
		setContentView(R.layout.activity_element_list);
		StaticGlobalVariables.language = (String) getIntent().getSerializableExtra(StaticGlobalVariables.LEVEL_ATTRIBUTE_NAME);
		StaticGlobalVariables.setLevelElements(initializeQuizElements()) ;		
		createView();
		showOrHideNamesDependingIfElementIsAnswered();
	}

	
	private void createView() {
		ViewGroup scrollView = (ViewGroup) findViewById(R.id.quizElementsList);
		StringBuilder iconNameStringBuilder = new StringBuilder();
		
		for(final QuizElement element: StaticGlobalVariables.getLevelElements()){
			View elementVisualRepresentation = getElementView(element,iconNameStringBuilder);
			quizElementToViewMap.put(element, elementVisualRepresentation);
			elementVisualRepresentation.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					System.out.println("click?");
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
		showOrHideNamesDependingIfElementIsAnswered();
	}


	private View getElementView(QuizElement element, StringBuilder iconNameStringBuilder) {
		View elementlRepresentation = getLayoutInflater().inflate(R.layout.quiz_list_element, null);
		
		TextView quizElementNameHolder = (TextView)elementlRepresentation.findViewById(R.id.listElementText);
		String elementPrincipalName= element.getLanguageToNamesMap().get(StaticGlobalVariables.language).getNames().get(0);
		quizElementNameHolder.setText(elementPrincipalName);
					
		ImageView quizElementImageHolder = (ImageView)elementlRepresentation.findViewById(R.id.listElementImage);
		
		iconNameStringBuilder.append("ic_").append(element.getImageName());
		quizElementImageHolder.setImageResource(QuizElementUtil.getResourceIdFromResourceName(this.getResources(), iconNameStringBuilder.toString()));
		iconNameStringBuilder.setLength(0);
		return elementlRepresentation;
	}


	private void showOrHideNamesDependingIfElementIsAnswered() {
		
		for(QuizElement element : quizElementToViewMap.keySet()){
			boolean isFruitAnswered = AnswerService.getAnswerService().isAwnsered(element, StaticGlobalVariables.language);
			int fruitNameVisibility = isFruitAnswered? View.VISIBLE : View.INVISIBLE;
			
			quizElementToViewMap.get(element).findViewById(R.id.listElementText).setVisibility(fruitNameVisibility);
			
		}
		
	}


	private List<QuizElement> initializeQuizElements() {
		try {
			return QuizElementsLoader
					.loadElementsFromLevel("english", this);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	

}
