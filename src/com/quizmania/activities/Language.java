package com.quizmania.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizmaniafruits.R;

public class Language extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_language);
		
	}

	

	public void goToFruitViewerActivity(View clickedButton) {
		Intent intent = new Intent(this, QuizLevelViewer.class);
		startActivity(intent);
	}
}
