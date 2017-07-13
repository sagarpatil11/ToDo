package com.bridgeit.todo.model;

import java.io.Serializable;

public class Token implements Serializable
{
	private String accessToken;
	private String refreshToken;
	private long expiryTimeAccessToken;
	private long expiryTimeRefreshToken;
	
	public Token()
	{
		
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

	public long getExpiryTimeAccessToken() {
		return expiryTimeAccessToken;
	}

	public void setExpiryTimeAccessToken(long expiryTimeAccessToken) {
		this.expiryTimeAccessToken = expiryTimeAccessToken;
	}

	public long getExpiryTimeRefreshToken() {
		return expiryTimeRefreshToken;
	}

	public void setExpiryTimeRefreshToken(long expiryTimeRefreshToken) {
		this.expiryTimeRefreshToken = expiryTimeRefreshToken;
	}

	@Override
	public String toString() {
		return "Token [accessToken=" + accessToken + ", refreshToken=" + refreshToken + ", expiryTimeAccessToken="
				+ expiryTimeAccessToken + ", expiryTimeRefreshToken=" + expiryTimeRefreshToken + "]";
	}
	
	
}
