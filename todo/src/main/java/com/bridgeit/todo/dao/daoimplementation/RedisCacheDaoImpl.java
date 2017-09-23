package com.bridgeit.todo.dao.daoimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bridgeit.todo.dao.daointerface.RedisCacheDao;
import com.bridgeit.todo.model.Token;

@Repository
public class RedisCacheDaoImpl implements RedisCacheDao
{
	
	@Autowired
	private RedisTemplate<String, Token> redisTemplate;
	
	private static final String key="redisKey";
	/*
	@Autowired
	public RedisCacheDaoImpl(RedisTemplate<String, Token> redisTemplate)
	{
	
	        this.redisTemplate = redisTemplate;
	}*/


	@Override
	public void saveinRedis(Token token) {
		// TODO Auto-generated method stub
		System.out.println("in saveinredis::"+token);
		redisTemplate.opsForHash().put(key, token.getAccessToken(), token);
		
	}

	@Override
	public Token getFromRedis(String token) {
		// TODO Auto-generated method stub
		return (Token) redisTemplate.opsForHash().get(key, token);
	}

	@Override
	public void deleteFromRedis(String id) {
		// TODO Auto-generated method stub
		
	}

}
