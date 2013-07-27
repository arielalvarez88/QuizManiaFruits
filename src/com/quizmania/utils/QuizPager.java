package com.quizmania.utils;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.quizmania.entities.QuizElement;
import com.quizmania.fragments.QuizMainFragment;

public class QuizPager extends FragmentStatePagerAdapter {

	List<QuizElement> elements;
	private int initialPosition;

	

	public QuizPager(List<QuizElement> elements, FragmentManager fm) {		
        this(elements,fm,0);
        
    }

	public QuizPager(List<QuizElement> elements, FragmentManager fm, int initialPosition) {		
        super(fm);
        this.elements = elements;
        this.initialPosition = initialPosition;
        
        
    }

	public List<QuizElement> getElements() {
		return elements;
	}

	public void setElements(List<QuizElement> elements) {
		this.elements = elements;
	}

	
	@Override
	public Fragment getItem(int numberToGet) {
		QuizElement toShow = getElementToShow(numberToGet);
		
		QuizMainFragment fragment = new QuizMainFragment();
		fragment.setElement(toShow);

		return fragment;
	}

	private QuizElement getElementToShow(int numberToGet) {
		
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
