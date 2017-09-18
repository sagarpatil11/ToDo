package com.bridgeit.todo.dao.daointerface;

import com.bridgeit.todo.model.Token;

public interface TokenDao 
{
	public void saveToken(Token token);
	
	public Token checkAccessToken(String accessToken);
	
	public Token checkRefreshToken(String refreshToken);
	
	public void updateToken(Token token);
	
	public void deleteToken(String refreshToken);
}
