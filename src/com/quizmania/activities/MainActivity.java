package com.quizmania.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.android.vending.billing.IabHelper;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;
import com.quizmania.fruits.R;
import com.quizmania.utils.BillingUtil;
import com.quizmania.utils.QuizManiaActivity;
import com.quizmania.utils.StaticGlobalVariables;
import com.quizmania.utils.ViewUtils;

public class MainActivity extends ActionBarActivity implements QuizManiaActivity{

	private static final String TAG = "MainActivity";
	private static final String ADMOB_ID = "a15272e50eb806a";
	public static String PACKAGE_NAME;
	private IabHelper billingHelper;
	private BillingUtil billingUtil;
	private InterstitialAd interstitialAd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.template);
		StaticGlobalVariables.currentActivity = this;
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		ViewUtils.inflateContentInTemplate(this, R.layout.activity_main);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		StaticGlobalVariables.packageName = getPackageName();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prepareInterstitialAds();
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
		
		int randomNumberBetween1and10 = (int) (Math.random()*(9) + 1);
		boolean fiftyPercentChange = randomNumberBetween1and10 >= 1 || randomNumberBetween1and10 <= 5;
		
		if(fiftyPercentChange && interstitialAd.isReady()){
			interstitialAd.show();
			normalOnBackPressed();
		}else{
			ViewUtils.showVote(this, handleBackButtonNormally);
		}
		
		
	}
	
	


	public void prepareInterstitialAds() {

		interstitialAd = new InterstitialAd(this, this.ADMOB_ID);
		
		//Aqui le digo que el objeto que va a escuchar los eventos cuando llegue el Add esté listo es este mismo objeto.
		//para eso tu implementas la interfaz AdListener y le declara los métodos, ve al método onReceiveAd que se llama
		//cuando el anuncia está listo para mostrar.		
		interstitialAd.loadAd(new AdRequest());
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
	
	@Override
	public Intent getSupportParentActivityIntent(){		
		onBackPressed();
		return null;
	}


}
