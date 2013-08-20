package com.quizmania.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HintLetterAdapter extends BaseAdapter {
    private Context androidContext;
    private char[] letters;
	private int offset;
	private int hintLetterWidth;
	private int hintLetterHeight;
	private LayoutParams layoutParam;
    public HintLetterAdapter(Context androidContext,char[] letters,int offset,HintStyle hintStyle) {
        this.androidContext = androidContext;
        this.letters=letters;
        this.offset = offset;
        layoutParam= new LayoutParams(hintStyle.getWidth(), hintStyle.getHeight());
      int[] attrs = {android.R.attr.textSize};     
            
    }

    public int getHintLetterWidth() {
		return hintLetterWidth;
	}

	public void setHintLetterWidth(int hintLetterWidth) {
		this.hintLetterWidth = hintLetterWidth;
	}

	public int getHintLetterHeight() {
		return hintLetterHeight;
	}

	public void setHintLetterHeight(int hintLetterHeight) {
		this.hintLetterHeight = hintLetterHeight;
	}

	public int getCount() {
        return letters.length - offset;
    }

    public Object getItem(int position) {
        return letters[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    
    public View getView(int position, View convertView, ViewGroup parent) {
    	
		
    	int realPosition = position + offset;
    	TextView hintLetter = ViewUtils.createHintLetter(androidContext);    			
		hintLetter.setText("" + letters[realPosition]);
		hintLetter.setLayoutParams(layoutParam);
		return hintLetter;
    }

}