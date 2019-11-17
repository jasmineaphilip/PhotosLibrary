package model;

import java.io.*;
import java.util.*;

public class Album implements Serializable {

	private String name;
	private List<Photo> photos;
	private Photo oldestPhoto;
	private Photo newestPhoto;
	
	
	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
		oldestPhoto = null;
		newestPhoto = null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return getName();
	}
	
	public void addPhoto(Photo photo) {
		photos.add(photo);
		oldestPhoto = getOldestPhoto();
		newestPhoto = getNewestPhoto();
	}

	public void removePhoto(Photo photo) {
		photos.remove(photo);
		oldestPhoto = getOldestPhoto();
		newestPhoto = getNewestPhoto();
	}
	
	public void displayPhoto() {
		
	}
	
	public void movePhoto(Photo photo, Album destination) {
		photos.remove(photo);
		destination.photos.add(photo);
	}
	
	public void copyPhoto(Photo photo, Album destination) {
		destination.photos.add(photo);
	}
	
	public Photo getOldestPhoto() {
		oldestPhoto = photos.get(0);
		for (Photo photo : photos) {
			if (photo.getCalendar().compareTo(oldestPhoto.getCalendar()) < 0) {
				oldestPhoto = photo;
			}
		}
		return oldestPhoto;
	}
	
	public String getOldestPhotoDate() {
		if (oldestPhoto == null) return "NA";
		return oldestPhoto.getDate();
	}
	
	public Photo getNewestPhoto() {
		newestPhoto = photos.get(0);
		for (Photo photo : photos) {
			if (photo.getCalendar().compareTo(oldestPhoto.getCalendar()) > 0) {
				newestPhoto = photo;
			}
		}
		return newestPhoto;
	}

}
