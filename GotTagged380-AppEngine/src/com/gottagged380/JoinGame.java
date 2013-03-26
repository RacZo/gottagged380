package com.gottagged380;


public class JoinGame {

		private boolean join;
		private String email;
		private long gemaSessionId;
		
		public String getEmail(){
			 return email;
		 }
		
		public void setEmail(String email){
			this.email = email;
		}
		
		public void setJoin(boolean join){
			this.join = join;
		}
		
		public boolean getJoin(){
			return join;
		}
		
		public void setId(long id){
			this.gemaSessionId = id;
		}
		
		
		public long getId(){
			return gemaSessionId;
		}
}
