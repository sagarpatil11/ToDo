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
	
	public Map<Integer, Token> tokenGenerator(int uid)
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
	
	public Map<Integer, Token> getAccessToken(int uid, Token token)
	{
		TokenGenerator	tokenGenerator=new TokenGenerator();
		
		token.setAccessToken(tokenGenerator.generateAccessToken());
		token.setExpiryTimeAccessToken(tokenGenerator.accessTokenExpiryTime());
		
		tokenmap.put(uid, token);
		
		return tokenmap;
	}
	
	
}
