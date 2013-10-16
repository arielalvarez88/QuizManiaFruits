package com.quizmania.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.quizmania.entities.Language;
import com.quizmania.fruits.R;
import com.quizmania.utils.QuizManiaActivity;
import com.quizmania.utils.StaticGlobalVariables;
import com.quizmania.utils.ViewUtils;

public class LevelChooser extends ActionBarActivity implements QuizManiaActivity{
//public class LevelChooser extends Activity implements QuizManiaActivity{

	Map<Integer,String> viewIdToLanguageMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template);
		StaticGlobalVariables.currentActivity = this;
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		ViewUtils.inflateContentInTemplate(this, R.layout.activity_choose_level);
		initializeViewToLanguageMap();

        final ListView listview = (ListView) findViewById(R.id.listView);
        String[] values = new String[] { "English", "Spanish", "Portuguese","French" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                /*
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                /*
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(1000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                //rfk
                                //list.remove(item);
                                //adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
                */
            }

        });
    }

	private void initializeViewToLanguageMap() {
		viewIdToLanguageMap = new HashMap<Integer, String>();
		
		viewIdToLanguageMap.put(R.id.englishLevelButton,Language.ENGLISH);
		viewIdToLanguageMap.put(R.id.spanishLevelButton,Language.SPANISH);
		viewIdToLanguageMap.put(R.id.portugueseLevelButton,Language.PORTUGUESE);
		viewIdToLanguageMap.put(R.id.frenchLevelButton,Language.FRENCH);
		
		
	}



	public void goToLevel(View clickedButton) {
		Intent intent = new Intent(this, ElementList.class);

		String level = viewIdToLanguageMap.get(clickedButton.getId());
		StaticGlobalVariables.currentLevel = level;
		intent.putExtra(StaticGlobalVariables.LEVEL_ATTRIBUTE_NAME, level);
		startActivity(intent);
		
	}


	@Override
	public void navigateBack(View view) {
		super.onBackPressed();
		
	}

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }


}
