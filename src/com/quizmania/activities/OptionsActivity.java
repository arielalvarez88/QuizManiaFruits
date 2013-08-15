package com.quizmania.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.quizmania.fruits.R;
import com.quizmania.utils.AnswerService;
import com.quizmania.utils.IOUtils;
import com.quizmania.utils.UserConfig;

public class OptionsActivity extends Activity implements OnClickListener {

	AlertDialog confirmGameReset;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
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
				UserConfig.getInstance().setSoundActivated(castedToggleButton.isChecked());
			break;
			case R.id.vibrationOnOff:
				System.out.println("VibrationOn: " + castedToggleButton.isChecked());
				UserConfig.getInstance().setVibrationActivated(castedToggleButton.isChecked());
			break;
		}
	}

	private void initializeOnOffButtons() {
		ToggleButton soundOnOffButton = (ToggleButton) findViewById(R.id.soundOnOff);
		ToggleButton vibrateOnOffButton = (ToggleButton) findViewById(R.id.vibrationOnOff);
		soundOnOffButton.setChecked(UserConfig.getInstance().isSoundActivated());
		vibrateOnOffButton.setChecked(UserConfig.getInstance().isVibrationActivated());
	}
	
	@Override
	public void onStop(){
		super.onStop();		
		UserConfig.getInstance().saveToSDCard(this);
	}


	
	
	public void resetAnswers(View resetButton){
		confirmGameReset.show();
		
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		IOUtils.removeFile(AnswerService.ANSWERS_FILE_PATH,this);		
		AnswerService.getAnswerService().reset();
	}

	

}
