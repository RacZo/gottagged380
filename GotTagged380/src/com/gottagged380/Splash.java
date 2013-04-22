package com.gottagged380;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity{

	MediaPlayer mpSplash;
	private boolean pingCheck = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		mpSplash = MediaPlayer.create(this, R.raw.splashsound);
		mpSplash.start();
		
		new NetCheck(this).execute();
		
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(7000);
				} catch (InterruptedException e){
					e.printStackTrace();
				} finally{
					Intent openWelcomeMenu = new Intent("com.gottagged380.WELCOMEMENU");
					openWelcomeMenu.putExtra("Ping", pingCheck);
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
	
	
	public void setNet(boolean check){
		pingCheck = check;
	}

}
