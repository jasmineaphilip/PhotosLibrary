package model;

import java.io.*;
import java.util.*;

import javafx.scene.image.Image;

public class Album implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6258723435304989168L;
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
	
	public Image getAlbumThumnail() {
		if (photos.isEmpty()) return null;
		return photos.get(0).getImage();
	}
	
	public Photo getPhoto(int index) {
		return photos.get(index);
	}
	
	public List<Photo> getPhotos() {
		return photos;
	}
	
	public int getPhotoIndex(Photo photo) {
		for (int i = 0; i < photos.size(); i++) {
			if (photos.get(i).equals(photo)) {
				return i;
			}
		} return -1;
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
//	
//	public void displayPhoto() {
//		
//	}
	
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
		if (oldestPhoto == null) return "N/A";
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
	
	public void setOldestPhoto(Photo oldestPhoto) {
		this.oldestPhoto = oldestPhoto;
	}
	public void setNewestPhoto(Photo newestPhoto) {
		this.newestPhoto = newestPhoto;
	}
	
	public String getNewestPhotoDate() {
		if (newestPhoto == null) return "N/A";
		return newestPhoto.getDate();
	}
	
	public String getDateRange() {
		return getOldestPhotoDate() + " - " + getNewestPhotoDate();
	}
}
