package com.bridgeit.todo.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	/*
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="uid")
	private User user;*/
	
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


	/*public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
*/
	
}
