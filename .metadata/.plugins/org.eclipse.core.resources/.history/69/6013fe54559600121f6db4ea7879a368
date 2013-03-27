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
	
	public GameSession(){
		
	}
	
	public void setId(Long id){
		 gameSessionId = id;
	}
	
	public Long getId(){
		return gameSessionId;
	}


}
