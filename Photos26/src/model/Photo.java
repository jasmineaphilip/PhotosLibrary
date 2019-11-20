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
	private String path;

	public Photo() {
		cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND,0);
		photo = new SerializablePhoto();
		caption = "";
		tags = new ArrayList<Tag>();
		setPath("");
	}
	
	public Calendar getCalendar() {
		return cal;
	}
	
	public String getDate() {
		String [] str = cal.getTime().toString().split("\\s+");
		return str[0] + " " + str[1] + " " + str[2] + ", " + str[5];
	}
	
	public int getYear() {
		return cal.get(Calendar.YEAR);
	}
	public int getDay() {
		return cal.get(Calendar.DAY_OF_YEAR);
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
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
	
	public void addTag(String name, String value) {
		tags.add(new Tag(name, value));
	}
	
	public void editTag(int index, String name, String value) {
		tags.get(index).setName(name);
		tags.get(index).setValue(value);
	}
	
	public void removeTag(int index) {
		tags.remove(index);
	}
	
	public List<Tag> getTags() {
		return tags;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
