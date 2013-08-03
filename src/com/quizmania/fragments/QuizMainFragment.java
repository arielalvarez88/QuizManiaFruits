package com.quizmania.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.example.quizmaniafruits.R;
import com.quizmania.entities.QuizElement;
import com.quizmania.utils.AnswerService;
import com.quizmania.utils.QuizElementUtil;
import com.quizmania.utils.StaticGlobalVariables;

public class QuizMainFragment extends Fragment implements OnKeyListener {

	QuizElement element;
	View thisView;


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
		setEventListeners(quizElementView);
		drawAnswerIconIfAnswered();
        return quizElementView;

	}



	private void drawAnswerIconIfAnswered() {
		boolean isCorrectAnswered = AnswerService.getAnswerService().isAwnsered(element, StaticGlobalVariables.language);
		if(isCorrectAnswered){
			showCorrectImage();
		}else{
			removeAnswerImage();
		}
		
	}



	private void removeAnswerImage() {
		ImageView answerIcon = (ImageView) thisView.findViewById(R.id.answerIcon);
		answerIcon.setVisibility(View.GONE);  

		
	}



	private void setEventListeners(View quizElementView) {
		AutoCompleteTextView textView = (AutoCompleteTextView) quizElementView.findViewById(R.id.autocomplete_quiz);
		textView.setOnKeyListener(this);		
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
		thisView = quizElementView;
		return quizElementView;
	}



	private void drawElementImage(View quizElementView) {
		
		int resID = QuizElementUtil.getResourceIdFromQuizElement(this, element);
		ImageView quizElementImage = (ImageView) quizElementView.findViewById(R.id.fruitImage);
		quizElementImage.setImageResource(resID);
	}



	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(keyCode != KeyEvent.KEYCODE_ENTER)
			return false;
		
		
		AutoCompleteTextView textView = (AutoCompleteTextView) v;
		
		boolean correctAnswer = AnswerService.getAnswerService().tryToAnswer(element, textView.getText().toString());
		
		if(correctAnswer){
			placeCorrectIcon();
		}else{
			placeIncorrectIcon();
		}
		Log.d("QuizMa/inFragment", "Fue correcta la respuesta: " + correctAnswer);
		return false;
	}



	private void placeIncorrectIcon() {
		// TODO Auto-generated method stub
		showIncorrectImage();
		playIncorrectSound();
		
	}



	private void playIncorrectSound() {
		// TODO Auto-generated method stub
		
	}



	private void showIncorrectImage() {
		ImageView answerIcon = (ImageView) thisView.findViewById(R.id.answerIcon);
		answerIcon.setImageResource(R.drawable.incorrect);
		answerIcon.setVisibility(View.VISIBLE);
		
	}



	private void placeCorrectIcon() {
		showCorrectImage();
		playCorrectSound();
		
	}



	private void playCorrectSound() {
		// TODO Auto-generated method stub
		
	}



	private void showCorrectImage() {
		System.out.println("showCorrectImage");
		ImageView answerIcon = (ImageView) thisView.findViewById(R.id.answerIcon);
		answerIcon.setImageResource(R.drawable.correct);
		answerIcon.setVisibility(View.VISIBLE);
		
	}



	
}
