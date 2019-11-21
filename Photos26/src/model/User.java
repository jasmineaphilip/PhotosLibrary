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
	//private Album allPhotos;
	 
	
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
		allPhotos = new ArrayList<Photo>();
		
	}
	
	public String toString() {
		return getUsername();
	}
	public boolean equals(Object user) {
		if(user instanceof User) {
			if(this.username.equals(((User)user).getUsername())) {
				return true;
			}
		}
		return false;
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
	
	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

	public ArrayList<Photo> getAllPhotos() {
		return allPhotos;
	}

	public void setAllPhotos(ArrayList<Photo> allPhotos) {
		this.allPhotos = allPhotos;
	}

}
