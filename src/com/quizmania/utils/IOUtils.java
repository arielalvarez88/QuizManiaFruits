package com.quizmania.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.quizmania.fruits.R;

public class IOUtils {

	public static boolean isSDCardAvailableToWrite() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());			
	}
	
	public static boolean isSDCardAvailableToRead() {
		Log.d(IOUtils.class.toString() + "isSDCardAvailableToRead()", "SD State: " + Environment.getExternalStorageDirectory());
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());			
	}
	
	
	public static boolean isFileSavedInSDCard(String filePath) {
		File savedFile = new File(filePath);
		Log.d(IOUtils.class.toString() + "isFileSavedInSDCard()", "Does " + filePath + " exists?" + savedFile.exists());
		return savedFile.exists();
	}
	
	public static boolean writeObjectToSDCard(SDWriteIntent parameterObject,Activity activity){
		if(!isSDCardAvailableToWrite())
			return false;
		
		ObjectOutputStream serializer=null;
		try {
			File targetFile= new File(parameterObject.filePath);
			
			
			createParentFolderIfDoesntExist(targetFile);
			
			
			
			serializer = new ObjectOutputStream(new FileOutputStream(targetFile));
			serializer.writeObject(parameterObject.object);
						
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			ViewUtils.showAlertMessage(activity, activity.getResources().getString(R.string.sdCardError), null);
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			ViewUtils.showAlertMessage(activity, activity.getResources().getString(R.string.sdCardError), null);
			return false;
		}finally{
			try {
				serializer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}

	private static void createParentFolderIfDoesntExist(File targetFile) {
		if(!Environment.getExternalStorageDirectory().exists()){
			Environment.getExternalStorageDirectory().mkdirs();
		}
		
		File parentFolder = targetFile.getParentFile();
		if(!parentFolder.exists()){
			parentFolder.mkdirs();
		}
		
	}
	
	public static Object loadFromSDCard(String filePath) throws StreamCorruptedException, FileNotFoundException, IOException, ClassNotFoundException {
		if(!isSDCardAvailableToRead()){
			throw new IOException("SDCard is not available to read.");
		}
		ObjectInputStream stream =  null;
		try{
			stream = new ObjectInputStream(new FileInputStream(filePath));
			return stream.readObject();	
		}finally{
			stream.close();
		}
		 
				 
	}

	public static void removeFile(String answersFilePath,Activity activity) {
		File file = new File(answersFilePath);
		try{
			file.delete();
		}		
		catch (Exception e) {

				e.printStackTrace();
				ViewUtils.showAlertMessage(activity, activity.getResources().getString(R.string.sdCardError), null);
		}

	}
	
	public static StringBuilder getAssetsFileContent(
			Context activity, String fileName) throws IOException {

		AssetManager assetManager = activity.getAssets();
		InputStream stream = assetManager.open(fileName);
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream,"UTF-8"));
		StringBuilder fileContent = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			fileContent.append(line);
		}
		reader.close();
		return fileContent;
	}


	
}
