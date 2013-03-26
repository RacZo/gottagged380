package com.gottagged380;


import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;


@PersistenceCapable
public class GameRequestResponse {
	
	@Persistent
	private Long gameSessionId;
	
	public void setId(Long id){
		this.gameSessionId = id;
	}
	
	public Long getId(){
		return gameSessionId;
	}
	
		
	
}
