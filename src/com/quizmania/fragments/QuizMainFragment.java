package com.quizmania.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.example.quizmaniafruits.R;
import com.quizmania.activities.MainActivity;
import com.quizmania.entities.QuizElement;
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
						 
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(getActivity(), R.layout.fragment_quiz_main, StaticGlobalVariables.quizSuggestions);
		textView.setAdapter(adapter);
	}



	private View constructViewFromQuizElement(LayoutInflater inflater,
			ViewGroup container) {
		View quizElementView = inflater.inflate(R.layout.fragment_quiz_main, container, false);
		
		drawElementImage(quizElementView);
		
		return quizElementView;
	}



	private void drawElementImage(View quizElementView) {
		
		int resID = getQuizImageResourceId();
		ImageView quizElementImage = (ImageView) quizElementView.findViewById(R.id.fruitImage);
		quizElementImage.setImageResource(resID);
	}



	private int getQuizImageResourceId() {
		Resources res = getResources();
		String imageName= element.getImageName();
		int resID = res.getIdentifier(imageName, "drawable",
				MainActivity.PACKAGE_NAME);
		return resID;
	}
}
