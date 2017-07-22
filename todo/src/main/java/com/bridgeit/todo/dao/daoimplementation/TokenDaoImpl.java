package com.bridgeit.todo.dao.daoimplementation;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.todo.dao.daointerface.TokenDao;
import com.bridgeit.todo.model.Token;

@Repository
public class TokenDaoImpl implements TokenDao
{
	@Autowired
	SessionFactory sessionFactory;
	
	public void saveToken(Token token) 
	{
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		
		session.save(token);
	}

	@Override
	public Token checkAccessToken(String accessToken) 
	{
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from Token where accessToken=:accessToken");
		query.setParameter("accessToken", accessToken);
		
		return (Token) query.uniqueResult();
	}

	@Override
	public Token checkRefreshToken(String refreshToken) 
	{
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from Token where accessToken=:accessToken");
		query.setParameter("accessToken", refreshToken);
		
		return (Token) query.uniqueResult();
	}

}
