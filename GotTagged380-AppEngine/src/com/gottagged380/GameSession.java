package com.gottagged380;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;



@PersistenceCapable
public class GameSession {
	
	@PrimaryKey
	@Persistent
	Long gameSessionId;
	@Persistent
	boolean isOver;
	@Persistent
	boolean hasStarted;
	@Persistent
	int gameDuration;
	
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
}
