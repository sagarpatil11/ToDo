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
public class WebScraper implements Serializable
{
	@Id
	@GenericGenerator(name="id",strategy="increment")
	@GeneratedValue(generator="id")
	private int id;
	
	private String title;
	
	private String imgurl;
	
	private String hostname;
	
	private String hosturl;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="tid")
	private Task task;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getHosturl() {
		return hosturl;
	}

	public void setHosturl(String hosturl) {
		this.hosturl = hosturl;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "WebScraper [id=" + id + ", title=" + title + ", imgurl=" + imgurl + ", hostname=" + hostname
				+ ", hosturl=" + hosturl + ", task=" + task + "]";
	}
	
	
}
