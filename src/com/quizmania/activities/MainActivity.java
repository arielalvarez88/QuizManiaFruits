package com.quizmania.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;

import com.android.vending.billing.IabHelper;
import com.quizmania.fruits.R;
import com.quizmania.utils.BillingUtil;
import com.quizmania.utils.QuizManiaActivity;
import com.quizmania.utils.StaticGlobalVariables;
import com.quizmania.utils.ViewUtils;

public class MainActivity extends Activity implements QuizManiaActivity{

	private static final String TAG = "MainActivity";
	public static String PACKAGE_NAME;
	private IabHelper billingHelper;
	private BillingUtil billingUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.template);
		StaticGlobalVariables.currentActivity = this;
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		ViewUtils.inflateContentInTemplate(this, R.layout.activity_main);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		StaticGlobalVariables.packageName = getPackageName();
		
		
	}

	
	public void navigateBack(View backGUIButton){
		onBackPressed();
	}
	
	@Override
	public void onBackPressed(){
		OnClickListener handleBackButtonNormally = new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				normalOnBackPressed();
			}
		};
		ViewUtils.showVote(this, handleBackButtonNormally);
	}

	public void normalOnBackPressed(){
		super.onBackPressed();
	}

	public void goToOptionsActivity(View button){
		Intent intent = new Intent(this, OptionsActivity.class);
		startActivity(intent);
	}
	public void goToLanguageActivity(View clickedButton) {
		Intent intent = new Intent(this, LevelChooser.class);
		startActivity(intent);
	}


	public void goToItemStoreActivity(View buyButton){
		Intent intent = new Intent(this,ItemStore.class);
		startActivity(intent);
	}
	
	
	
	@Override
	public void onDestroy() {
	   super.onDestroy();
	   if (billingUtil != null) billingUtil.dispose();
	   billingUtil = null;
	}
	


}
