package com.quizmania.activities;

import android.app.Activity;
import android.content.Intent;
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
		ViewUtils.inflateContentInTemplate(this, R.layout.activity_main);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		StaticGlobalVariables.packageName = getPackageName();
		
	}

	
	public void navigateBack(View backGUIButton){
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
