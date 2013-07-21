package com.quizmania.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.quizmaniafruits.R;
import com.quizmania.activities.MainActivity;
import com.quizmania.entities.QuizElement;

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
		View quizElementView = inflater.inflate(R.layout.fragment_quiz_main, container, false);
		
		ImageView fruitImage = (ImageView) quizElementView.findViewById(R.id.fruitImage);
		Resources res = getResources();
		String imageName= element.getImageName();
		int resID = res.getIdentifier(imageName, "drawable",
				MainActivity.PACKAGE_NAME);

		fruitImage.setImageResource(resID);

        return quizElementView;
        
        

	}
}
