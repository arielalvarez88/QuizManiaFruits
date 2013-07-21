package com.quizmania.activities;

import java.io.IOException;
import java.util.List;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;

import com.example.quizmaniafruits.R;
import com.quizmaina.entities.QuizElement;
import com.quizmania.fragments.QuizMainFragment;
import com.quizmania.utils.QuizElementsLoader;

public class QuizLevelViewer extends FragmentActivity {
	List<QuizElement> elements;
	FragmentManager fragmentManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fruit_viewer);
		elements = initializeQuizElements();

		fragmentManager = getSupportFragmentManager();
		
		QuizMainFragment quizLevelFragment = new QuizMainFragment();
		quizLevelFragment.setElement(elements.get(0));

		fragmentManager.beginTransaction()
				.add(R.id.fragment_container, quizLevelFragment).commit();

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
