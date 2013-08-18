package com.quizmania.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.quizmania.fruits.R;
import com.quizmania.utils.BillingContext;
import com.quizmania.utils.BillingEventListener;
import com.quizmania.utils.BillingUtil;
import com.quizmania.utils.QuizManiaBillingEventListenerImp;

public class ItemStore extends Activity {

	private BillingUtil billingUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_store);				
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
			Log.d("**********", "******* resourceId: " + R.id.buy50HintsPackButton);
			Log.d("**********", "******* in-app id:  " + getResources().getString(R.string.hintsPackCode50));
			return getResources().getString(R.string.hintsPackCode50);
			
		
		}
		
		Log.d("**********", "******* returning null :(");
		Log.d("**********", "******* returning null :(" + view.getId());
		
		return null;
	}

}
