package com.gottagged380;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.gottagged380.gamemanager.Gamemanager;
import com.gottagged380.gamemanager.model.JoinGame;

public class JoinGameTask extends AsyncTask<Long, Void, JoinGame> {

	Activity activity;
	
	public JoinGameTask( Activity a ){
		activity = a;
	}
	
	public JoinGame doInBackground(Long... sid){
		// send JoinGame message to server
		Gamemanager.Builder builder = new Gamemanager.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
	    builder.setApplicationName("com.gottagged380");
		Gamemanager gm = builder.build();
		
		JoinGame joinGame = new JoinGame();
		joinGame.setId( sid[0] );
		SharedPreferences shPref = activity.getSharedPreferences( "com.gottagged380", 0 );
		joinGame.setName( shPref.getString( "Username", "UNKNOWN_PLAYER" ) );
		joinGame.setJoin(true);
		
		JoinGame response = null;
		
		try{
			response = gm.game().join(joinGame).execute();
		} catch (IOException e){
			e.printStackTrace();
		} catch (Exception e){
			// output fail message here
		}

		return response;
	}
	
	public void onPostExecute(JoinGame response){
		if( response != null ){
			SharedPreferences settings = activity.getSharedPreferences( "com.gottagged380", 0 );
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putString("SessionId", response.getId().toString());
		    editor.commit();
		    
			// Transition to WaitingScreen, send updates
			Intent intent = new Intent(activity, WaitingActivity.class);
			activity.startActivity(intent);
			activity.finish();
		}
	}
	
}
