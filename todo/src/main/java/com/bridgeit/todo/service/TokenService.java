package com.bridgeit.todo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.token.TokenGenerator;

public class TokenService 
{	
	
	Map<Integer, Token> tokenmap=new HashMap<Integer, Token>();
	
	
	//.........................Generate Access and Refresh Token..................//
	
	public Map<Integer, Token> tokenGenerator(Integer uid)
	{
		TokenGenerator tokenGenerator=new TokenGenerator();
		
		Token token=new Token();
		
		token.setAccessToken(tokenGenerator.generateAccessToken());
		token.setExpiryTimeAccessToken(tokenGenerator.accessTokenExpiryTime());
		
		token.setRefreshToken(tokenGenerator.generateRefreshToken());
		token.setExpiryTimeRefreshToken(tokenGenerator.refreshTokenExpiryTime());
		
		tokenmap.put(uid, token);
		
		return tokenmap;
	}
	
	
	public void validateTokens(int uid)
	{
		Token token=tokenmap.get(uid);
		
		TokenGenerator tokenGenerator=new TokenGenerator();
		
		if(token.getExpiryTimeAccessToken() > System.currentTimeMillis()/1000)
		{
			
		}
		
		if(token.getExpiryTimeRefreshToken() > System.currentTimeMillis()/1000)
		{
			token.setAccessToken(tokenGenerator.generateAccessToken());
			token.setExpiryTimeAccessToken(tokenGenerator.accessTokenExpiryTime());
			tokenmap.put(uid, token);
		}
		
	}
	
}
