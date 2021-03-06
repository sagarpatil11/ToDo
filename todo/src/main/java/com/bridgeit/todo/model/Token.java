package com.bridgeit.todo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Token implements Serializable
{
	@Id
	@GenericGenerator(name="id",strategy="increment")
	@GeneratedValue(generator="id")
	int id;
	
	private String accessToken;
	
	private String refreshToken;
	
	private Date accessTokenCreation;
	
	private Date refreshTokenCreation;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="uid")
	private User user;
	
	public Token()
	{
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Date getAccessTokenCreation() {
		return accessTokenCreation;
	}

	public void setAccessTokenCreation(Date accessTokenCreation) {
		this.accessTokenCreation = accessTokenCreation;
	}

	public Date getRefreshTokenCreation() {
		return refreshTokenCreation;
	}

	public void setRefreshTokenCreation(Date refreshTokenCreation) {
		this.refreshTokenCreation = refreshTokenCreation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Token [accessToken=" + accessToken + ", refreshToken=" + refreshToken + ", accessTokenCreation="
				+ accessTokenCreation + ", refreshTokenCreation=" + refreshTokenCreation + ", user=" + user.getId() + "]";
	}

	  
	
	
}
