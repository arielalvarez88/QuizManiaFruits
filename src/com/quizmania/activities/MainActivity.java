package com.quizmania.activities;

import com.example.quizmaniafruits.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	
	
	public void goToLanguageActivity(View clickedButton) {
		Intent intent = new Intent(this, Language.class);
		startActivity(intent);
	}
	


}
