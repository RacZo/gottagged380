package com.gottagged380;

import java.util.ArrayList;
import java.util.List;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Gameplay extends Activity {
	Button disButton;
	//TextView disInfo;
	private Gameplay gMenu;
	LocationManager locManager;
	LocationListener locListener;
	private double lon = 0;
	private double lat = 0;
	private long currentGame;
	private Gameplay gpMenu;
	SharedPreferences settings;
	private Gameplay gameplay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameplay);
		gameplay = this;

		disButton = (Button) findViewById(R.id.disButton);

		settings = this.getSharedPreferences("com.gottagged380", 0);

		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		locListener = new MyLocationListener();
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locListener);
		disButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//disInfo.setText("Your GPS is " + counter);
			}
		}); // end of setOnClickListener

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gameplay, menu);
		return true;
	}


	public class MyLocationListener implements LocationListener 
	{
		TextView disInfo = (TextView) findViewById(R.id.disInfo);
		double lon;
		double lat;
		double dist;
		List<Double> latList = new ArrayList<Double>();
		List<Double> lonList = new ArrayList<Double>();
		List<Double> locations = new ArrayList<Double>();
		double average = 0;
		int counter = 0;
		SharedPreferences settings;
		String winner;
		boolean status = false;
		
		
		@Override
		public void onLocationChanged(Location loc){
			Log.d("tag", "Finding Latitude");
			lat = loc.getLatitude();
			Log.d("tag", "Lat: "+String.valueOf(lat));
			Log.d("tag", "Finding Longitude");
			lon = loc.getLongitude();
			Log.d("tag", "Lon: "+String.valueOf(lon));

			String Text = "My current location is: " +
					"\nLatitude = " + lat +
					"\nLongitude = " + lon +
					"\nDistance = " + dist;

			// Display location
			disInfo.setText(Text);

			if(latList.size() == 5){
				latList.remove(0);
				latList.add(lat);
			}else{
				latList.add(lat);
			}

			if(lonList.size() == 5){
				lonList.remove(0);
				lonList.add(lon);
			}else{
				lonList.add(lon);
			}

			new GameplayUpdate(this).execute();

			if(status){
				Intent gameOverIntent = new Intent(gameplay, GameOverActivity.class);
				gameOverIntent.putExtra("Winner", winner);
				startActivity(gameOverIntent);
				gameplay.finish();				
			}
			
			vibe1(dist);

		}

		@Override
		public void onProviderDisabled(String provider){
			Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
		}

		@Override
		public void onProviderEnabled(String provider){
			Toast.makeText(getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras){
		}

		public void vibe1(double distance){
			Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			
			if(dist <= 25){
				long[] pattern = {0, 25};
				v.vibrate(pattern, -1);
			}

			else if(dist > 25 && dist <= 75){
				long[] pattern = {0, 50};
				v.vibrate(pattern, -1);
			}

			else if(dist > 75){
				long[] pattern = {0, 75};
				v.vibrate(pattern, -1);
			}

		}

		public double getLong(){

			double tempLon = 0;
			int counter = 0;
			for(double l : lonList){
				tempLon += l;
				counter++;
			}
			return lon;
			//return tempLon/counter;
		}

		public double getLat(){

			double tempLat = 0;
			int counter = 0;

			for(double l : latList){
				tempLat += l;
				counter++;
			}

			return lat;
			//return tempLat/counter;
		}

		public void setDist(double dis){
			dist = dis;
		}

		public Long getCurrentGame(){	

			SharedPreferences settings = getShared();
			Long sid = settings.getLong("CurrentGame", 0);
			return sid;
		}

		public SharedPreferences getShared(){
			return getSharedPreferences("com.gottagged380", 0);
		}
		
		public void setWinner(String name){
			winner = name;
		}
		
		public void setGameStatus(boolean value){
			status = value;
		}
	}//end of LocationListenerClass

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		locManager.removeUpdates(locListener);
		settings.edit().remove("CurrentGame");
	}

}
