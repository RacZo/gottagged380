package com.gottagged380;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.gottagged380.gamemanager.Gamemanager;

public class WaitingActivity extends Activity {
	
	//Gamemanager gm;
	//final WaitingTask checkGame = new WaitingTask(this);
	Boolean readyToGo = false;
	WaitingActivity activ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waiting);
		//checkGame = new WaitingTask(this);//.execute();
		activ = this;
		Thread waitLoop = new Thread(){
			public void run(){
				//WaitingTask checkGame = new WaitingTask( activ );
				while( !readyToGo ){
					WaitingTask checkGame = new WaitingTask( activ );
					try{
						checkGame.execute(); // sets readyToGo to true
						sleep(5000);
					} catch (InterruptedException e){
						e.printStackTrace();
					} 
				}
			}
		};
		waitLoop.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.waiting, menu);
		return true;
	}

}
