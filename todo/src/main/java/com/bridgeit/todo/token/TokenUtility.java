package com.bridgeit.todo.token;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.service.TokenService;

public class TokenUtility 
{
	@Autowired
	TokenService tokenService;
	
	public Token tokenGenerator()
	{
		
		
		Token token=new Token();
		
		token.setAccessToken(UUID.randomUUID().toString().replaceAll("-", ""));
		token.setAccessTokenCreation(new Date());
		
		token.setRefreshToken(UUID.randomUUID().toString().replaceAll("-", ""));
		token.setRefreshTokenCreation(new Date());
		
		return token;
	}
	
	public Boolean validateAccessToken(String accessToken)
	{
		System.out.println("in validate");
		
		Token token=tokenService.checkAccessToken(accessToken);
		
		if(token != null)
		{
			long difference = new Date().getTime() - token.getAccessTokenCreation().getTime();
			long differenceinseconds = TimeUnit.MILLISECONDS.toSeconds(difference);
			
			if(differenceinseconds > 60)
			{
				return false;
			}
			else
			{
				return true;
			}
 		}
		
		
		return false;
	}
	
	public Boolean validateRefreshToken(String refreshToken)
	{
		System.out.println("in validate");
		
		Token token=tokenService.checkRefreshToken(refreshToken);
		
		if(token != null)
		{
			long difference = new Date().getTime() - token.getRefreshTokenCreation().getTime();
			
			long differenceinseconds = TimeUnit.MILLISECONDS.toSeconds(difference);
			
			if(differenceinseconds > 60)
			{
				return false;
			}
			else
			{
				
			}
 		}
		
		
		return false;
	}
}
