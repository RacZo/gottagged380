package com.gottagged380;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.gottagged380.gamemanager.Gamemanager;
import com.gottagged380.gamemanager.model.GameRequest;
import com.gottagged380.gamemanager.model.GameRequestResponse;

public class CreateGameTask extends AsyncTask<Void, Void, String> {
	CreateGameActivity activ;
	private SharedPreferences settings;
	private static final String TAG = "createcheck";
	
	public CreateGameTask(CreateGameActivity a ){
		super();
		activ = a;
	}

	
	protected String doInBackground(Void...unused){
		Gamemanager.Builder builder = new Gamemanager.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
	    builder.setApplicationName("com.gottagged380");
		Gamemanager gm = builder.build();
		
		String session = null;
		
		GameRequest gr = new GameRequest();
		
		List<String> playList = new LinkedList<String>();
	
		// Read from textfields, put into Array
		// This is ugly code that will need to be updated/simplified
		String p1 = ((EditText)activ.findViewById(R.id.Player1_Name)).getText().toString();
		String p2 = ((EditText)activ.findViewById(R.id.Player2_Name)).getText().toString();
		String p3 = ((EditText)activ.findViewById(R.id.Player3_Name)).getText().toString();
		if( !p1.equals("") ) playList.add( p1 );
		if( !p2.equals("") ) playList.add( p2 );
		if( !p3.equals("") ) playList.add( p3 );
		
		SharedPreferences settings = activ.getSharedPreferences( "com.gottagged380", 0 );
		String userName = settings.getString( "Username", "UNKNOWN_CREATOR" );
		
		gr.setNameOfPlayers( playList );
		gr.setNameOfCreator( userName );
		gr.setDuration( 10 );
		
		if( !playList.isEmpty() ){
			GameRequestResponse grResponse = null;
			try{
				grResponse = gm.game().create( gr ).execute();
				
				settings = activ.getPref();
			    SharedPreferences.Editor editor = settings.edit();
			    editor.putString("SessionId", grResponse.getId().toString());
			    editor.commit();
			     
			    // double-check that the session has been stored properly
			    session = settings.getString("SessionId", ""); 
				Log.v(TAG, session);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return session;
	}
	
	protected void onPostExecute(String sid){
		if( sid == null ){
			//Session Creation failed, stay in this menu.
			((Button)activ.findViewById(R.id.create_button)).setEnabled(true);
			((TextView)activ.findViewById(R.id.CreateGameTextView)).setText(
					"Create Session Failed.  Try again.");
		}
		else {
			// Session Creation successful, go to Waiting screen.
			// Transition to WaitingScreen, send updates
			Intent intent = new Intent(activ, WaitingActivity.class);
			activ.startActivity(intent);
			activ.finish();
		}
	}
}
