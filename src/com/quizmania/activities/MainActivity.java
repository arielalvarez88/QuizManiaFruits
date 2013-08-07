package com.quizmania.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.quizmaniafruits.R;
import com.google.inAppBilling.IabHelper;
import com.google.inAppBilling.IabHelper.OnIabSetupFinishedListener;
import com.google.inAppBilling.IabResult;
import com.quizmania.utils.StaticGlobalVariables;

public class MainActivity extends Activity implements OnIabSetupFinishedListener{

	private static final String TAG = "MainActivity";
	public static String PACKAGE_NAME;
	private IabHelper billingHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StaticGlobalVariables.packageName = getPackageName();
		initializeBiling();
	}



	private void initializeBiling() {
		Resources resources = getResources();
		String appPublicKey = resources.getString(R.string.appPublicKey);
		IabHelper billingHelper = new IabHelper(this, appPublicKey);
		billingHelper.startSetup(this);
	}
	
	
	
	public void goToLanguageActivity(View clickedButton) {
		Intent intent = new Intent(this, LevelChooser.class);
		startActivity(intent);
	}



	@Override
	public void onIabSetupFinished(IabResult result) {
			
	      if (!result.isSuccess()) {
	          // Oh noes, there was a problem.
	          Log.d(TAG, "Problem setting up In-app Billing: " + result);
	       }            
	      Log.d(TAG, "In-app Billing success" + result);  

	}
	
	@Override
	public void onDestroy() {
	   super.onDestroy();
	   if (billingHelper != null) billingHelper.dispose();
	   billingHelper = null;
	}


}
