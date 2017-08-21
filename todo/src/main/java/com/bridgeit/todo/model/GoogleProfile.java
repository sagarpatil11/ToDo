package com.bridgeit.todo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleProfile 
{
	private String id;
	private String name;
	private List<Gmail> email;
	private ProfileImage image;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Gmail> getEmail() {
		return email;
	}
	
	public void setEmail(List<Gmail> email) {
		this.email = email;
	}
	
	public ProfileImage getImage() {
		return image;
	}
	
	public void setImage(ProfileImage image) {
		this.image = image;
	}
	
	
	
	
}
