package com.quizmania.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ToggleButton;

import com.quizmania.fruits.R;
import com.quizmania.utils.AnswerService;
import com.quizmania.utils.IOUtils;
import com.quizmania.utils.QuizManiaActivity;
import com.quizmania.utils.StaticGlobalVariables;
import com.quizmania.utils.UserConfig;
import com.quizmania.utils.ViewUtils;

public class OptionsActivity extends ActionBarActivity implements OnClickListener, QuizManiaActivity{

	AlertDialog confirmGameReset;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template);
		StaticGlobalVariables.currentActivity = this;
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		ViewUtils.inflateContentInTemplate(this, R.layout.activity_options);
		initializeOnOffButtons();
        initializeConfirmGameResetDialog();
        
	}
	
	
	
	
	private void initializeConfirmGameResetDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String areYouSureMessage = getResources().getString(R.string.resetGameAreYouSure);
		String cancelText = getResources().getString(R.string.cancel);
		String yesText = getResources().getString(R.string.yes);
		builder.setMessage(areYouSureMessage);
		builder.setNegativeButton(cancelText, null);
		builder.setPositiveButton(yesText, this);
		confirmGameReset = builder.create();
		
	}


	public void onToggleButtonClick(View toggleButton){
		ToggleButton castedToggleButton = (ToggleButton) toggleButton;
		switch(toggleButton.getId()){
			case R.id.soundOnOff:
				System.out.println("SoundOn: " + castedToggleButton.isChecked());
				UserConfig.getInstance(this).setSoundActivated(castedToggleButton.isChecked());
			break;
			case R.id.vibrationOnOff:
				System.out.println("VibrationOn: " + castedToggleButton.isChecked());
				UserConfig.getInstance(this).setVibrationActivated(castedToggleButton.isChecked());
			break;
		}
	}

	private void initializeOnOffButtons() {
		ToggleButton soundOnOffButton = (ToggleButton) findViewById(R.id.soundOnOff);
		ToggleButton vibrateOnOffButton = (ToggleButton) findViewById(R.id.vibrationOnOff);
		soundOnOffButton.setChecked(UserConfig.getInstance(this).isSoundActivated());
		vibrateOnOffButton.setChecked(UserConfig.getInstance(this).isVibrationActivated());
	}
	
	@Override
	public void onStop(){
		super.onStop();		
		UserConfig.getInstance(this).saveToSDCard(this);
	}


	
	
	public void resetAnswers(View resetButton){
		confirmGameReset.show();
		
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		IOUtils.removeFile(AnswerService.ANSWERS_FILE_PATH,this);		
		AnswerService.getAnswerService().reset();
	}




	@Override
	public void navigateBack(View view) {
		super.onBackPressed();		
	}

	

}
