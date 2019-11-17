package model;

import java.io.*;
import java.util.*;

public class Album implements Serializable {

	private String name;
	private List<Photo> photos;
	private Photo oldestPhoto;
	private Photo newestPhoto;
	
	
	public Album(String name) {
		name = this.name;
		photos = new ArrayList<Photo>();
		oldestPhoto = null;
		newestPhoto = null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		name = this.name;
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
	
	public void movePhoto(Album destination) {
		
	}
	
	public void copyPhoto() {
		
	}
	
	public Photo getOldestPhoto() {
		oldestPhoto = photos.get(0);
		for (Photo photo : photos) {
			//if (photo.get())
		}
		return oldestPhoto;
	}
	
	public Photo getNewestPhoto() {
		// TODO Auto-generated method stub
		return null;
	}

}
