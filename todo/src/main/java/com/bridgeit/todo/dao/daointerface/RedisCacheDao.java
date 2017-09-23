package com.bridgeit.todo.dao.daointerface;

import com.bridgeit.todo.model.Token;

public interface RedisCacheDao 
{
	public void saveinRedis(Token token);
	
	public Object getFromRedis(String id);
	
	public void deleteFromRedis(String id);
}
