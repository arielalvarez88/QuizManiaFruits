package com.quizmania.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quizmania.fruits.R;

public class ViewUtils {

	public static void showAlertMessage(Activity activity,String message,OnClickListener listener){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		AlertDialog alertDialog = builder.create();
		alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",listener);	        
        alertDialog.show();
	}
	
	public static void inflateContentInTemplate(Activity activity, int viewsResourceIdToInflate){
		
		View templateContent = activity.findViewById(R.id.templateContent);
		LayoutInflater inflater = activity.getLayoutInflater();
		inflater.inflate(viewsResourceIdToInflate, (ViewGroup) templateContent);
	}
	
}
