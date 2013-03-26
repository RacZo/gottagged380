package com.gottagged380;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;


@PersistenceCapable
public class GameRequest {

	String nameOfCreator;
	List<String> nameOfPlayers;
	int duration;
	
	public GameRequest(){
		
	}
	
	public void setNameOfCreator(String name){
		this.nameOfCreator = name;
		
	}
	
	public String getNameOfCreator(){
		return this.nameOfCreator;
	}
	
	public void setNameOfPlayers(List<String> name){
		this.nameOfPlayers = name;
	}
	
	public List<String> getNameOfPlayers(){
		return nameOfPlayers;
	}
	
	public void setDuration(int duration){
		this.duration = duration;
	}
	
	public int getDuration(){
		return duration;
	}
	
}
