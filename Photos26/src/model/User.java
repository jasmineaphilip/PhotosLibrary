package model;

import java.io.Serializable;

public class User implements Serializable{
	private String username;
	
	public User(String username) {
		this.setUsername(username);
	}
	
	public String toString() {
		return getUsername();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
