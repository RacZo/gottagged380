package com.gottagged380;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
@PersistenceCapable
public class AvailableGames {

	@Persistent
	private List<Game> games;
	
	public List<Game> getGames(){
		return games;
	}
	
	public void setGames(List<Game> game){
		games = game;
	}
	
	

}
