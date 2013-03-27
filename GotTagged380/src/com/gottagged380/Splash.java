package com.gottagged380;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity{

	MediaPlayer mpSplash;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		mpSplash = MediaPlayer.create(this, R.raw.splashsound);
		mpSplash.start();
		
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(3000);
				} catch (InterruptedException e){
					e.printStackTrace();
				} finally{
					Intent openWelcomeMenu = new Intent("com.gottagged380.WELCOMEMENU");
					startActivity(openWelcomeMenu);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mpSplash.release();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mpSplash.pause();
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mpSplash.start();
	}
	
	
	

}
