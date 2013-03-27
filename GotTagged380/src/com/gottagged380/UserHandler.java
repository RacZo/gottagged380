package com.gottagged380;

import java.io.IOException;

import com.gottagged380.userendpoint.model.User;
import com.gottagged380.userendpoint.Userendpoint;
import android.os.AsyncTask;
import android.content.Context;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

public abstract class UserHandler extends AsyncTask<Context, Integer, Long> {

	protected Long doinBackground(Context... contexts){
		 
		Userendpoint.Builder endpointBuilder = new Userendpoint.Builder(
	              AndroidHttp.newCompatibleTransport(),
	              new JacksonFactory(),
	              new HttpRequestInitializer() {
	              public void initialize(HttpRequest httpRequest) { }
	              });
		 
	      Userendpoint endpoint = CloudEndpointUtils.updateBuilder(
	      endpointBuilder).build();
	      try {
	          User nUser = new User();
	          nUser.setName("Armand");
	          nUser.setDeviceRegisterId("312312312313");
	          nUser.setEmail("armand.saro@gmail.com");      
	          User fUser = endpoint.insertUser(nUser).execute();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	          return (long) 0;
	}
}

