package com.quizmania.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;

public class ViewUtils {

	public static void showAlertMessage(Activity activity,String message,OnClickListener listener){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		AlertDialog alertDialog = builder.create();
		alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",listener);	        
        alertDialog.show();
	}
}
