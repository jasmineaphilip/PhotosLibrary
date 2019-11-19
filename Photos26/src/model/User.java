package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2818796451511130119L;
	private String username;
	private ArrayList<Album> albums;
	private ArrayList<Photo> allPhotos;
	 
	
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
		
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
