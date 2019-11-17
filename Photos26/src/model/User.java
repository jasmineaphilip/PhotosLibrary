package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	private String username;
	private ArrayList<Album> albums;
	
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
	
	public ArrayList<Album> getAlbums() {
		return albums;
	}

}
