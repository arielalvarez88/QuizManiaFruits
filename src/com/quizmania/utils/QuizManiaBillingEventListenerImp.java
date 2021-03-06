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
	//private static final String TAG = StaticGlobalVariables.packageName + "." + QuizManiaBillingEventListenerImp.class.toString();
	private static final String TAG = "QuizMania";
	
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
		Log.d(TAG,"************************Retorno : " + result);
		if(result.isFailure()){
			Log.d(TAG,"************************Error while purchasing: " + result);
			Log.d(TAG,"info: " + info);
			boolean failedBecauseUserOwnsThisItem = result.getMessage().contains("Own");
			if(failedBecauseUserOwnsThisItem) 
				tryToConsume(this.lastItemId); 			
			showErrorMessage();
			
		}else{
			Log.d(TAG,"************* FUNCIONO! AHORA A CONSUMIR" + result);
			
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
			billingUtil.consumeAsync(purchase, this);
		} catch (IabException e) {
			Log.d(TAG,e.getMessage());
			e.printStackTrace();			
			showErrorMessage();
		}
		
		
	}

	@Override
	public void onConsumeFinished(Purchase purchase, IabResult result) {
		Log.d("**************", "onConsumedFinished: "+ result.isSuccess());
		if(result.isSuccess()){
			Resources res = billingUtil.getBillingContext().androidContext.getResources();
			String add50HintsItemId = res.getString(R.string.hintsPack50Code);
			if(purchase.getSku().equals(add50HintsItemId)){
				UserConfig.getInstance(billingUtil.getBillingContext().androidContext).addHints(50);
				UserConfig.getInstance(billingUtil.getBillingContext().androidContext).saveToSDCard(billingUtil.getBillingContext().androidContext);				
			}
		}else{
			showErrorMessage();
		}
		
	}
	
	

}
