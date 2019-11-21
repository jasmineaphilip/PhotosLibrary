package model;

import java.io.*;
import java.util.*;

import javafx.scene.image.Image;

/**
 * 
 * @author Jasmine Philip
 * @author Radhe Bangad
 * Provides getters, setters, and toString for album objects
 */
public class Album implements Serializable {
	private static final long serialVersionUID = -6258723435304989168L;
	private String name;
	private List<Photo> photos;
	private Photo oldestPhoto;
	private Photo newestPhoto;
	
	/**
	 * Constructor for album 
	 * @param name name of the album
	 */
	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
		oldestPhoto = null;
		newestPhoto = null;
	}
	
	/**
	 * Getter for album name
	 * @return album name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for album name
	 * @param name album name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * toString method for album name
	 */
	public String toString() {
		return getName();
	}
	
	
	/**
	 * Getter for photo given album arraylist index
	 * @param index index of photo in arraylist
	 * @return photo at the specified index
	 */
	public Photo getPhoto(int index) {
		return photos.get(index);
	}
	
	/**
	 * Getter for arraylist of photos in specific album
	 * @return arraylist of photos
	 */
	public List<Photo> getPhotos() {
		return photos;
	}
	
	/**
	 * Getter for photo index given photo
	 * @param photo photo object
	 * @return index of arraylist photo is located
	 */
	public int getPhotoIndex(Photo photo) {
		for (int i = 0; i < photos.size(); i++) {
			if (photos.get(i).equals(photo)) {
				return i;
			}
		} return -1;
	}
	
	/**
	 * Adds a photo to album it's called on
	 * @param photo photo to be added to album
	 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
		oldestPhoto = getOldestPhoto();
		newestPhoto = getNewestPhoto();
	}
	
	/**
	 * Removes a photo from album it's called on
	 * @param photo photo to be removed from album
	 */
	public void removePhoto(Photo photo) {
		photos.remove(photo);
		oldestPhoto = getOldestPhoto();
		newestPhoto = getNewestPhoto();
	}

	/**
	 * Moves photo from current album to specified destination
	 * @param photo photo to be moved to other album
	 * @param destination album photo will be moved to
	 */
	public void movePhoto(Photo photo, Album destination) {
		photos.remove(photo);
		destination.photos.add(photo);
	}
	
	/**
	 * Copies photo to specified album
	 * @param photo photo to be copied to another album
	 * @param destination album photo will be copied to
	 */
	public void copyPhoto(Photo photo, Album destination) {
		destination.photos.add(photo);
	}
	
	/**
	 * Getter method to get the oldest photo in an album, by calendar date
	 * @return oldest photo in the album
	 */
	public Photo getOldestPhoto() {
		oldestPhoto = photos.get(0);
		for (Photo photo : photos) {
			if (photo.getCalendar().compareTo(oldestPhoto.getCalendar()) < 0) {
				oldestPhoto = photo;
			}
		}
		return oldestPhoto;
	}
	
	/**
	 * Getter method to get the date of the oldest photo in the album
	 * @return date of the oldest photo in the album
	 */
	public String getOldestPhotoDate() {
		if (oldestPhoto == null) return "N/A";
		return oldestPhoto.getDate();
	}
	
	/**
	 * Getter method to get the newest photo added to the album
	 * @return newest photo added to the album
	 */
	public Photo getNewestPhoto() {
		newestPhoto = photos.get(0);
		for (Photo photo : photos) {
			if (photo.getCalendar().compareTo(oldestPhoto.getCalendar()) > 0) {
				newestPhoto = photo;
			}
		}
		return newestPhoto;
	}
	
	/**
	 * Setter method to set the oldest photo in the album to a specified photo
	 * @param oldestPhoto photo to set as the oldest in the album
	 */
	public void setOldestPhoto(Photo oldestPhoto) {
		this.oldestPhoto = oldestPhoto;
	}
	
	/**
	 * Setter method to set the newest photo in the album to a specified photo
	 * @param newestPhoto photo to set as the newest in the album
	 */
	public void setNewestPhoto(Photo newestPhoto) {
		this.newestPhoto = newestPhoto;
	}
	
	/**
	 * Getter method to get the date of the newest photo added to the album
	 * @return date of the newest photo added to the album
	 */
	public String getNewestPhotoDate() {
		if (newestPhoto == null) return "N/A";
		return newestPhoto.getDate();
	}
	
	/**
	 * Getter method to get the range of dates of photos in the album
	 * @return date range as a dash separated string
	 */
	public String getDateRange() {
		return getOldestPhotoDate() + " - " + getNewestPhotoDate();
	}
}
