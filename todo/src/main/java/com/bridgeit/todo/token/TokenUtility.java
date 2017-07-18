package com.bridgeit.todo.token;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.bridgeit.todo.model.Token;

public class TokenUtility 
{
	
	public Token tokenGenerator()
	{
		
		
		Token token=new Token();
		
		token.setAccessToken(UUID.randomUUID().toString().replaceAll("-", ""));
		token.setAccessTokenCreation(new Date());
		
		token.setRefreshToken(UUID.randomUUID().toString().replaceAll("-", ""));
		token.setRefreshTokenCreation(new Date());
		
		return token;
	}
	
	public Boolean validateToken(String accesstoken,long creationtime)
	{
		long currenttime=new Date().getTime();
		
		long diffrence=creationtime - currenttime;
		
		long diffrenceinseconds=TimeUnit.MILLISECONDS.toSeconds(diffrence);
		
		if(diffrenceinseconds > 30)
		{
			return false; 
		}
		
		return true;
	}
}
