package com.bridgeit.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.todo.dao.daointerface.TokenDao;
import com.bridgeit.todo.model.Token;

@Service
public class TokenService 
{	
	@Autowired
	TokenDao tokenDao;
	
	@Transactional
	public void saveToken(Token token)
	{
		tokenDao.saveToken(token);
	}
	
	@Transactional(readOnly = true)
	public Token checkAccessToken(String accessToken)
	{
		return tokenDao.checkAccessToken(accessToken);
		
	}
	
	@Transactional(readOnly=true)
	public Token checkRefreshToken(String refreshToken)
	{
		return tokenDao.checkRefreshToken(refreshToken);
	}
	
	@Transactional
	public void updateToken(Token token)
	{
		tokenDao.updateToken(token);
	}
	
	@Transactional
	public void deleteToken(String refreshToken)
	{
		tokenDao.deleteToken(refreshToken);
	}
	
}
