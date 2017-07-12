package com.bridgeit.todo.token;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class TokenGenerator 
{
	
	//....................Generate Access Tokon....................//
	
	public String generateAccessToken()
	{
		return UUID.randomUUID().toString().replaceAll("-", "");
		
	}
	
	
	//....................Generate Refresh Tokon....................//
	
	public String generateRefreshToken()
	{
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	//....................Generate Expiry Time for Access Token....................//
	
	public long accessTokenExpiryTime()
	{
		long currenttime=System.currentTimeMillis()/1000;
	
		return (currenttime+1800);
	}
	
	
	//....................Generate Expiry Time for Refresh Tokon....................//
	
	public long refreshTokenExpiryTime()
	{
		long currenttime=System.currentTimeMillis()/1000;
		
		return (currenttime+3600);
	}
}
