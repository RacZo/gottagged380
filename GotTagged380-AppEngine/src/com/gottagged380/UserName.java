package com.gottagged380;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class UserName {
	
	private boolean isAvailable;
	private String name;
	
	
	public String getname(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void isAvailable(boolean status){
		this.isAvailable = status;
	}
	
	public boolean isAvailable(){
		return isAvailable;
	}
}
