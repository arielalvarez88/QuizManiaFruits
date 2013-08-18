package com.quizmania.utils;

import java.security.acl.LastOwnerException;

import android.content.res.Resources;
import android.nfc.Tag;
import android.util.Log;

import com.android.vending.billing.IabException;
import com.android.vending.billing.IabHelper.OnConsumeFinishedListener;
import com.android.vending.billing.IabHelper.OnIabPurchaseFinishedListener;
import com.android.vending.billing.IabResult;
import com.android.vending.billing.Inventory;
import com.android.vending.billing.Purchase;
import com.quizmania.fruits.R;

public class QuizManiaBillingEventListenerImp implements BillingEventListener, OnConsumeFinishedListener, OnIabPurchaseFinishedListener {

	
	private BillingUtil billingUtil;
	private String lastItemId;
	private static final String TAG = StaticGlobalVariables.packageName + "." + QuizManiaBillingEventListenerImp.class.toString();
	public QuizManiaBillingEventListenerImp(BillingUtil billingUtil){
		this.billingUtil = billingUtil;
	}
	

	private boolean doesUserAlreadyHasThisItem(String sku) {
		
		return false;
	}
	
	@Override
	public void sellItem(String itemId) {
		
		this.lastItemId = itemId;
		billingUtil.launchPurchaseFlow(billingUtil.getBillingContext().androidContext, itemId, 0, this);
		
		
		
		
	}
	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase info) {
		if(result.isFailure()){
			Log.e(TAG,"Error while purchasing: " + result);
			Log.d(TAG,"info: " + info);
			boolean failedBecauseUserOwnsThisItem = result.getMessage().contains("Own");
			if(failedBecauseUserOwnsThisItem) 
				tryToConsume(this.lastItemId); 			
			showErrorMessage();
			
		}else{
			billingUtil.consumeAsync(info, this);
		}
		
	}
	
	private void showErrorMessage() {
		ViewUtils.showAlertMessage(billingUtil.getBillingContext().androidContext, "Error in the purchase please try again",  null);
		
	}


	private void tryToConsume(String sku) {
		Log.d(TAG, "trying to consume!");
		try {
			Purchase purchase= billingUtil.getPurchaseForItem(sku);			 
			billingUtil.consumeAsync(purchase, null);
		} catch (IabException e) {

			e.printStackTrace();			
			showErrorMessage();
		}
		
		
	}

	@Override
	public void onConsumeFinished(Purchase purchase, IabResult result) {
		if(result.isSuccess()){
			Resources res = billingUtil.getBillingContext().androidContext.getResources();
			String add50HintsItemId = res.getString(R.string.hintsPackCode50);
			if(purchase.getSku().equals(add50HintsItemId)){
				UserConfig.getInstance().addHints(50);
				UserConfig.getInstance().saveToSDCard(billingUtil.getBillingContext().androidContext);
			}
		}
		
	}
	
	

}
