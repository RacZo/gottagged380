package com.gottagged380;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.gottagged380.Gameplay.MyLocationListener;
import com.gottagged380.gamemanager.Gamemanager;
import com.gottagged380.gamemanager.model.PlayerUpdate;
import com.gottagged380.gamemanager.model.PlayerUpdateRespond;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;



public class GameplayUpdate extends AsyncTask<Void, Void, Boolean> {
	
	private static final String TAG = "gamecheck";
	private static final String TAG5 = "gameidcheck";
	private static final String TAG6 = "uncheck";
	private MyLocationListener locListen;
	
	public GameplayUpdate(MyLocationListener menu){
		super();
		locListen = menu;
	}

	protected Boolean doInBackground(Void...unused){
		
		Gamemanager.Builder builder = new Gamemanager.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
	    builder.setApplicationName("com.gottagged380");
		Gamemanager gEndpoint = builder.build();
		
		PlayerUpdate playerUpdate = new PlayerUpdate();
		long id = locListen.getCurrentGame();
		
		String gameIdString = id + "";
		Log.v(TAG5, gameIdString);
		
		SharedPreferences settings = locListen.getShared();
		String name = settings.getString("Username", "");
		Log.v(TAG6, name);
		
		playerUpdate.setId(id);
		playerUpdate.setName(name);
		playerUpdate.setLatitude(locListen.getLat());
		playerUpdate.setLongitude(locListen.getLong());
		PlayerUpdateRespond playerUpdateResponse = new PlayerUpdateRespond();
		
		try{
			playerUpdateResponse = gEndpoint.player().update(playerUpdate).execute();
			locListen.setDist(playerUpdateResponse.getDistance());
			
			
			
			//if(playerUpdateResponse.getHasEnded() != null)
			if(playerUpdateResponse.getHasEnded()){
				locListen.setWinner(playerUpdateResponse.getWinner());
				locListen.setGameStatus(true);
			}
			
			return true;
		} catch (IOException e){
			e.printStackTrace();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		return false;
	}

	
	protected void onPostExecute(Boolean result){
		
		
	}
}
