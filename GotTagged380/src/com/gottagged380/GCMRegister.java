package com.gottagged380;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class GCMRegister extends Activity {

	/**
	 * An activity that communicates with your App Engine backend via Cloud
	 * Endpoints.
	 * 
	 * When the user hits the "Register" button, a message is sent to the backend
	 * (over endpoints) indicating that the device would like to receive broadcast
	 * messages from it. Clicking "Register" also has the effect of registering this
	 * device for Google Cloud Messaging (GCM). Whenever the backend wants to
	 * broadcast a message, it does it via GCM, so that the device does not need to
	 * keep polling the backend for messages.
	 * 
	 * If you've generated an App Engine backend for an existing Android project,
	 * this activity will not be hooked in to your main activity as yet. You can
	 * easily do so by adding the following lines to your main activity:
	 * 
	 * Intent intent = new Intent(this, RegisterActivity.class);
	 * startActivity(intent);
	 * 
	 * To make the sample run, you need to set your PROJECT_NUMBER in
	 * GCMIntentService.java. If you're going to be running a local version of the
	 * App Engine backend (using the DevAppServer), you'll need to toggle the
	 * LOCAL_ANDROID_RUN flag in CloudEndpointUtils.java. See the javadoc in these
	 * classes for more details.
	 * 
	 * For a comprehensive walkthrough, check out the documentation at
	 * http://developers.google.com/eclipse/docs/cloud_endpoints
	 */

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //setContentView(R.layout.activity_register);

	            try {
	              GCMIntentService.register(getApplicationContext());
	            } catch (Exception e) {
	              Log.e(GCMRegister.class.getName(),
	                  "Exception received when attempting to register for Google Cloud "
	                      + "Messaging. Perhaps you need to set your virtual device's "
	                      + " target to Google APIs? "
	                      + "See https://developers.google.com/eclipse/docs/cloud_endpoints_android"
	                      + " for more information.", e);
	              showDialog("There was a problem when attempting to register for "
	                  + "Google Cloud Messaging. If you're running in the emulator, "
	                  + "is the target of your virtual device set to 'Google APIs?' "
	                  + "See the Android log for more details.");
	            }
	            
	  }

	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    //getMenuInflater().inflate(R.menu.activity_register, menu);
	    return true;
	  }

	  private void showDialog(String message) {
	    new AlertDialog.Builder(this)
	        .setMessage(message)
	        .setPositiveButton(android.R.string.ok,
	            new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int id) {
	                dialog.dismiss();
	              }
	            }).show();
	  }

	}
