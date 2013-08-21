package com.quizmania.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.TextView;

import com.quizmania.entities.NameHints;
import com.quizmania.fruits.R;

public class ViewUtils {

	public static void showAlertMessage(Activity activity,String message,OnClickListener listener){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		AlertDialog alertDialog = builder.create();
		alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",listener);	        
        alertDialog.show();
	}
	
	public static TextView createHintLetter(Context androidContext){
		TextView letterHolder = new TextView(androidContext);
		letterHolder.setBackgroundResource(R.drawable.letter_holder);			
		letterHolder.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);		
		letterHolder.setGravity(Gravity.CENTER);
		letterHolder.setTextColor(Color.WHITE);
		letterHolder.setTypeface(null, Typeface.BOLD);			        			        				
		letterHolder.setPadding(0, 0, 0, 0);
		return letterHolder;
	}
	
	public static void inflateContentInTemplate(Activity activity, int viewsResourceIdToInflate){
		
		View templateContent = activity.findViewById(R.id.templateContent);
		LayoutInflater inflater = activity.getLayoutInflater();
		inflater.inflate(viewsResourceIdToInflate, (ViewGroup) templateContent);
	}
	
}
