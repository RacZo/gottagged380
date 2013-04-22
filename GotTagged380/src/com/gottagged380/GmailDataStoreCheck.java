package com.gottagged380;

import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.gottagged380.userendpoint.Userendpoint;
import com.gottagged380.userendpoint.model.*;


public class GmailDataStoreCheck extends AsyncTask<Void, Void, Boolean> {
	private String email;
	private WelcomeMenu wMenu;
	private SharedPreferences settings;
	private static final String TAG = "dscheck";

	public GmailDataStoreCheck(WelcomeMenu welcome){
		super();
		wMenu = welcome;
	}

	protected Boolean doInBackground(Void...unused){
		Userendpoint.Builder builder = new Userendpoint.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		builder.setApplicationName("com.gottagged380");
		Userendpoint uEndpoint = builder.build();
		User cUser = null;
		email = wMenu.getEmail();

		try{
			//result = uEndpoint.user().containsUser(cUser).execute();
			cUser = uEndpoint.user().get(email).execute();
		} catch (IOException e){
			e.printStackTrace();
		}
		if(cUser == null){  // FALSE if the name is not in the DataStore
			wMenu.setEmailCheck(false);
			return false;
		} 
		else{
			settings = wMenu.getPref();
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("Email", cUser.getEmail());
			editor.putString("deviceId", cUser.getDeviceRegisterId());
			editor.putString("Username", cUser.getName());
			editor.commit();	

			String email1 = settings.getString("Email", ""); 
			String deviceId1 = settings.getString("deviceId", ""); 
			String userName1 = settings.getString("Username", "");
			Log.v(TAG, email1);
			Log.v(TAG, deviceId1);
			Log.v(TAG, userName1);

			wMenu.setEmailCheck(true); // TRUE if name is already in DataStore.
			return true;
		}
	}


	protected void onPostExecute(Boolean registered){

		if(registered){

			Intent intent1 = new Intent("com.gottagged380.MAINMENU");
			//Intent intent1 = new Intent("com.gottagged380.GAMEPLAYACTIVITY");
			wMenu.startActivity(intent1);
			wMenu.finish();
		}
		else {
			Intent intent2 = new Intent("com.gottagged380.REGISTERUSERNAME");
			intent2.putExtra("Email", email);
			wMenu.startActivity(intent2);
			wMenu.finish();
		}

	}
}