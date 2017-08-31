package com.bridgeit.todo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Task implements Serializable 
{
	@Id
	@GenericGenerator(name="tid",strategy="increment")
	@GeneratedValue(generator="tid")
	private int tid;
	
	private String title;
	
	private String description;
	
	private Date creation_date;
	
	private Date edited_date;
	
	private String color;
	
	private Date reminder;
	
	private String isTrash;
	
	private String isArchive;
	
	private String isPinned;
	
	@Transient
	List<WebScraper> webscraper;
	
	@Lob
	@Column(columnDefinition="mediumblob")
	private String image;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="uid")
	private User user;
	
	public Task() 
	{
		
	}


	public int getTid() {
		return tid;
	}


	public void setTid(int tid) {
		this.tid = tid;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Date getCreation_date() {
		return creation_date;
	}


	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	

	public Date getEdited_date() {
		return edited_date;
	}


	public void setEdited_date(Date edited_date) {
		this.edited_date = edited_date;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}

	
	public Date getReminder() {
		return reminder;
	}


	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}

	
	public String getIsTrash() {
		return isTrash;
	}


	public void setIsTrash(String isTrash) {
		this.isTrash = isTrash;
	}


	public String getIsArchive() {
		return isArchive;
	}


	public void setIsArchive(String isArchive) {
		this.isArchive = isArchive;
	}


	public String getIsPinned() {
		return isPinned;
	}


	public void setIsPinned(String isPinned) {
		this.isPinned = isPinned;
	}

	
	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}

	
	
	public List<WebScraper> getWebscraper() {
		return webscraper;
	}


	public void setWebscraper(List<WebScraper> webscraper) {
		this.webscraper = webscraper;
	}


	@Override
	public String toString() {
		return "Task [tid=" + tid + ", title=" + title + ", description=" + description + ", creation_date="
				+ creation_date + "]";
	}

	
}
