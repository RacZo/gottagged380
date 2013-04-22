package com.gottagged380;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;


import java.util.logging.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;


@Api(name = "gamemanager")
public class GameManager {
	private static final Logger log = Logger.getLogger(GameManager.class.getName());
	
	
	
	/**
	 * this method uses http get to create a game session 
	 * the invitation of other players are handled here 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	@ApiMethod(name = "game.create")
	public  GameRequestResponse createGame(GameRequest request) throws Exception{
		PersistenceManager pm = getPersistenceManager();
		User user = new User();
		List<Player> players = new ArrayList<Player>();
		GameSession game = new GameSession();
			game.setId(new Date().getTime());
			game.hasStarted(false);
			game.setDuration(request.getDuration());
			game.setNumPlayers(request.getNameOfPlayers().size()+1);
			game.setCreator(request.getNameOfCreator());
			
		
		Player creator = new Player();
			creator.setId(game.getId());
			creator.setName(request.getNameOfCreator());
			creator.join(true);
			creator.hasResponded(true);
			players.add(creator);
			
		
			for(int i = 0 ; i< request.getNameOfPlayers().size(); i++){
				Player player = new Player();
				try{
				 user = getUser(request.getNameOfPlayers().get(i));
				}
				 catch(java.lang.IndexOutOfBoundsException e){
					 throw new Exception("User " + request.getNameOfPlayers().get(i) + " does not exsist");
				 }
					
				String message = "Invite " + request.getNameOfCreator() +" GameSessionId "+ game.getId();
				sendGCMessage(user, message);
				
				player.join(false);
				player.setName(user.getName());
				player.setId(game.getId());
				players.add(player);
				//pm.makePersistent(player);
			}
		
			GameRequestResponse grr = new GameRequestResponse();
			grr.setId(game.getId());
			
			pm.makePersistent(game);
			pm.makePersistentAll(players);
			pm.close();
	     return grr;
	}
	/**
	 * this method let the client to check to see if the game has 
	 * started or not
	 * @param request
	 * @return
	 */
	@ApiMethod(name = "game.status")
	public GameStatus checkGameStatus(@Named("id") long request , @Named("name") String name){
		GameStatus respond = new GameStatus();
		PersistenceManager pm = getPersistenceManager();
		GameSession game = pm.getObjectById(GameSession.class, request);
		
		if(game.getHider().equalsIgnoreCase(name)){
			respond.setRole("hider");
		}else{
			respond.setRole("seeker");		}
		respond.setStatus(game.hasStarted());
		respond.setGameId(request);
		pm.close();
		return respond;
		
	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "game.available")
	public AvailableGames getAvailableGame(@Named("name") String name){
		AvailableGames games = new AvailableGames();
		PersistenceManager pm = getPersistenceManager();
		Query q = pm.newQuery(Player.class);
		
		q.setFilter(" name == '" + name + "'" + " && hasResponded == false");
		log.info("playername " + name);
		List<Player> playersList;
		List<Game> availableGames = new ArrayList<Game>();
		try{ 
			playersList = (List<Player>) q.execute();
		
		log.info("playerslist " +playersList.size());
		for(Player p : playersList){
			log.info("Player name = " +p.getName() + " gameid = " + p.getId() +"\n");
			GameSession gameSession = pm.getObjectById(GameSession.class, p.getId());
			log.info("game found");
			
			if(!gameSession.hasStarted()){
				Game game = new Game();
				log.info("made game");
				game.setCreator(gameSession.getCreator());
				game.setId(gameSession.getId());
				log.info("game is set");
				availableGames.add(game);
				log.info("we added gameid " + p.getId());
			}
			
		}
		}finally{
			q.closeAll();
		}
		games.setGames(availableGames);
		pm.close();
		return games;
	}
	
