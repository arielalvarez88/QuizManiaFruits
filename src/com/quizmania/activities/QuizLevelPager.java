package com.quizmania.activities;

import java.io.IOException;
import java.util.List;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.example.quizmaniafruits.R;
import com.quizmania.entities.QuizElement;
import com.quizmania.utils.QuizElementsLoader;
import com.quizmania.utils.QuizPager;
import com.quizmania.utils.StaticGlobalVariables;

public class QuizLevelPager extends FragmentActivity {
	List<QuizElement> elements;
	FragmentManager fragmentManager;
	QuizElement initialElement;
	int currentSlide=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_level);
		initializeElementsFromIntent();
		initializeLevelPager();
	}

	


	private void initializeElementsFromIntent() {
		initialElement = (QuizElement) getIntent().getExtras().get("clickedElement");
		elements =  (List<QuizElement>) getIntent().getExtras().get("allElements");
	}




	private void initializeLevelPager() {
		fragmentManager = getSupportFragmentManager();
		QuizPager quizPager = new QuizPager(elements, fragmentManager,firstElementToShow);
		ViewPager quizPagerView = (ViewPager) findViewById(R.id.quizElementPager);
		quizPagerView.setAdapter(quizPager);
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
