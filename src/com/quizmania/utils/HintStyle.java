package com.quizmania.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.quizmania.fruits.R;

public class HintStyle {
	private int width;
	private  int height;
	
	public HintStyle(Context androidContext){
		int[] attrs = {android.R.attr.textSize};
		TypedArray ta = androidContext.obtainStyledAttributes(R.style.hintLettersStyle, attrs);    
        this.width = ta.getDimensionPixelSize(0, 0);
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
