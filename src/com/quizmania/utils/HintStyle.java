package com.quizmania.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.TypedValue;

import com.quizmania.fruits.R;

public class HintStyle {
	private int width;
	private  int height;
	private int letterSize;
	
	public HintStyle(Context androidContext){
		int[] attrs = {android.R.attr.textSize};
		TypedArray ta = androidContext.obtainStyledAttributes(R.style.hintLettersStyle, attrs);    
        this.letterSize= ta.getDimensionPixelSize(0, 0);
        this.width = ta.getDimensionPixelSize(1, 0);
        Log.d("********", "newWidth: " + width);
        
        this.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, androidContext.getResources().getDisplayMetrics());
        Log.d("********", "workingWidth: " + width);
        this.height= width;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
