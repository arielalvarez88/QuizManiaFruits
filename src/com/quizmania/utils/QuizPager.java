package com.quizmania.utils;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.quizmania.entities.QuizElement;
import com.quizmania.fragments.QuizMainFragment;

public class QuizPager extends FragmentStatePagerAdapter {

	List<QuizElement> elements;
	boolean firstTime = true;
	private QuizElement firstElementToShow;
	

	public QuizPager(List<QuizElement> elements, FragmentManager fm) {		
        this(elements,fm,null);
    }

	public QuizPager(List<QuizElement> elements, FragmentManager fm, QuizElement firstElementToShow) {		
        super(fm);
        this.elements = elements;
        this.firstElementToShow = firstElementToShow;
    }

	public List<QuizElement> getElements() {
		return elements;
	}

	public void setElements(List<QuizElement> elements) {
		this.elements = elements;
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	public QuizElement getFirstElementToShow() {
		return firstElementToShow;
	}

	public void setFirstElementToShow(QuizElement firstElementToShow) {
		this.firstElementToShow = firstElementToShow;
	}

	@Override
	public Fragment getItem(int numberToGet) {
		QuizElement toShow = getElementToShow(numberToGet);
		
		QuizMainFragment fragment = new QuizMainFragment();
		fragment.setElement(toShow);

		return fragment;
	}

	private QuizElement getElementToShow(int numberToGet) {
		
		if(firstTime && firstElementToShow != null){
			firstTime = false;
			return firstElementToShow;
		}
		return elements.get(numberToGet);
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
