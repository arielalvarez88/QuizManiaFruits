package com.quizmania.activities;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.example.quizmaniafruits.R;
import com.quizmania.utils.UserConfig;
import com.quizmania.utils.ViewUtils;

public class OptionsActivity extends Activity implements OnClickListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		initializeOnOffButtons();
        
        
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
		
		try {
			UserConfig.getInstance().saveToSDCard();
		} catch (FileNotFoundException e) {
			ViewUtils.showAlertMessage(this,getResources().getString(R.string.sdCardError),this);
			

		} catch (IOException e) {
			ViewUtils.showAlertMessage(this,getResources().getString(R.string.sdCardError),this);
		}
	}


	


	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

	

}
