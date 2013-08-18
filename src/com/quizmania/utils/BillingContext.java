package com.quizmania.utils;

import java.util.List;

import android.app.Activity;

public class BillingContext {
	public Activity androidContext;
	public String appPublicKey;
	public List<String> itemIds;

	public BillingContext(Activity androidContext, String appPublicKey,
			List<String> itemIds) {
		this.androidContext = androidContext;
		this.appPublicKey = appPublicKey;
		this.itemIds = itemIds;
	}
}