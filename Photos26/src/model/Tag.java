package model;

public class Tag {
	
	private String name;
	private String value;
	
	public Tag (String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String toString() {
		return name + ":" + value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		name = this.name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		value = this.value;
	}
	
}
