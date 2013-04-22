package com.gottagged380;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gcm.GCMRegistrar;

import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;

public class WelcomeMenu extends Activity {

	private AccountManager accMgr = null;
	private WelcomeMenu wMenu;
	private WelcomeMenu WelcomeMenuActivity = this;
	private boolean networkState;
	private boolean pingCheck;
	private boolean emailCheck = false;
	AccountManagerFuture<Bundle> futur;
	String email;
	SharedPreferences settings;
	LocationManager locationManager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		wMenu = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_menu);

		pingCheck = getIntent().getExtras().getBoolean("Ping");


		List<String> list = new ArrayList<String>();
		Spinner emailSpinner;
		settings = getSharedPreferences("com.gottagged380", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();

		final String PROJECT_NUMBER = "606819551411";
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, PROJECT_NUMBER);
			regId = GCMRegistrar.getRegistrationId(this);
		}

		try {
			accMgr = AccountManager.get(this);
			Account[] accounts = accMgr.getAccountsByType("com.google");
			String accountsList = "Accounts: " + accounts.length + "\n";

			for (Account account : accounts)
				accountsList += account.toString() + "\n";

			for (int i = 0; i < accounts.length; i++)
				list.add(accounts[i].name.toString());

			// setMessage(accountsList);
		} catch (Exception e) {
			setMessage(e.toString());
		}

		
		
		if (list.size() > 0) {
			emailSpinner = (Spinner) findViewById(R.id.spinner);
			ArrayAdapter<String> emailAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item, list);
			emailSpinner.setAdapter(emailAdapter);
			emailSpinner.setOnItemSelectedListener(new EmailSelector());
			// email = emailSpinner.getSelectedItem().toString();
		} else {
			emailSpinner = (Spinner) findViewById(R.id.spinner);
			String invalidEmail = "You need a Gmail account linked to the phone";
			list.add(invalidEmail);
			ArrayAdapter<String> emailAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item, list);
			emailSpinner.setAdapter(emailAdapter);
		}

	}

	private class EmailSelector implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			String selected = parent.getItemAtPosition(pos).toString();
			setEmail(selected);

			settings = getSharedPreferences("com.gottagged380", 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("Email", email);
			editor.commit();
		}

		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	};

	private OnClickListener mLoginListener = new OnClickListener() {

		public void onClick(View v) {
			try {
				Button loginButton = (Button) findViewById(R.id.login);
				loginButton.setText("Logging In..");
				loginButton.setEnabled(false);
				Account[] accounts = accMgr.getAccountsByType("com.google");
				if (accounts.length == 0) {
					setResult("No Accounts");
					return;
				}

				Account account = accounts[0];
				futur = accMgr.getAuthToken(account, "mail", null, wMenu,
						new GetAuthTokenCallback(), null);

				new GmailDataStoreCheck(WelcomeMenuActivity).execute();

			} catch (Exception e) {
				setResult(e.toString());
			}
		}
	};

	private class GetAuthTokenCallback implements
	AccountManagerCallback<Bundle> {
		public void run(AccountManagerFuture<Bundle> result) {
			Bundle bundle;
			try {
				bundle = result.getResult();
				Intent intent = (Intent) bundle.get(AccountManager.KEY_INTENT);

				if (intent != null) {
					// User input required
				} else {// Authentication is successful
					// this.setResult("Token:  " +
					// bundle.getString(AccountManager.KEY_AUTHTOKEN));
					// startActivity(intent2);
					// finish();

				}
			} catch (Exception e) {
				wMenu.setResult(e.toString());
			}
		}
	};

	public void setResult(String msg) {
		TextView tv = (TextView) this.findViewById(R.id.auth);
		tv.setText(msg);
	}

	public void setMessage(String msg) {
		TextView tv = (TextView) this.findViewById(R.id.auth);
		tv.setText(msg);
	}

	public void setEmailCheck(boolean check) {
		emailCheck = check;
	}

	public void setEmail(String finEmail) {
		email = finEmail;
	}

	public String getEmail() {
		return email;
	}

	public SharedPreferences getPref() {
		return settings = getSharedPreferences("com.gottagged380", 0);
	}

	public void setNet(boolean check){
		pingCheck = check;
	}

	private void showGPSDisabledAlertToUser() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
		.setMessage(
				"GPS is disabled in your device. Would you like to enable it?")
				.setCancelable(false)
				.setPositiveButton("Enable GPS Settings",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent callGPSSettingIntent = new Intent(
								android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(callGPSSettingIntent);
					}
				});
		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				finish();
			}
		});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();


		if(pingCheck){
			Button loginButton = (Button) findViewById(R.id.login);
			loginButton.setOnClickListener(mLoginListener);
		}
		else{
			Toast.makeText(this, "No internet connectivity",
					Toast.LENGTH_SHORT).show();
		}


		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "GPS is Enabled in your device",
					Toast.LENGTH_SHORT).show();
		}
		else 
			showGPSDisabledAlertToUser();
	}


}
