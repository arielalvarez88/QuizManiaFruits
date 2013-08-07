package com.quizmania.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Locale;

import android.os.Environment;

public class UserConfig {
	
	private static UserConfig instance;
	private boolean vibrationActivated;
	private boolean soundActivated;	
	private String language;
	private static String userConfigFilePath = Environment.getExternalStorageDirectory().toString()  + "QuizMania_Fruits/userConfig.quizmania";
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
		if(instance==null){
			
			instance = isConfSavedInSDCard() ? loadConfFromSDCard() : new UserConfig();
		}
		return instance;
	}

	private static boolean isConfSavedInSDCard() {
		File savedUserConfigFile = new File(userConfigFilePath);		
		return savedUserConfigFile.exists();
	}

	private static UserConfig loadConfFromSDCard() {
		
		ObjectInputStream serializedUserConfig;
		try {
			serializedUserConfig = new ObjectInputStream(new FileInputStream(userConfigFilePath));
			instance = (UserConfig) serializedUserConfig.readObject();
			serializedUserConfig.close();
			System.out.print("User config loaded from SD CARD");
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
	
	

	public void saveToSDCard() throws FileNotFoundException, IOException{
		ObjectOutputStream serializer = new ObjectOutputStream(new FileOutputStream(userConfigFilePath));		
		serializer.writeObject(instance);
		serializer.close();
	}
}
