package com.quizmania.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import android.os.Environment;
import android.util.Log;

public class UserConfig extends SDCardSavableEntity implements Serializable{
	
	private static UserConfig instance;
	private boolean vibrationActivated;
	private boolean soundActivated;	
	private String language;
	
	private static String userConfigFilePath = Environment.getExternalStorageDirectory().toString()  + "/Android/data/" + StaticGlobalVariables.packageName + "userConfig.quizmania";
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
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

	private UserConfig(){
		vibrationActivated = true;
		soundActivated = true;
		language = Languages.ENGLISH.getStringReperesentation();
		System.out.println("language: " +   language);
		
	}
	
	public static UserConfig getInstance(){

		Log.d(UserConfig.class.getCanonicalName() , "getExternalStorageState From UserConfig: " + Environment.getExternalStorageState()); 
		if(instance==null){			
			instance = IOUtils.isFileSavedInSDCard(userConfigFilePath) ? loadFromSDCard() : new UserConfig();
		}
		return instance;
	}

	

	private static UserConfig loadFromSDCard() {		
		
		try {
			
						
			instance = (UserConfig) IOUtils.loadFromSDCard(userConfigFilePath);						
						
		} catch (StreamCorruptedException e) {
			instance = new UserConfig();			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			instance = new UserConfig();
			e.printStackTrace();
		} catch (IOException e) {
			instance = new UserConfig();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			instance = new UserConfig();
			e.printStackTrace();
		}
		return instance;
		
	}

	@Override
	public String getUserConfigFilePath() {
		return userConfigFilePath;
	}
	
	

}
