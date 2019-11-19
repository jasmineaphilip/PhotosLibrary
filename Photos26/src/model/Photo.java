package model;

import java.io.*;
import java.util.*;
import java.util.Calendar;

import javafx.scene.image.Image;

public class Photo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8379223698967757L;
	private SerializablePhoto photo;
	private String caption;
	private List<Tag> tags;
	private Calendar cal;

	public Photo() {
		cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND,0);
		photo = new SerializablePhoto();
		caption = "";
		tags = new ArrayList<Tag>();
		
	}
	
	public Calendar getCalendar() {
		return cal;
	}
	
	public String getDate() {
		String [] str = cal.getTime().toString().split("\\s+");
		return str[0] + " " + str[1] + " " + str[2] + ", " + str[5];
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void editCaption(String newCaption) {
		caption = newCaption;
	}

	public Image getImage() {
		return photo.getPhoto();
	}
	
	public SerializablePhoto getSerializableImage() {
		return photo;
	}
	
	public String getTagName() {
		return null;
	}
	
	public String getTagValue() {
		return null;
	}
	
	public void addTag() {
		
	}
	
	public void editTag() {
		
	}
	public void removeTag() {
		
	}
	public List<Tag> getTags() {
		return tags;
	}
}
