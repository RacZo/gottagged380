package com.gottagged380;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {
	
	private SharedPreferences settings;
	private static final String TAG = "menucheck";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		//UserHandler user = new UserHandler();
		//user.sendUser("Armand", "213123123123", "armand.saro@gmail.com");
		
		 settings = getSharedPreferences("com.gottagged380",0);
	     String session = settings.getString("SessionId", ""); 
	     Log.v(TAG, session);
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		settings = getSharedPreferences("com.gottagged380",0);
	    String session = settings.getString("SessionId", ""); 
	    Log.v(TAG, session);
	     
	    ((Button)findViewById(R.id.create_game_button)).setEnabled(true);
	    ((Button)findViewById(R.id.join_game_button)).setEnabled(true);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	public void goToCreateGame(View view){
		((Button)findViewById(R.id.create_game_button)).setEnabled(false);
		Intent intent = new Intent(this, CreateGameActivity.class);
		startActivity(intent);
	}
	
	public void goToJoinGame(View view){
		((Button)findViewById(R.id.join_game_button)).setEnabled(false);
		Intent intent = new Intent(this, JoinGameActivity.class);
		startActivity(intent);
	}
	
}
