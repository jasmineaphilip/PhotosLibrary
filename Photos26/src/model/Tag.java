package model;

import java.io.Serializable;

/**
 * Tag class which handles the getter, setters, and toString for all tag objects
 * @author Jasmine Philip
 * @author Radhe Bangad
 *
 */
public class Tag implements Serializable{
	
	private static final long serialVersionUID = -4817523966395760834L;
	private String name;
	private String value;
	
	/**
	 * Tag constructor, using the tag's name and value
	 * @param name Tag's name
	 * @param value Tag's value
	 */
	public Tag (String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * toString method used to display the photo's tag name/value pairs in the NonAdminAlbum listview
	 */
	public String toString() {
		return name + ": " + value;
	}
	
	/**
	 * Getter method for the Tag's name
	 * @return Tag's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter method for the Tag's name
	 * @param name name to set the Tag's name field to
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter method for the Tag's value
	 * @return Tag's value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Setter method for the Tag's value
	 * @param name name to set the Tag's value field to
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
