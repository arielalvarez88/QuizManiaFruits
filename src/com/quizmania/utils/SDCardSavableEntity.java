package com.quizmania.utils;

import java.io.Serializable;

import android.app.Activity;

public abstract class SDCardSavableEntity implements Serializable {

		public void saveToSDCard(Activity intentActivity) {				 
		 SDWriteIntent saveAnswerIntent = new SDWriteIntent(getUserConfigFilePath(), this);
		 IOUtils.writeObjectToSDCard(saveAnswerIntent, intentActivity);		 		
	    }
		
		public abstract String getUserConfigFilePath();
			
		
}