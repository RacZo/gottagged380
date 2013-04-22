package com.gottagged380;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;



@PersistenceCapable
public class GameSession {
	
	@PrimaryKey
	@Persistent
	private Long gameSessionId;
	@Persistent
	private boolean isOver;
	@Persistent
	private boolean hasStarted;
	@Persistent
	private int gameDuration;
	@Persistent
	private int numPlayers;
	@Persistent
	private String hider;
	@Persistent
	private String creator;
	@Persistent
	private String winner;
	
	public GameSession(){
		
	}
	
	public void setId(Long id){
		 gameSessionId = id;
	}
	
	public Long getId(){
		return gameSessionId;
	}
	
	public void isOver(boolean status){
		this.isOver = status;
	}

	public boolean isOver(){
		return isOver;
	}
	
	public void hasStarted(boolean status){
		this.hasStarted = status;
	}
	
	public boolean hasStarted(){
		return hasStarted;
	}
	
	public void setDuration(int duration){
		this.gameDuration = duration;
	}
	
	public int getDuration(){
		return gameDuration;
	}
	
	public int getNumPlayers(){
		return numPlayers;
	}
	
	public void setNumPlayers(int num){
		numPlayers = num;
	}
	
	public String getHider(){
		return hider;
	}
	
	public void setHider(String name){
		hider = name;
	}
	
	public String getCreator(){
		return creator;
	}
	
	public void setCreator(String name){
		creator = name;
	}
	public void setWinner(String winner){
		
		this.winner = winner;
	}
	
	public String getWinner(){
		
		return winner;
	}
}
