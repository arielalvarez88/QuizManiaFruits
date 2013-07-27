package com.quizmania.activities;

import com.example.quizmaniafruits.R;
import com.quizmania.utils.StaticGlobalVariables;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StaticGlobalVariables.packageName = getPackageName();
		
	}
	
	
	
	public void goToLanguageActivity(View clickedButton) {
		Intent intent = new Intent(this, LevelChooser.class);
		startActivity(intent);
	}
	


}
