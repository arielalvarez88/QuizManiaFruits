package com.quizmania.activities;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.quizmaniafruits.R;
import com.quizmaina.entities.QuizElement;
import com.quizmania.utils.QuizElementsLoader;

public class QuizLevelViewer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fruit_viewer);
		
		
		try {
			List<QuizElement> elements = QuizElementsLoader.loadElementsFromLevel("english", this);
			System.out.println("****** the real deal: " + elements);
			ImageView fruitImage = (ImageView) findViewById(R.id.fruitImage);
			Resources res = getResources();
			String imagePath = elements.get(0).getImage();
			int resID = res.getIdentifier(imagePath , "drawable", getPackageName());
			
			
			fruitImage.setImageResource(resID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


}
