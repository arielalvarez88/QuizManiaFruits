package com.quizmania.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.example.quizmaniafruits.R;
import com.quizmania.entities.QuizElement;
import com.quizmania.utils.QuizElementUtil;
import com.quizmania.utils.StaticGlobalVariables;

public class QuizMainFragment extends Fragment {

	QuizElement element;



	public QuizElement getElement() {
		return element;
	}



	public void setElement(QuizElement element) {
		this.element = element;
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
		View quizElementView = constructViewFromQuizElement(inflater, container);
		setAutocomplete(quizElementView);
	
        return quizElementView;

	}



	private void setAutocomplete(View quizElementView) {
		AutoCompleteTextView textView = (AutoCompleteTextView) quizElementView.findViewById(R.id.autocomplete_quiz);
		textView.setThreshold(1);
		
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(getActivity(), R.layout.autocomplete,StaticGlobalVariables.quizSuggestions);
		
		textView.setAdapter(adapter);
	}



	private View constructViewFromQuizElement(LayoutInflater inflater,
			ViewGroup container) {
		View quizElementView = inflater.inflate(R.layout.fragment_quiz_main, container, false);
		
		drawElementImage(quizElementView);
		
		return quizElementView;
	}



	private void drawElementImage(View quizElementView) {
		
		int resID = QuizElementUtil.getResourceIdFromQuizElement(this, element);
		ImageView quizElementImage = (ImageView) quizElementView.findViewById(R.id.fruitImage);
		quizElementImage.setImageResource(resID);
	}



	
}
