package com.quizmania.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizmaniafruits.R;
import com.quizmania.utils.StaticGlobalVariables;

public class LevelChooser extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_level);
		
		
	}

	

	public void goToLevel(View clickedButton) {
		Intent intent = new Intent(this, ElementList.class);
		intent.putExtra(StaticGlobalVariables.LANGUAGE_ATTRIBUTE_NAME, "english");
		startActivity(intent);
		
	}
}
