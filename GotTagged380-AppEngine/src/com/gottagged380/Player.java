package com.gottagged380;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Player {

	@Persistent
	private double latitude;
	@Persistent
	private double longitude;
	@Persistent
	private Long gameSessionId;
	@Persistent
	private boolean hasJoined;
	@Persistent
	private String role;
	@Persistent
	private String name;
	
	
	public void setLatitude(double lat){
		
		this.latitude = lat;
	}
	
	public double getLatitude(){
		
		return latitude;
	}
	
	public void setLongitude(double lon){
		
		this.longitude = lon;
	}
	
	public double getLongitude(){
		
		return longitude;
	}
	
	
	public void setId(long id){
		this.gameSessionId = id;
	}
	
	
	public long getId(){
		return gameSessionId;
	}
	
	public void join(boolean join){
		this.hasJoined = join;
	}
	
	public boolean hasJoined(){
		return hasJoined;
	}
	
	public void setRole(String role){
		
		this.role = role;
	}
	
	public String getRole(){
		
		return role;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	
}
