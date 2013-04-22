package com.gottagged380;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class GameStatus {

	@Persistent
	private boolean status;
	@Persistent
	private long gameSessionId;
	@Persistent
	private String role;
	
	public GameStatus(){};
	
	public void setStatus(boolean status){
		this.status = status;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	public void  setGameId(long id){
		
		this.gameSessionId = id;
	}
	
	public long getGameId(){
		return gameSessionId;
	}
	
	public String getRole(){
		return role;
	}
	
	public void setRole(String role){
		this.role = role;
	}

	
}
