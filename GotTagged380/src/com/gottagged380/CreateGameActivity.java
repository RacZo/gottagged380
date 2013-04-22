package com.gottagged380;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class CreateGameActivity extends Activity {
	
	SharedPreferences settings;

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_game, menu);
		return true;
	}
	
	public void createGameSession(View view){
		((Button)findViewById(R.id.create_button)).setEnabled(false);
		new CreateGameTask( this ).execute();
	}
	
	public SharedPreferences getPref(){
		   return settings = getSharedPreferences("com.gottagged380", 0);
	}

}
