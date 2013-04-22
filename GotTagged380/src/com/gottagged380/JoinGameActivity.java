package com.gottagged380;

import java.util.LinkedList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.gottagged380.gamemanager.model.AvailableGames;
import com.gottagged380.gamemanager.model.Game;

public class JoinGameActivity extends ListActivity{
	
	ArrayAdapter<String> arrAd;
	List<Game> aGames;
	private static final String TAG_POSITION = "ListPos";
	private static final String TAG_SESSIONID = "SessionID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_game);
		aGames = null;
		
		LinkedList<String> lls = new LinkedList<String>();
		arrAd = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lls );
		setListAdapter(arrAd);
		((Button)findViewById(R.id.refresh_button)).setEnabled(false);
		new GetJoinableGamesTask(this).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.join_game, menu);
		return true;
	}

	public void onListItemClick( ListView l, View view, int position, long id ){
		// get SessionId corresponding to this list item
		if( !(aGames == null) && !aGames.isEmpty() ){
			super.onListItemClick(l, view, position, id);
			Long sessionId = aGames.get( position ).getId();
			new JoinGameTask(this).execute(sessionId);
		}
	}
	
	public void setGameList( AvailableGames ag ){  
		// stores Game objects from AvailableGames class
		aGames = ag.getGames();
	}
	
	public void refreshList( View view ){   // occurs when user hits Refresh button
		((Button)findViewById(R.id.refresh_button)).setEnabled(false);
		new GetJoinableGamesTask(this).execute();
	}
	
	public void displayResults(){
		arrAd.clear();
		if( aGames != null ){
			for( int i = 0; i < aGames.size(); i++ ){
				arrAd.add( aGames.get(i).getCreator() + aGames.get(i).getId().toString() );
			}
		} else arrAd.add( "No Available Games." );
		arrAd.notifyDataSetChanged();
	}
}
