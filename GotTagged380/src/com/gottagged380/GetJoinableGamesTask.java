package com.gottagged380;

import java.io.IOException;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.gottagged380.gamemanager.Gamemanager;
import com.gottagged380.gamemanager.model.AvailableGames;


public class GetJoinableGamesTask extends AsyncTask<Void, Void, Boolean> {
	
	JoinGameActivity jgActivity;
	AvailableGames ag;
	private static final String TAG = "my username:";
	
	public GetJoinableGamesTask( JoinGameActivity jga ){
		super();
		jgActivity = jga;
	}
	
	protected Boolean doInBackground(Void...unused){
		Gamemanager.Builder builder = new Gamemanager.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
	    builder.setApplicationName("com.gottagged380");
		Gamemanager gm = builder.build();

		SharedPreferences settings = jgActivity.getSharedPreferences( "com.gottagged380", 0 );
		String uName = settings.getString( "Username", "" );
		Log.v( TAG, uName );
		
		try{
			jgActivity.setGameList( gm.game().available( uName ).execute() );
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return false; // FALSE if name is already in Datastore.
	}
	
	protected void onPostExecute(Boolean isFree){
		jgActivity.displayResults();
		((Button)jgActivity.findViewById(R.id.refresh_button)).setEnabled(true);
	}
}
