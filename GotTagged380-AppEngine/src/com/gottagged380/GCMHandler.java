package com.gottagged380;

import java.io.IOException;

import javax.inject.Named;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.response.CollectionResponse;

public class GCMHandler {
	
	 private static final String API_KEY = "AIzaSyBoZIotz2n3A9dxqAZxLQpwyPElgjQWUj0";

	 
	 public void sendMessage(User user, String message) throws IOException{
		 
		 Sender sender = new Sender(API_KEY);
		 
		 Message msg = new Message.Builder().addData("message", message).build();
		   
		 	Result result = sender.send(msg, user.getDeviceRegisterId(),5);
		 
		   
	 }
}
