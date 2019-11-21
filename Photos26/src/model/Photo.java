package model;

import java.io.*;
import java.util.*;
import java.util.Calendar;

import javafx.scene.image.Image;

/**
 * Photo class handles the constructors, getters and setters for Photo objects
 * @author Jasmine Philip
 * @author Radhe Bangad
 *
 */
public class Photo implements Serializable {
	private static final long serialVersionUID = 8379223698967757L;
	private String caption;
	private List<Tag> tags;
	private Calendar cal;
	private String path;

	/**
	 * Constructor for photo objects
	 */
	public Photo() {
		cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND,0);
		caption = "";
		tags = new ArrayList<Tag>();
		setPath("");
	}
	
	/**
	 * Getter method to get a calendar object for a photo
	 * @return calendar object
	 */
	public Calendar getCalendar() {
		return cal;
	}
	
	/**
	 * Getter method to get the date photo was added to album based on Calendar time
	 * @return date of photo as a string
	 */
	public String getDate() {
		String [] str = cal.getTime().toString().split("\\s+");
		return str[0] + " " + str[1] + " " + str[2] + ", " + str[5];
	}
	
	/**
	 * Getter method to get the year the photo was added to the album using Calendar
	 * @return year photo was added
	 */
	public int getYear() {
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * Getter method to get the day the photo was added to the album using Calendar
	 * @return day photo was added
	 */
	public int getDay() {
		return cal.get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * Getter method to get the caption of the photo
	 * @return caption of the photo
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * Setter method to set the caption of the photo
	 * @param caption String to set the photo's caption to 
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * Adds a tag to the photo
	 * @param name tag's name
	 * @param value tag's value (or type)
	 */
	public void addTag(String name, String value) {
		tags.add(new Tag(name, value));
	}
	
	/**
	 * Sets the name and value of the tag to new Strings
	 * @param t Tag object to be edited
	 * @param newName Tag's new name
	 * @param newValue Tag's new value
	 */
	public void editTag(Tag t, String newName, String newValue) {
		t.setName(newName);
		t.setValue(newValue);
	}

	/**
	 * Removes the tag at a specified index of the photo object's Tag arraylist
	 * @param index Tag arraylist index to get tag object to remove
	 */
	public void removeTag(int index) {
		tags.remove(index);
	}
	
	/**
	 * Getter method to get the arraylist of Tags of a photo
	 * @return arraylist of tags for a certain photo
	 */
	public List<Tag> getTags() {
		return tags;
	}

	/**
	 * Getter method to the get the path of a photo, when added from a user's directory
	 * @return file path of the added photo
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Setter method to set the path of a photo, when added from a user's directory
	 * @param path path of the added photo
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
