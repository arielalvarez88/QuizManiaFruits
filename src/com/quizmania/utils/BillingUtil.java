package com.quizmania.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

import com.android.vending.billing.IabException;
import com.android.vending.billing.IabHelper;
import com.android.vending.billing.IabHelper.OnConsumeFinishedListener;
import com.android.vending.billing.IabHelper.OnIabPurchaseFinishedListener;
import com.android.vending.billing.IabHelper.OnIabSetupFinishedListener;
import com.android.vending.billing.IabResult;
import com.android.vending.billing.Inventory;
import com.android.vending.billing.Purchase;
import com.quizmania.fruits.R;


public class BillingUtil implements OnIabSetupFinishedListener{
	private static final String TAG = StaticGlobalVariables.packageName + "OnIabSetupFinishedListener";
	int token;
	IabHelper billingHelper;
	public IabHelper getBillingHelper() {
		return billingHelper;
	}

	public void setBillingHelper(IabHelper billingHelper) {
		this.billingHelper = billingHelper;
	}

	public BillingContext getBillingContext() {
		return billingContext;
	}

	public void setBillingContext(BillingContext billingContext) {
		this.billingContext = billingContext;
	}

	List<BillingEventListener> billingEventsListeners;
	boolean initializedCorrectly;

	private BillingContext billingContext;
	
	public BillingUtil(Activity androidContext){
		
		this.billingContext = this.buildBillingContext(androidContext);
		billingEventsListeners = new ArrayList<BillingEventListener>();
		billingHelper = new IabHelper(billingContext.androidContext, billingContext.appPublicKey);		
		initializeBiling();
	}
	
	public void addBillingEventListener(BillingEventListener listener){
		billingEventsListeners.add(listener);
	}
	
	private void initializeBiling() {
		
		
		billingHelper.startSetup(this);
	}

	@Override
	public void onIabSetupFinished(IabResult result) {
			
	      if (!result.isSuccess()) {
	          // Oh noes, there was a problem.
	          Log.d(TAG, "Problem setting up In-app Billing: " + result);
	       }            
	      Log.d(TAG, "In-app Billing success" + result);  
	      initializedCorrectly=true;
	}
	
	public boolean isInitializedCorrectly() {
		return initializedCorrectly;
	}

	public void sellItem(String itemId) {
			for(BillingEventListener listener: billingEventsListeners){
				listener.sellItem(itemId);
			}
			
							
	}

	public void dispose() {
		billingHelper.dispose();
		billingHelper = null;
		
	}
	
	public static BillingContext buildBillingContext(Activity androidContext){
		Resources res = androidContext.getResources();
		String[] itemsForSale = res.getStringArray(R.array.itemsForSale);		
		List<String> itemIds = Arrays.asList(itemsForSale);
		String appPublicKey = res.getString(R.string.appPublicKey);
		BillingContext billingContext = new BillingContext(androidContext, appPublicKey, itemIds);
		return billingContext;
	}

	public Purchase getPurchaseForItem(String sku) throws IabException {
		ArrayList<String> itemId = new ArrayList<String>();
		itemId.add(sku);
		Inventory inv = billingHelper.queryInventory(true, itemId);
		return inv.getPurchase(sku);
		
	}

	public void launchPurchaseFlow(Activity androidContext, String itemId,
			int i,
			 OnIabPurchaseFinishedListener eventListenerImp) {
		billingHelper.launchPurchaseFlow(androidContext, itemId, i, eventListenerImp);
		
	}

	public void consumeAsync(Purchase info,
			OnConsumeFinishedListener eventListenerImp) {
		billingHelper.consumeAsync(info, eventListenerImp);
		
	}


		
}
