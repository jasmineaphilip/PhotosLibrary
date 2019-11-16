package model;

import java.io.Serializable;

public class User implements Serializable{
	private String fullname;
	private String username;
	
	public User(String fullname, String username) {
		this.setFullname(fullname);
		this.setUsername(username);
	}
	
	public String toString() {
		return getFullname() + " - " + getUsername();
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
