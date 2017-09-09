package com.bridgeit.todo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleProfile 
{
	private String id;
	private String displayName;
	private List<Gmail> emails;
	//private ProfileImage image;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return displayName;
	}
	
	public void setName(String name) {
		this.displayName = name;
	}
	
	public List<Gmail> getEmail() {
		return emails;
	}
	
	public void setEmail(List<Gmail> email) {
		this.emails = email;
	}
	
	/*public ProfileImage getImage() {
		return image;
	}
	
	public void setImage(ProfileImage image) {
		this.image = image;
	}
	*/
	
	
	
}
