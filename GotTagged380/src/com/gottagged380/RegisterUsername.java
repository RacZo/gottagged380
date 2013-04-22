package com.gottagged380;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterUsername extends Activity {

	public boolean registerCheck = false;
	public boolean registerDone = false;
	RegisterUsername register;
	public String email;
	private static final String TAG = "register";
	SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_username);
		
		
		//email = getIntent().getExtras().getString("Email");
		settings = getSharedPreferences("com.gottagged380",0);
		email = settings.getString("Email", ""); 
		Log.v(TAG, email);
		
		Button loginButton = (Button) findViewById(R.id.registerButton);
		loginButton.setOnClickListener(mRegisterListener);
		
		//loginButton.setOnClickListener(mGCMListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_username, menu);
		return true;
	}

	public void checkStringInDatastore(View view){
		//EditText editText = (EditText) findViewById(R.id.username_entry);
		//String userNameChoice = editText.getText().toString();
		Button registerButton = (Button)findViewById(R.id.registerButton);
		registerButton.setText("Registering...");
		registerButton.setEnabled(false);
		Log.v("BPress", "Yep." );
		new UserEndpointTask(this).execute();
	}
	
	private OnClickListener mRegisterListener = new OnClickListener() {

		public void onClick(View v) {
			checkStringInDatastore(v);	
		}	
	};
	
	public String getEmail(){
		return email;
	}
	
	public void enableButton(){
		Button registerButton = (Button)findViewById(R.id.registerButton);
		registerButton.setEnabled(true);
		registerButton.setText("Register");
	}
	
	public SharedPreferences getPref(){
		return settings = getSharedPreferences("com.gottagged380", 0);
	}
	
}	
