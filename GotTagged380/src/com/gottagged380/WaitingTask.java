package com.gottagged380;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.gottagged380.gamemanager.Gamemanager;
import com.gottagged380.gamemanager.model.GameStatus;

public class WaitingTask extends AsyncTask<Void, Void, Boolean> {

	WaitingActivity activity;
	public static final String TAG = "waitGame";
	public static final String TAG2 = "waitGameId";
	private static final String TAG3 = "role";
	
	public WaitingTask( WaitingActivity a ){
		super();
		activity = a;
	}
	
	public Boolean doInBackground( Void...unused){
		Gamemanager.Builder builder = new Gamemanager.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
	    builder.setApplicationName("com.gottagged380");
		Gamemanager gm = builder.build();
		boolean allPlayersReady = false;
		
		SharedPreferences settings = activity.getSharedPreferences( "com.gottagged380", 0 );
		String sidString = settings.getString( "SessionId", "UNKNOWN_GAME" );
		String name = settings.getString("Username", "");
		Long sid = Long.valueOf(sidString);
		
		GameStatus readyToGo= new GameStatus();
		readyToGo.setStatus(false);
		boolean start = false;
		
		//while( !start){
			try{
				//sleep(2000);
				SharedPreferences.Editor editor = settings.edit();
				editor.putLong("CurrentGame", sid);
				editor.commit();
				Log.v(TAG2, sid + "");
				
				readyToGo = gm.game().status(sid, name).execute();
				start = readyToGo.getStatus();
				Log.v( TAG, readyToGo.getGameId().toString() + " " + readyToGo.getStatus() );
				Log.v(TAG3, readyToGo.getRole());
			} catch (IOException e){
				e.printStackTrace();
			}
		//}
		
		
		//Log.v( TAG, uName );
		return start;
	}

	public void onPostExecute( Boolean allJoin ){
		if( allJoin){
		activity.readyToGo = true;
		Intent intent = new Intent(activity, Gameplay.class);		
		activity.startActivity(intent);
		activity.finish();
		}
	}
}