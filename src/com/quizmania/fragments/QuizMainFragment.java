package com.quizmania.fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizmania.entities.QuizElement;
import com.quizmania.fruits.R;
import com.quizmania.utils.AnswerService;
import com.quizmania.utils.QuizElementUtil;
import com.quizmania.utils.StaticGlobalVariables;
import com.quizmania.utils.UserConfig;

public class QuizMainFragment extends Fragment implements OnKeyListener, OnClickListener, OnTouchListener {

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
		addAnswerTextboxEvents();
		addAnswerButtonEvents();
		addElementImageEvents();
		
	}



	private void addElementImageEvents() {
		ImageView imageView = getElementImageView();
		imageView.setOnTouchListener(this);
	}



	private void addAnswerButtonEvents() {
		Button answerButton =  (Button) thisView.findViewById(R.id.answerButton);
		answerButton.setOnClickListener(this);
	}



	private void addAnswerTextboxEvents() {
		TextView answerTextbox = (TextView) thisView.findViewById(R.id.answerTextbox);
		answerTextbox.setOnKeyListener(this);
	}



	private ImageView getElementImageView() {
		ImageView imageView =  (ImageView) thisView.findViewById(R.id.elementImage);
		return imageView;
	}


	private View constructViewFromQuizElement(LayoutInflater inflater,
			ViewGroup container) {
		View quizElementView = inflater.inflate(R.layout.fragment_quiz_main, container, false);
		thisView = quizElementView;
		drawElementImage(quizElementView);
		return quizElementView;
	}



	private void drawElementImage(View quizElementView) {
		
		int resID = QuizElementUtil.getResourceIdFromQuizElement(this, element);
		ImageView quizElementImage =  getElementImageView();
		quizElementImage.setImageResource(resID);
	}



	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(keyCode != KeyEvent.KEYCODE_ENTER)
			return false;
				
		tryToAnswer();
		return false;
	}



	public void tryToAnswer() {
		TextView textView = (TextView) thisView.findViewById(R.id.answerTextbox);
		
		boolean correctAnswer = AnswerService.getAnswerService().tryToAnswer(element, textView.getText().toString());
		
		if(correctAnswer){
			placeCorrectIcon();			
			AnswerService.getAnswerService().saveToSDCard(getActivity());
			
		}else{
			placeIncorrectIcon();
		}
		Log.d("QuizMa/inFragment", "Fue correcta la respuesta: " + correctAnswer);
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
		if(UserConfig.getInstance().isSoundActivated()){
			MediaPlayer playerMedia = MediaPlayer.create(getActivity(), R.raw.fail);
			playerMedia.start();
		}
	
		
	}



	private void showIncorrectImage() {
		ImageView answerIcon =  getElementImageView();
		answerIcon.setImageResource(R.drawable.incorrect);
		answerIcon.setVisibility(View.VISIBLE);
		
	}



	private void placeCorrectIcon() {
		showCorrectAnsweredIcon();
		playCorrectSound();
		
	}



	private void playCorrectSound() {
		if(UserConfig.getInstance().isSoundActivated()){
			MediaPlayer playerMedia = MediaPlayer.create(getActivity(), R.raw.correct);
			playerMedia.start();	
		}
		
		
	}



	private void showCorrectAnsweredIcon() {
		
		ImageView answerIcon = (ImageView) thisView.findViewById(R.id.answerIcon);
		answerIcon.setImageResource(R.drawable.correct);
		answerIcon.setVisibility(View.VISIBLE);
		
	}



	@Override
	public void onClick(View button) {		
		tryToAnswer();
	}



	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
			      Context.INPUT_METHOD_SERVICE);
		EditText answerTextBox = (EditText) thisView.findViewById(R.id.answerTextbox);
			imm.hideSoftInputFromWindow(answerTextBox.getWindowToken(), 0);
		return true;
		
	}



	
}
