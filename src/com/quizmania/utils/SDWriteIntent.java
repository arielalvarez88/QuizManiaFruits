package com.quizmania.utils;

public class SDWriteIntent {
	public String filePath;
	public Object object;
	

	public SDWriteIntent(String filePath, Object object) {
		this.filePath = filePath;
		this.object = object;		
	}
}