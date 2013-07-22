package com.quizmania.activities;

import java.io.IOException;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.quizmaniafruits.R;
import com.quizmania.entities.QuizElement;
import com.quizmania.fragments.QuizMainFragment;
import com.quizmania.utils.QuizElementsLoader;
import com.quizmania.utils.QuizPager;

public class QuizLevelViewer extends FragmentActivity {
	List<QuizElement> elements;
	FragmentManager fragmentManager;
	int currentSlide=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fruit_viewer);
		elements = initializeQuizElements();

		fragmentManager = getSupportFragmentManager();
		QuizPager quizPager = new QuizPager(elements, fragmentManager);
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
