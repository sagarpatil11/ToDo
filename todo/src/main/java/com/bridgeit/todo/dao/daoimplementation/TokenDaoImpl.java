package com.bridgeit.todo.dao.daoimplementation;

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

}
