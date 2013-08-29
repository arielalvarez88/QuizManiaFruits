package com.quizmania.utils;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.quizmania.entities.QuizElement;
import com.quizmania.fragments.QuizMainFragment;

public class QuizPager extends FragmentStatePagerAdapter {

	List<QuizElement> elements;
	public QuizPager(List<QuizElement> elements, FragmentManager fm) {		
        super(fm);
        this.elements = elements;
    }

	@Override
	public Fragment getItem(int numberToGet) {
		QuizMainFragment fragment = new QuizMainFragment();
		fragment.setElement(elements.get(numberToGet));
		return fragment;
	}
	
	  @Override
	    public void destroyItem(View collection, int position, Object o) {
	        View view = (View)o;
	        ((ViewPager) collection).removeView(view);
	        view = null;
	    }

	@Override
	public int getCount() {
		return elements.size();
		
	}
	
	 @Override
	    public CharSequence getPageTitle(int position) {
	        return "QuizMania";
	    }

}
