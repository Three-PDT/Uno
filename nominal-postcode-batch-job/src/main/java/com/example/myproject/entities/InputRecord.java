package com.example.myproject.entities;

public class InputRecord {
	private String id;
	private String description;
	public InputRecord() {
	}
	public InputRecord(String id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
