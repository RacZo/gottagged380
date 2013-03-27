package com.gottagged380;


import com.google.appengine.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.Persistent;

import org.mortbay.log.Log;

import java.util.logging.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;


@Api(name = "gamemanager")
public class GameManager {
	private static final Logger log = Logger.getLogger(GameManager.class.getName());
	@SuppressWarnings("unchecked")
	@ApiMethod(name = "game.create")
	public  GameRequestResponse createGame(GameRequest request) throws IOException{
		
		
		GameSession game = new GameSession();
		game.setId(new Date().getTime());
		game.hasStarted(false);
		game.setDuration(request.getDuration());
		saveGameSession(game);
	
			for(int i = 0 ; i< request.getNameOfPlayers().size(); i++){
				//log.info("name " + request.getNameOfPlayers().get(i));
				Player player = new Player();
				User user = getUser(request.getNameOfPlayers().get(i));
				
				String message = "Invite " + request.getNameOfCreator() +" GameSessionId "+ game.getId();
				sendGCMessage(user, message);
				
				player.join(false);
				player.setName(user.getName());
				player.setId(game.getId());
				savePlayer(player);
			}
		
			GameRequestResponse grr = new GameRequestResponse();
			grr.setId(game.getId());
	     return grr;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@ApiMethod(name = "game.join")
	public JoinGame joinGame(JoinGame game) throws Exception{
		
		//log.info("player name " + player.getName());
		GameSession session = getGameSession(game.getId());
		PersistenceManager pm = getPersistenceManager();
		Query q = pm.newQuery(Player.class);
		Player player = new Player();
		q.setFilter("gameSessionId == " + game.getId() + " && name == '" + game.getName() + "'");
		
	    try{
		List<Player> players = (List<Player>) q.execute(); 
	
			player = players.get(0);
			
	    }finally{
	    	q.closeAll();
	    }
		
		if(!session.hasStarted()){
			player.join(game.getJoin());
			pm.makePersistent(player);
		}else{
			throw new Exception("Game Has Already Started");
		}
			
		return game;
	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	private Player getPlayer(long gameSessionId, String name){
		PersistenceManager pm = getPersistenceManager();
		Query q = pm.newQuery(Player.class);
		Player player = new Player();
		q.setFilter("gameSessionId == " + gameSessionId + " && name == '" + name + "'");
		
	    try{
		List<Player> players = (List<Player>) q.execute(); 
	
			player = players.get(0);
			
	    }finally{
	    	q.closeAll();
	    }
	    pm.close();
	    return player;

	}
	
	@SuppressWarnings("unused")
	private void savePlayer(Player player){
		
		PersistenceManager pm = getPersistenceManager();
		
		try {
			pm.makePersistent(player);
			}finally{
				pm.close();
			}
	}
	
	@SuppressWarnings("unchecked")
	private User getUser( String name){
		PersistenceManager pm = getPersistenceManager();
		Query q = pm.newQuery(User.class);
		q.setFilter("name == nameParam");
		q.declareParameters("String nameParam");
		List<User >result;
		try{
		result = (List<User>) q.execute(name);
		}finally{
			q.closeAll();
		}
		return result.get(0);
		
		
	}
	
	/** 
	 * This method will save or updat GameSession into 
	 * datastore
	 * 
	 * @param game
	 */
	private void saveGameSession(GameSession game){
		PersistenceManager pm = getPersistenceManager();
		
		try{
			pm.makePersistent(game);
			
		}finally{
			pm.close();
		}
			
	}
	
	@SuppressWarnings("unused")
	private GameSession getGameSession(long id){
		GameSession game = new GameSession();
		PersistenceManager pm = getPersistenceManager();
		try{
			game = pm.getObjectById(GameSession.class,id);
		}finally{
			
			pm.close();
		}
		
		return game;
	}
	
	private void sendGCMessage(User user, String message) throws IOException{
		GCMHandler gcm = new GCMHandler();
		gcm.sendMessage(user, message);
	}
	
	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}


