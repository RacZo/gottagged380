package com.gottagged380;

import java.io.IOException;

import android.app.Activity;
import com.google.android.gcm.GCMRegistrar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.gottagged380.userendpoint.Userendpoint;
import com.gottagged380.userendpoint.model.User;
import com.gottagged380.userendpoint.model.UserName;
//import android.app.FragmentManager;
//import android.content.Context;


public class UserEndpointTask extends AsyncTask<Void, Void, Boolean> {
	private String uName;
	private RegisterUsername activ;


	public UserEndpointTask(RegisterUsername a){
		super();
		uName="";
		activ = a;
	}

	protected Boolean doInBackground(Void...unused){
		Userendpoint.Builder builder = new Userendpoint.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		builder.setApplicationName("com.gottagged380");
		Userendpoint uEndpoint = builder.build();
		SharedPreferences settings = activ.getPref();


		String email = activ.getEmail();
		String deviceId = GCMRegistrar.getRegistrationId(activ);

		EditText edtxt = (EditText) activ.findViewById(R.id.username_entry);
		uName = edtxt.getText().toString();

		UserName un = new UserName();
		UserName result = null;
		un.setName(uName);

		try{
			result = uEndpoint.username().check(un).execute();
		} catch (IOException e){
			e.printStackTrace();
		}
		if( result.getAvailable() ){  // TRUE if the name is not in the datastore
			User nUser = new User();

			nUser.setName(uName);
			nUser.setDeviceRegisterId(deviceId);
			nUser.setEmail(email); 

			SharedPreferences.Editor editor = settings.edit();
			editor.putString("Email", nUser.getEmail());
			editor.putString("deviceId", nUser.getDeviceRegisterId());
			editor.putString("Username", nUser.getName());
			editor.commit();	

			try {
				uEndpoint.user().insert(nUser).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} else return false; // FALSE if name is already in Datastore.

	}

	protected void onPostExecute(Boolean isFree){
		if( isFree ){
			Intent intent = new Intent(activ, GCMRegister.class);
			Thread timer = new Thread(){
				public void run(){
					try{
						sleep(3000);
					} catch (InterruptedException e){
						e.printStackTrace();
					} finally{
						activ.startActivity(new Intent("com.gottagged380.MAINMENU"));
					}
				}
			};
			timer.start();
		}
		else {
			// modify text and try again
			TextView tView = (TextView) activ.findViewById(R.id.textView1);
			tView.setText(uName + " has already been taken.\nEnter a new name.");
			activ.enableButton();
		}
	}
}
