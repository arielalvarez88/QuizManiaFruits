package com.quizmania.activities;

import com.quizmania.fruits.R;
import com.quizmania.fruits.R.layout;
import com.quizmania.fruits.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ChooseGame extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_game);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_game, menu);
		return true;
	}

}
