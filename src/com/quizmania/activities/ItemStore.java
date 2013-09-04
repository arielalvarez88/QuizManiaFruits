package com.quizmania.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.quizmania.fruits.R;
import com.quizmania.utils.BillingEventListener;
import com.quizmania.utils.BillingUtil;
import com.quizmania.utils.QuizManiaActivity;
import com.quizmania.utils.QuizManiaBillingEventListenerImp;
import com.quizmania.utils.StaticGlobalVariables;
import com.quizmania.utils.ViewUtils;

public class ItemStore extends Activity implements QuizManiaActivity{

	private BillingUtil billingUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template);
		StaticGlobalVariables.currentActivity = this;
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		ViewUtils.inflateContentInTemplate(this, R.layout.activity_item_store);
		
		billingUtil = new BillingUtil(this);
		BillingEventListener consmptionHanlder = new QuizManiaBillingEventListenerImp(billingUtil);
		billingUtil.addBillingEventListener(consmptionHanlder);
	}

	public void purchaseItem(View view) {
		String itemIdToPurchase = getItemIdToPurchaseFromClickedView(view);
		
		billingUtil.sellItem(itemIdToPurchase);
	}

	
	private String getItemIdToPurchaseFromClickedView(View view) {
		// TODO Auto-generated method stub
		
		switch (view.getId()) {
		case R.id.buy50HintsPackButton:
			Log.d("QuizMania", "******* resourceId: " + R.id.buy50HintsPackButton);
			Log.d("QuizMania", "******* in-app id:  " + getResources().getString(R.string.hintsPackCode50));
			return getResources().getString(R.string.hintsPackCode50);
			
		
		}
		
		Log.d("**********", "******* returning null :(");
		Log.d("**********", "******* returning null :(" + view.getId());
		
		return null;
	}

	

	@Override	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 // Pass on the activity result to the helper for handling
	    if (!billingUtil.getBillingHelper().handleActivityResult(requestCode, resultCode, data)) {
	        // not handled, so handle it ourselves (here's where you'd
	        // perform any handling of activity results not related to in-app
	        // billing...
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	    else {
	        Log.d("QuizMania", "onActivityResult handled by IABUtil.");
	    }
	}

	@Override
	public void navigateBack(View view) {
		super.onBackPressed();
		
	}

}
