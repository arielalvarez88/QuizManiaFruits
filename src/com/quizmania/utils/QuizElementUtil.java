package com.quizmania.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;

import com.quizmania.entities.QuizElement;

public class QuizElementUtil {
	public QuizElement element;

	public static int getResourceIdFromQuizElement(Context context, QuizElement element){
		
		
			Resources res = context.getResources();
			return getResourceIdFromQuizElement(res, element);
		}
	
	public static int getResourceIdFromQuizElement(Resources res, QuizElement element){
		String imageName= element.getImageName();
		int resID = res.getIdentifier(imageName, "drawable",
				StaticGlobalVariables.packageName);
		return resID;
		
	}
	public static int getResourceIdFromQuizElement(Fragment context, QuizElement element){
		
		
		Resources res = context.getResources();
		return getResourceIdFromQuizElement(res, element);
	}
}
