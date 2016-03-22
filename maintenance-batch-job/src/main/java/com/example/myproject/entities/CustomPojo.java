package com.example.myproject.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "POJO")
public class CustomPojo implements Serializable {

	private static final long serialVersionUID = -4191716175718099904L;

	@Id
	@Column(name="id")
	private String id;

	@Column(name="description")
	private String description;

	@Column(name="addedField")
	private String addedField;
	
	public CustomPojo() {

	}
	public CustomPojo(String id, String description) {
		this.id = id;
		this.description = description;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddedField() {
		return addedField;
	}
	public void setAddedField(String addedField) {
		this.addedField = addedField;
	}
	@Override
	public String toString() {
		return "CustomPojo [id=" + id + ", description=" + description + ", addedField=" + addedField + "]";
	}

}