	/**
	 * this method allows user to join a game that 
	 * they were invited to.
	 * it calls the startGame method after each join
	 * to check if all the player have joined and if the game can start
	 * 
	 * @param game
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	@ApiMethod(name = "game.join")
	public JoinGame joinGame(JoinGame game) throws Exception{
		
		
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
			player.hasResponded(true);
			pm.makePersistent(player);
		}else{
			throw new Exception("Game Has Already Started");
		}
			
		startGame(game.getId());
		return game;
	}
	
	
	/**
	 * this method receives players update (Latitude, Longitude)
	 * and return the distance from the hider.
	 * in case if the player is the hider, it retuns the distance to the 
	 * closest player 
	 * @param update
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "player.update")
	public PlayerUpdateRespond playerUpdate(PlayerUpdate update) throws Exception{
		PlayerUpdateRespond playerRespond = new PlayerUpdateRespond();
		double dis = 0;
		PersistenceManager pm = getPersistenceManager();
		GameSession game = pm.getObjectById(GameSession.class, update.getId());
		Query q = pm.newQuery(Player.class);
		q.setFilter("gameSessionId == "+ update.getId());
		List<Player> players ;
		
		if(game.isOver()){
			
			playerRespond.setHasEnded(true);
			playerRespond.setWinner(game.getWinner());
			return playerRespond;
		}
			
		
		if(game.getHider().equalsIgnoreCase(update.getName() )){
			log.info(game.getHider()+ "is the hider ");
			
			playerRespond.setDistence(getClosestPlayer(update));
	
			
			return playerRespond;
		}
		
	    try{
	    	
	    	 players = (List<Player>) q.execute(); 
		
			
	    }finally{
	    	q.closeAll();
	    	
	    }
	    for(Player player : players){
			if(player.getRole().equalsIgnoreCase("hider")){
			
				playerRespond.setDistence(getDistanceFromHider(player.getLatitude(), player.getLongitude(), update.getLatitude(), update.getLongitude()));
				
			}
			
			if(player.getName().equalsIgnoreCase(update.getName())){
				player.setLatitude(update.getLatitude());
				player.setLongitude(update.getLongitude());
		
				pm.makePersistent(player);
			}
	    }
	    playerRespond.setHasEnded(false);
	    log.info("player res game " + playerRespond.getHasEnded());
	    if(playerRespond.getDistance() <= 5){
	    	game.setWinner(update.getName());
	    	game.isOver(true);
	    	playerRespond.setWinner(update.getName());
	    	playerRespond.setHasEnded(true);
	    	pm.makePersistent(game);
	    
	    }
	    
	   pm.close();
	   
	  
	   return playerRespond;
	}
	
	/**
	 * this method uses the GPSDistance to calculate the 
	 * distance between two GPS location 
	 * 
	 * @param latitude
	 * @param longitude
	 * @param latitude2
	 * @param longitude2
	 * @return distance
	 */

	private double getDistanceFromHider(double latitude, double longitude,
			double latitude2, double longitude2) {
			
			GPSDistance gps = new GPSDistance();
		
			return gps.getDistance(latitude, longitude, latitude2, longitude2);
	}



	@SuppressWarnings({ "unchecked", "unused" })
	private double getClosestPlayer(PlayerUpdate update) {
		//way too far 
		double distance = 1000000000000000.0;
		double tempDistance;
		GPSDistance gps = new GPSDistance();
		PersistenceManager pm = getPersistenceManager();
		List<Player> playersList;
		Query q = pm.newQuery(Player.class);
		q.setFilter("gameSessionId == "+ update.getId());
		
		
		try {
			playersList = (List<Player>) q.execute(); 
			for(Player p : playersList){
				if(p.getName().equalsIgnoreCase(update.getName())){
					p.setLatitude(update.getLatitude());
					p.setLongitude(update.getLongitude());
					pm.makePersistent(p);
				}else{
					tempDistance = gps.getDistance(p.getLatitude(), p.getLongitude(), update.getLatitude(), update.getLongitude());
					
					if(tempDistance < distance)
						distance = tempDistance;
				}
			}
			
		}finally{
			q.closeAll();
			pm.close();
		}
		return distance;
	}



	/**
	 * startGame will be called after every joinGame
	 * it will check to see if all the users have joined.
	 * starts the game and send GCM to all the users 
	 * enabling them to send PlayerUpdate
	 * @param gameSessionId
	 * @throws Exception 
	 */
	 @SuppressWarnings("unchecked")
	private void startGame(long id) throws Exception{
		 User user;
		 int pickHider;
		 List<Player> players;
		 PersistenceManager pm = getPersistenceManager();
		 GameSession game = pm.getObjectById(GameSession.class, id);
		 Random random = new Random();
	
		 
		 Query q = pm.newQuery(Player.class);
		 q.setFilter("gameSessionId == " + id + " && hasJoined == true" );
		 
		
		 try{ 
			 players = (List<Player>) q.execute();
		 }finally{
			 q.closeAll();
		 }
		 
		 
		 pickHider = random.nextInt(players.size());
		 
		 if( players.size() == game.getNumPlayers()){
			 for(int i = 0 ; i < players.size(); i++){
				 user = getUser(players.get(i).getName());
				 if(i == pickHider){
					 String message = "Start " + id + " role hider";
					 sendGCMessage(user, message);
					 players.get(i).setRole("hider");
					 game.setHider(players.get(i).getName());
					 
				 }else{
					 String message = "Start " + id + " role seeker";
					 sendGCMessage(user, message);
					 players.get(i).setRole("seeker");
				 }
				 pm.makePersistent(players.get(i));
			 }
			 pm.makePersistent(game);
			 game.hasStarted(true);
			 pm.close();
			 
			 
			
		 }
		 
	 }
	
	
	
	
	@SuppressWarnings("unchecked")
	private User getUser( String name) throws Exception{
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


