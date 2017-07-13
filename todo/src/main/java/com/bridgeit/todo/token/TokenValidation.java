package com.bridgeit.todo.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.service.TokenService;

public class TokenValidation 
{
	@Autowired
	TokenService tokenService;
	
	Token token;
	Map<Integer, Token> tokenmap=new HashMap<Integer, Token>();
	public Map<Integer, Token> validateTokens(int uid)
	{
		
		token=tokenmap.get(uid);
		
		if(token.getExpiryTimeAccessToken() > System.currentTimeMillis()/1000)
		{
			
		}
		
		if(token.getExpiryTimeRefreshToken() > System.currentTimeMillis()/1000)
		{
			tokenmap=tokenService.getAccessToken(uid, token);
		}
		
		tokenService.tokenGenerator(uid);
		return null;
		
	}
}
