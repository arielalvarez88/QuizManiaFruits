package com.quizmania.activities;


import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.quizmania.entities.QuizElement;
import com.quizmania.fragments.QuizMainFragment;
import com.quizmania.fruits.R;
import com.quizmania.utils.QuizManiaActivity;
import com.quizmania.utils.QuizPager;
import com.quizmania.utils.StaticGlobalVariables;
import com.quizmania.utils.ViewUtils;

public class QuizLevelPager extends ActionBarActivity implements QuizManiaActivity, OnPageChangeListener{
	
	FragmentManager fragmentManager;
	QuizElement initialElement;
	int initialSlide=0;
	private QuizPager quizPager;
	private ViewPager quizPagerView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_level);
		StaticGlobalVariables.currentActivity = this;
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		//ViewUtils.inflateContentInTemplate(this, R.layout.activity_quiz_level);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		initializeElementsFromIntent();		
		calculateInitialSlide();
		initializeLevelPager();
		ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
		ViewUtils.showToast(this, getString(R.string.slideForNext), Gravity.CENTER);
		
	}


	
	
		
	


	private void calculateInitialSlide() {
		initialSlide = StaticGlobalVariables.getLevelElements().indexOf(initialElement);
		
	}
	
	private void initializeElementsFromIntent() {
		initialElement = (QuizElement) getIntent().getExtras().get("clickedElement");
	
	}


	 public Intent getSupportParentActivityIntent(){
		 Log.d("***TEST", "cliiiick!!!!!!!!");
		 Intent parentIntent = new Intent(this,ElementList.class);
		 parentIntent.putExtra(StaticGlobalVariables.LEVEL_ATTRIBUTE_NAME, StaticGlobalVariables.currentLevel);
		 return parentIntent;
	 }

	private void initializeLevelPager() {
		fragmentManager = getSupportFragmentManager();
		this.quizPager = new QuizPager(StaticGlobalVariables.getLevelElements(), fragmentManager);
		this.quizPagerView = (ViewPager) findViewById(R.id.quizElementPager);		
		quizPagerView.setAdapter(quizPager);
		quizPagerView.setOnPageChangeListener(this);
		quizPagerView.setCurrentItem(initialSlide);
		
		
		
	}


	@Override
	public void navigateBack(View view) {
		super.onBackPressed();
		
	}


	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageSelected(int slideNumber) {
		QuizMainFragment currentFragment = (QuizMainFragment) quizPager.instantiateItem(quizPagerView, slideNumber);
		currentFragment.refreshHintButton();
		
	}
	
	
	
	 


}
