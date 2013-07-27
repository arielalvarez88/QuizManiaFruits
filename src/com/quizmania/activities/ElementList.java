package com.quizmania.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizmaniafruits.R;
import com.quizmania.entities.QuizElement;
import com.quizmania.utils.QuizElementUtil;
import com.quizmania.utils.QuizElementsLoader;
import com.quizmania.utils.StaticGlobalVariables;

public class ElementList extends Activity {

	private List<QuizElement> elements;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_element_list);
		StaticGlobalVariables.language = (String) getIntent().getSerializableExtra(StaticGlobalVariables.LANGUAGE_ATTRIBUTE_NAME);
		elements = initializeQuizElements();
		createView();
	}

	
	private void createView() {
		ViewGroup scrollView = (ViewGroup) findViewById(R.id.quizElementsList);
		
		
		for(final QuizElement element: elements){
			View elementVisualRepresentation = getElementView(element);
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
		intent.putExtra("levelElements", ((ArrayList<QuizElement>) elements));
		startActivity(intent);
		
	}


	private View getElementView(QuizElement element) {
		View elementlRepresentation = getLayoutInflater().inflate(R.layout.quiz_list_element, null);
		
		TextView quizElementNameHolder = (TextView)elementlRepresentation.findViewById(R.id.listElementText);		
		quizElementNameHolder.setText(element.getLanguagueToNameMap().get(StaticGlobalVariables.language));
		
		ImageView quizElementImageHolder = (ImageView)elementlRepresentation.findViewById(R.id.listElementImage);		
		quizElementImageHolder.setImageResource(QuizElementUtil.getResourceIdFromQuizElement(this, element));
		
		return elementlRepresentation;
	}


	private List<QuizElement> initializeQuizElements() {
		try {
			elements = QuizElementsLoader
					.loadElementsFromLevel("english", this);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elements;

	}
	

}
