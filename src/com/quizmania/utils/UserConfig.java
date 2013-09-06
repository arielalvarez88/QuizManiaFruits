package com.quizmania.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.quizmania.entities.Language;

public class UserConfig extends SDCardSavableEntity implements Serializable{
	
	private static UserConfig instance;
	private boolean vibrationActivated;
	private boolean soundActivated;	
	private Language language;
	private int hintsLeft;
	
	private static String userConfigFilePath = Environment.getExternalStorageDirectory().toString()  + "/Android/data/com.quizmania/userConfig.quizmania";
    public static final String HAS_BEEN_RUN_BEFORE = "QuizManiaHasBeenRunBefore";

	public Language getLanguage() {
		return language;
	}

	public int getHintsLeft() {
		return hintsLeft;
	}

	public void setHintsLeft(int hints) {
		this.hintsLeft = hints;
	}

	public static void setUserConfigFilePath(String userConfigFilePath) {
		UserConfig.userConfigFilePath = userConfigFilePath;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public boolean isVibrationActivated() {
		return vibrationActivated;
	}

	public void setVibrationActivated(boolean vibrationActivated) {
		this.vibrationActivated = vibrationActivated;
	}

	public boolean isSoundActivated() {
		return soundActivated;
	}

	public void setSoundActivated(boolean soundActivated) {
		this.soundActivated = soundActivated;
	}

	private UserConfig(Activity activity){
		vibrationActivated = true;
		soundActivated = true;
		language = new Language(Language.ENGLISH);
	    SharedPreferences settings = activity.getSharedPreferences(HAS_BEEN_RUN_BEFORE, 0);
	    
	    boolean hasBeenRunBefore = settings.getBoolean(HAS_BEEN_RUN_BEFORE, false);

		hintsLeft = hasBeenRunBefore? 0 : 10;
		SharedPreferences.Editor editor = settings.edit();
	    editor.putBoolean(HAS_BEEN_RUN_BEFORE, true);
		System.out.println("language: " +   language);
		
	}
	
	public static UserConfig getInstance(Activity activity){

		Log.d(UserConfig.class.getCanonicalName() , "getExternalStorageState From UserConfig: " + Environment.getExternalStorageState()); 
		if(instance==null){			
			instance = IOUtils.isFileSavedInSDCard(userConfigFilePath) ? loadFromSDCard(activity) : new UserConfig(activity);
		}
		return instance;
	}

	

	private static UserConfig loadFromSDCard(Activity activity) {		

		try {
			
						
			instance = (UserConfig) IOUtils.loadFromSDCard(userConfigFilePath);						
						
		} catch (StreamCorruptedException e) {
			instance = new UserConfig(activity);			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			instance = new UserConfig(activity);
			e.printStackTrace();
		} catch (IOException e) {
			instance = new UserConfig(activity);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			instance = new UserConfig(activity);
			e.printStackTrace();
		}
		return instance;
		
	}

	@Override
	public String getUserConfigFilePath() {
		return userConfigFilePath;
	}

	public void addHints(int i) {
		this.hintsLeft += i;
			
	}
	
	

}
