package com.quizmania.utils;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class YesNoAlertParams {
	public Context context;
	public String message;
	public String positiveText;
	public String negativeText;
	public OnClickListener positiveListener;
	public OnClickListener negativeListener;


	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPositiveText() {
		return positiveText;
	}

	public void setPositiveText(String positiveText) {
		this.positiveText = positiveText;
	}

	public String getNegativeText() {
		return negativeText;
	}

	public void setNegativeText(String negativeText) {
		this.negativeText = negativeText;
	}

	public OnClickListener getPositiveListener() {
		return positiveListener;
	}

	public void setPositiveListener(OnClickListener positiveListener) {
		this.positiveListener = positiveListener;
	}

	public OnClickListener getNegativeListener() {
		return negativeListener;
	}

	public void setNegativeListener(OnClickListener negativeListener) {
		this.negativeListener = negativeListener;
	}
}