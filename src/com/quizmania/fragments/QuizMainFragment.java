package com.quizmania.fragments;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore.Audio.Media;
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
import com.quizmania.utils.UserConfig;
import com.quizmania.utils.ViewUtils;

public class QuizMainFragment extends Fragment implements OnKeyListener, OnClickListener {

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
			showCorrectAnsweredIcon();
		}else{
			hideAnswerIcon();
		}
		
	}


	@Override
	public void onResume(){
		super.onResume();
		if(!AnswerService.getAnswerService().isAwnsered(element, StaticGlobalVariables.language)){
			hideAnswerIcon();
		}
	}


	private void hideAnswerIcon() {
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
			try {
				AnswerService.getAnswerService().saveToSDCard();
			} catch (FileNotFoundException e) {
				ViewUtils.showAlertMessage(getActivity(),getResources().getString(R.string.sdCardError),this);
			} catch (IOException e) {
				ViewUtils.showAlertMessage(getActivity(),getResources().getString(R.string.sdCardError),this);
			}
		}else{
			placeIncorrectIcon();
		}
		Log.d("QuizMa/inFragment", "Fue correcta la respuesta: " + correctAnswer);
		return false;
	}



	private void placeIncorrectIcon() {

		showIncorrectImage();
		playIncorrectSound();
		vibrate();
		
	}
	



	private void vibrate() {
		if(UserConfig.getInstance().isVibrationActivated()){
			Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);		 		
			v.vibrate(500);
		}
		
	}



	private void playIncorrectSound() {

		MediaPlayer playerMedia = MediaPlayer.create(getActivity(), R.raw.fail);
		playerMedia.start();
		
	}



	private void showIncorrectImage() {
		ImageView answerIcon = (ImageView) thisView.findViewById(R.id.answerIcon);
		answerIcon.setImageResource(R.drawable.incorrect);
		answerIcon.setVisibility(View.VISIBLE);
		
	}



	private void placeCorrectIcon() {
		showCorrectAnsweredIcon();
		playCorrectSound();
		
	}



	private void playCorrectSound() {		
		MediaPlayer playerMedia = MediaPlayer.create(getActivity(), R.raw.correct);
		playerMedia.start();
		
	}



	private void showCorrectAnsweredIcon() {
		System.out.println("showCorrectAnsweredIcon");
		ImageView answerIcon = (ImageView) thisView.findViewById(R.id.answerIcon);
		answerIcon.setImageResource(R.drawable.correct);
		answerIcon.setVisibility(View.VISIBLE);
		
	}



	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}



	
}
