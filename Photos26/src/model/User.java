package model;

import java.io.Serializable;

public class User implements Serializable{
	private String username;
	private String fullname;
	
	public User(String username, String fullname) {
		this.username = username;
		this.fullname = fullname;
	}

}
