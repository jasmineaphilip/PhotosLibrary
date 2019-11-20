package model;

import java.io.Serializable;

public class Tag implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4817523966395760834L;
	private String name;
	private String value;
	
	public Tag (String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String toString() {
		return name + ": " + value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;;
	}
	
}
