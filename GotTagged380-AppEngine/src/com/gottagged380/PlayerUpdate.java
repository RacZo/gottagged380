package com.gottagged380;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
@PersistenceCapable
public class PlayerUpdate {

		@Persistent
		private double latitude;
		@Persistent
		private double longitude;
		@Persistent
		private Long gameSessionId;
		
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
		
		
		
		public void setName(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
		
	}


