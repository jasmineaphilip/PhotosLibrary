package model;

import java.io.*;
import java.util.*;
import java.util.Calendar;

import javafx.scene.image.Image;

public class Photo implements Serializable {
	
	private SerializablePhoto photo;
	private String caption;
	private List<Tag> tags;
	private Calendar cal;

	public Photo() {
		cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND,0);
		photo = new SerializablePhoto();
		caption = "";
		
	}
	
	public Calendar getCalendar() {
		return cal;
	}
	
	public String getDate() {
		String [] str = cal.getTime().toString().split("\\s+");
		return str[0] + " " + str[1] + " " + str[2] + ", " + str[5];
	}
	
	public void editCaption(String newCaption) {
		caption = newCaption;
	}

	

	
	
	
	
	

}
