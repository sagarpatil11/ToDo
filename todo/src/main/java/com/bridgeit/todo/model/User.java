package com.bridgeit.todo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name="UserInfo")
public class User implements Serializable
{
	@Id
	@GenericGenerator(name = "id", strategy = "increment")
	@GeneratedValue(generator="id")
	@Column(name="u_id")
	private int id;
	
	private String fullname;
	
	@Column(unique=true)
	private String email;
	
	@Column(unique=true)
	private String mobile;
	
	private String password;
	
	private String isActive;
	
	public User() 
	{
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullname=" + fullname + ", email=" + email + ", mobile=" + mobile + ", password="
				+ password + ", isActive=" + isActive + "]";
	}
	
	
}
