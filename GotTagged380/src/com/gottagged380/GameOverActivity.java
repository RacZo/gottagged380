package com.gottagged380;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class GameOverActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		String winner = "UNKNOWN_PLAYER";
		Intent intent = getIntent();
		winner = intent.getStringExtra("Winner");
		((TextView)findViewById(R.id.game_over_msg)).setText( winner + " is the winner!");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_over, menu);
		return true;
	}

}
