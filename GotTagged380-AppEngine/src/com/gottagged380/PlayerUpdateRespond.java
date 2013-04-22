package com.gottagged380;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class PlayerUpdateRespond {

	@Persistent
	private double distance;
	@Persistent
	private long gameSessionId;
	@Persistent
	private boolean hasEnded = false;
	@Persistent
	private String winner = "";
	@Persistent
	
	public void setDistence(double distance){
		this.distance = distance;
	}
	
	public double getDistance(){
		return distance;
	}
	
	public void setId(long id){
		this.gameSessionId = id;
	}
	
	
	public long getId(){
		return gameSessionId;
	}
	
	public void setHasEnded(boolean end){
		this.hasEnded = end;
	}
	
	public boolean getHasEnded(){
		return hasEnded;
	}
	
	public void setWinner(String winner){
		
		this.winner = winner;
	}
	
	public String getWinner(){
		
		return winner;
	}
}
