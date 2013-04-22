package com.gottagged380;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;



public class NetCheck extends AsyncTask<String, Void, Boolean> {
	
	private Splash splash;
	private static final String TAG = "pingcheck";
	
	public NetCheck(Splash menu){
		super();
		splash = menu;
	}

	protected Boolean doInBackground(String...params){
		
		ConnectivityManager cm = (ConnectivityManager) splash.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			try {
				URL url = new URL("http://www.google.com");
				HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
				urlc.setConnectTimeout(2000);
				urlc.connect();
				if (urlc.getResponseCode() == 200) {
					Log.v(TAG, "here");
					return true;
				}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	
	protected void onPostExecute(Boolean result){
		if(result)
			splash.setNet(true);
		else 
			splash.setNet(false);
	}
}
