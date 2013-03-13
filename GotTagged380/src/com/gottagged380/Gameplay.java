package com.gottagged380;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Gameplay extends Activity {
	Button disButton;
	//TextView disInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameplay);
		
		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener locListener = new MyLocationListener();
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locListener);
		disButton = (Button) findViewById(R.id.disButton);
	//	disInfo = (TextView) findViewById(R.id.disInfo);

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

        @Override
        public void onLocationChanged(Location loc){
            Log.d("tag", "Finding Latitude");
            double lat = loc.getLatitude();
            Log.d("tag", "Lat: "+String.valueOf(lat));
            Log.d("tag", "Finding Longitude");
            double lon = loc.getLongitude();
            Log.d("tag", "Lon: "+String.valueOf(lon));
            String Text = "My current location is: " +
            "\nLatitude = " + lat +
            "\nLongitude = " + lon;

            // Display location
            disInfo.setText(Text);
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
    }
}
