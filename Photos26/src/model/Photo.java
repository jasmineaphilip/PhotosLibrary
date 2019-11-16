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
		cal.set(Calendar.MILLISECOND,0);
		photo = new SerializablePhoto();
		caption = "";
		
	}
	
	

}
