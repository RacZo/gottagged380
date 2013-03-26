package com.gottagged380;
import java.util.Date;

import javax.persistence.*;

@Entity
public class User {
	
	 @Id
	 private String name;	
	 private String email;
	 private String deviceRegisterId;
	 private String deviceType;
	 private Date dateRegistered;
	
	 public User() {

	 }

	 public User(String name, String email, String deviceType, String deviceId) {
	  this.name = name;
	  this.email = email;
	  this.deviceRegisterId = deviceId;	
	  this.dateRegistered = new Date();
	 }

	 public String getName() {
	  return name;
	 }
	 
	 public String getEmail(){
		 return email;
	 }
	 
	 public String getDeviceRegisterId(){
		 return deviceRegisterId;
	 }
	 
	 public String getDeviceType(){
		 return deviceType;
	 }
	 
	 public void setName(String name){
		 this.name = name;
	 }
	 public void setDeviceRegisterId(String id){
		 this.deviceRegisterId = id;
	 }
	 public void setDeviceType(String type){
		 this.deviceType = type;
	 }
	 
}	



