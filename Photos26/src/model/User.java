package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * User class which handles the getter, setters, and toString for all User objects. It also has field for each user's list of albums and photos.
 * @author Jasmine Philip
 * @author Radhe Bangad
 *
 */
public class User implements Serializable{
	private static final long serialVersionUID = 2818796451511130119L;
	private String username;
	private ArrayList<Album> albums;
	private ArrayList<Photo> allPhotos;
	 
	/**
	 * Constructor for Users
	 * @param username username which is used to identify a user and allow login
	 */
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
		allPhotos = new ArrayList<Photo>();
		
	}
	
	/**
	 * toString for User, which returns the user's username
	 * @return User's username
	 */
	public String toString() {
		return getUsername();
	}
	
	/**
	 * Equals method for users, to determine of a user object is equal to another, based on usernames
	 * @return true if the user objects have the same usernames
	 */
	public boolean equals(Object user) {
		if(user instanceof User) {
			if(this.username.equals(((User)user).getUsername())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Getter method to get the user's usename
	 * @return username User's username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Setter method to set the user's username
	 * @param username username to set the User's username to
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Getter method to get the user's arraylist of albums
	 * @return user's arraylist of albums
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
	}
	
	/**
	 * Setter method to set the user's arraylist of albums
	 * @param albums user's arraylist of albums
	 */
	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

	/**
	 * Getter method to get an arraylist containing all of the user's photos, across all of their albums
	 * @return arraylist of all the user's photos
	 */
	public ArrayList<Photo> getAllPhotos() {
		return allPhotos;
	}

	/**
	 * Setter method to set an arraylist containing all of the user's photos, across all of their albums
	 * @param allPhotos arraylist of all the user's photos
	 */
	public void setAllPhotos(ArrayList<Photo> allPhotos) {
		this.allPhotos = allPhotos;
	}

}
