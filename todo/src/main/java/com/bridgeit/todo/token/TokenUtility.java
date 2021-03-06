package com.bridgeit.todo.token;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.responsemsg.TokenResponse;
import com.bridgeit.todo.service.TokenService;

public class TokenUtility 
{
	@Autowired
	TokenService tokenService;
	
	@Autowired
	TokenResponse tokenResponse;
	
	public Token tokenGenerator()
	{
		
		
		Token token=new Token();
		
		token.setAccessToken(UUID.randomUUID().toString().replaceAll("-", ""));
		token.setAccessTokenCreation(new Date());
		
		token.setRefreshToken(UUID.randomUUID().toString().replaceAll("-", ""));
		token.setRefreshTokenCreation(new Date());
		
		return token;
	}
	
	//..................validate access token.......................//
	
	public TokenResponse validateAccessToken(String accessToken)
	{
		System.out.println("in validateAccessToken() "+accessToken);
		
		if(accessToken == null)
		{
			tokenResponse.setStatus(-3);
			tokenResponse.setMessage("Access Token is null");
			tokenResponse.setToken(null);
			
			return tokenResponse;
		}
		
		Token token=tokenService.checkAccessToken(accessToken);
		
		if(token != null)
		{
			System.out.println(token.toString());
			
			long difference = new Date().getTime() - token.getAccessTokenCreation().getTime();
			long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(difference);
			
			if(differenceInMinutes > 60)
			{
				System.out.println("access token expired");
				
				tokenResponse.setStatus(-4);
				tokenResponse.setMessage("Access Token is Expired");
				return tokenResponse;
			}
			else
			{
				System.out.println("access token valid");
				
				tokenResponse.setStatus(4);
				tokenResponse.setMessage("Access Token is valid");
				
				return tokenResponse;
				
			}
 		}
		
		tokenResponse.setStatus(-3);
		tokenResponse.setMessage("Access token sent is Wrong");
		return tokenResponse;
	}

	
	
	
	//..................validate refresh token.......................//
	
	
	public TokenResponse validateRefreshToken(String refreshToken)
	{
		System.out.println(refreshToken);
		
		if(refreshToken == null)
		{
			tokenResponse.setStatus(-3);
			tokenResponse.setMessage("Refresh Token is null");
			tokenResponse.setToken(null);
			
			return tokenResponse;
		}
		
		Token token=tokenService.checkRefreshToken(refreshToken);
		
		if(token != null)
		{
			System.out.println(token.toString());
			
			long difference = new Date().getTime() - token.getRefreshTokenCreation().getTime();
			
			long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(difference);
			
			if(differenceInMinutes > 1440)
			{
				System.out.println("Refresh token expired");
				
				tokenResponse.setStatus(-4);
				tokenResponse.setMessage("Refresh Token is Expired");
				
				return tokenResponse;
			}
			else
			{
				System.out.println("refresh token valid");
				
				Token newToken=tokenGenerator();
				System.out.println(token.getUser());
				
				newToken.setUser(token.getUser());
				newToken.setId(token.getId());
				
				try
				{
					tokenService.updateToken(newToken);
					
					System.out.println(newToken);
					newToken.setUser(null);
					
					
					
					tokenResponse.setStatus(4);
					tokenResponse.setMessage("Refresh Token is valid.New Access Token generated.");
					tokenResponse.setToken(newToken);
					
					return tokenResponse;
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					tokenResponse.setStatus(-3);
					tokenResponse.setMessage("Refresh token sent is Wrong");
					tokenResponse.setToken(null);
					
					return tokenResponse;
				}
				
			}
 		}
		
		tokenResponse.setStatus(-3);
		tokenResponse.setMessage("Refresh token sent is Wrong");
		tokenResponse.setToken(null);
		
		return tokenResponse;
	}
}
