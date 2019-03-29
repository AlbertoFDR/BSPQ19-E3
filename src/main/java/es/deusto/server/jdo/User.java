package es.deusto.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class User {
	@PrimaryKey
	public String username=null;
	public String password=null;
	
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		
	}
	

	public String getLogin() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	 
}

