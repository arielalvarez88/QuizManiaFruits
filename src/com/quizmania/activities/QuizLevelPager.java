package com.quizmania.activities;

import java.io.IOException;
import java.util.List;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

import com.example.quizmaniafruits.R;
import com.quizmania.entities.QuizElement;
import com.quizmania.utils.QuizElementsLoader;
import com.quizmania.utils.QuizPager;
import com.quizmania.utils.StaticGlobalVariables;

public class QuizLevelPager extends FragmentActivity implements OnKeyListener{
	List<QuizElement> levelElements;
	FragmentManager fragmentManager;
	QuizElement initialElement;
	int initialSlide=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_level);
		initializeElementsFromIntent();		
		calculateInitialSlide();
		initializeLevelPager();
		
		
	}

	


	private void calculateInitialSlide() {
		initialSlide = levelElements.indexOf(initialElement);
		
	}
	
	private void initializeElementsFromIntent() {
		initialElement = (QuizElement) getIntent().getExtras().get("clickedElement");
		levelElements =  (List<QuizElement>) getIntent().getExtras().get("levelElements");
	}




	private void initializeLevelPager() {
		fragmentManager = getSupportFragmentManager();
		QuizPager quizPager = new QuizPager(levelElements, fragmentManager);
		ViewPager quizPagerView = (ViewPager) findViewById(R.id.quizElementPager);		
		quizPagerView.setAdapter(quizPager);
		quizPagerView.setCurrentItem(initialSlide);
		
		
		
	}
	
	
	
	private List<QuizElement> initializeQuizElements() {
		try {
			levelElements = QuizElementsLoader
					.loadElementsFromLevel("english", this);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return levelElements;

	}




	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
