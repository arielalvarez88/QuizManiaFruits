package com.quizmania.utils;

import com.android.vending.billing.IabHelper;
import com.android.vending.billing.IabResult;
import com.android.vending.billing.Purchase;

public interface BillingEventListener {
	public void sellItem(String itemId);
	
}
