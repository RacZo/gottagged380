package com.gottagged380;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class Game {

	@Persistent
	private String creator;
	@Persistent
	private long id;
	
	public void setCreator(String name){
		creator = name;
	}
	
	public String getCreator(){
		return creator;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}
}
