package com.gottagged380;


public class JoinGame {

		private boolean join;
		private String name;
		private long gemaSessionId;
		
		public String getName(){
			 return name;
		 }
		
		public void setName(String name){
			this.name = name;
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
