package com.gottagged380;

import java.io.IOException;
import java.net.URLEncoder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.gottagged380.userendpoint.Userendpoint;
import com.gottagged380.userendpoint.model.User;

/**
 * This class is started up as a service of the Android application. It listens
 * for Google Cloud Messaging (GCM) messages directed to this device.
 * 
 * When the device is successfully registered for GCM, a message is sent to the
 * App Engine backend via Cloud Endpoints, indicating that it wants to receive
 * broadcast messages from the it.
 * 
 * Before registering for GCM, you have to create a project in Google's Cloud
 * Console (https://code.google.com/apis/console). In this project, you'll have
 * to enable the "Google Cloud Messaging for Android" Service.
 * 
 * Once you have set up a project and enabled GCM, you'll have to set the
 * PROJECT_NUMBER field to the project number mentioned in the "Overview" page.
 * 
 * See the documentation at
 * http://developers.google.com/eclipse/docs/cloud_endpoints for more
 * information.
 */
public class GCMIntentService extends GCMBaseIntentService {
  private final Userendpoint endpoint;
  

  /*
   * TODO: Set this to a valid project number. See
   * http://developers.google.com/eclipse/docs/cloud_endpoint for more
   * information.
   */
  protected static final String PROJECT_NUMBER = "606819551411";
  private static final String TAG = "GCMLog";

  /**
   * Register the device for GCM.
   * 
   * @param mContext
   *            the activity's context.
   */
  public static void register(Context mContext) {
    GCMRegistrar.checkDevice(mContext);
    GCMRegistrar.checkManifest(mContext);
    GCMRegistrar.register(mContext, PROJECT_NUMBER);
  }

  /**
   * Unregister the device from the GCM service.
   * 
   * @param mContext
   *            the activity's context.
   */
  public static void unregister(Context mContext) {
    GCMRegistrar.unregister(mContext);
  }

  public GCMIntentService() {
    super(PROJECT_NUMBER);
    Userendpoint.Builder endpointBuilder = new Userendpoint.Builder(
        AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
        new HttpRequestInitializer() {
          public void initialize(HttpRequest httpRequest) {
          }
        });
    endpointBuilder.setApplicationName("com.gottagged380");
    endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
  }

  /**
   * Called on registration error. This is called in the context of a Service
   * - no dialog or UI.
   * 
   * @param context
   *            the Context
   * @param errorId
   *            an error message
   */
  @Override
  public void onError(Context context, String errorId) {

    sendNotificationIntent(
        context,
        "Registration with Google Cloud Messaging...FAILED!\n\n"
            + "A Google Cloud Messaging registration error occured (errorid: "
            + errorId
            + "). "
            + "Do you have your project number ("
            + ("".equals(PROJECT_NUMBER) ? "<unset>"
                : PROJECT_NUMBER)
            + ")  set correctly, and do you have Google Cloud Messaging enabled for the "
            + "project?", true, true);
  }

  /**
   * Called when a cloud message has been received.
   */
  @Override
  public void onMessage(Context context, Intent intent) {
	 
	  NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("GotTagged Invite")
		        .setContentText(intent.getStringExtra("message"));
		// Creates an explicit intent for an Activity in your app
	  
		Intent resultIntent = new Intent(this, WelcomeMenu.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		//TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		//stackBuilder.addParentStack(WelcomeMenu.class);
		// Adds the Intent that starts the Activity to the top of the stack
		//stackBuilder.addNextIntent(resultIntent);
		//PendingIntent resultPendingIntent =
		 //       stackBuilder.getPendingIntent(
		 //           0,
		//            PendingIntent.FLAG_UPDATE_CURRENT
		//        );
		//mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		// mId allows you to update the notification later on.
	//	mNotificationManager.notify(mId, mBuilder.build());
		
		/*
	 SharedPreferences myPrefs = this.getSharedPreferences("com.gottagged380", 0);
     SharedPreferences.Editor prefsEditor = myPrefs.edit();
     prefsEditor.putString("GameSessionId", intent.getStringExtra("message"));
     prefsEditor.commit();
	 */
	 Log.v(TAG, intent.getStringExtra("message"));
	// Log.v(TAG, internalStore.getGameId());
   // sendNotificationIntent(
   //     context,
   //     "Message received via Google Cloud Messaging:\n\n"
   //         + intent.getStringExtra("message"), true, false);
   }


  /**
   * Generate a notification intent and dispatch it to the RegisterActivity.
   * This is how we get information from this service (non-UI) back to the
   * activity.
   * 
   * For this to work, the 'android:launchMode="singleTop"' attribute needs to
   * be set for the RegisterActivity in AndroidManifest.xml.
   * 
   * @param context
   *            the application context
   * @param message
   *            the message to send
   * @param isError
   *            true if the message is an error-related message; false
   *            otherwise
   * @param isRegistrationMessage
   *            true if this message is related to registration/unregistration
   */
  private void sendNotificationIntent(Context context, String message,
      boolean isError, boolean isRegistrationMessage) {
    Intent notificationIntent = new Intent(context, GCMRegister.class);
    notificationIntent.putExtra("gcmIntentServiceMessage", true);
    notificationIntent.putExtra("registrationMessage",
        isRegistrationMessage);
    notificationIntent.putExtra("error", isError);
    notificationIntent.putExtra("message", message);
    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(notificationIntent);
  }

  private String getWebSampleUrl(String endpointUrl) {
    // Not the most elegant solution; we'll improve this in the future
    if (CloudEndpointUtils.LOCAL_ANDROID_RUN) {
      return CloudEndpointUtils.LOCAL_APP_ENGINE_SERVER_URL
          + "index.html";
    }
    return endpointUrl.replace("/_ah/api/", "/index.html");
  }

@Override
protected void onUnregistered(Context arg0, String arg1) {
	// TODO Auto-generated method stub
	
}

@Override
protected void onRegistered(Context arg0, String arg1) {
	// TODO Auto-generated method stub
	
}
}
