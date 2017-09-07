package com.bridgeit.todo.dao.daoimplementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.todo.dao.daointerface.CollaboratorDao;
import com.bridgeit.todo.model.Collaborator;

@Repository
public class CollaboratorDaoImpl implements CollaboratorDao 
{
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void addCollaborator(Collaborator collaborator) 
	{
		// TODO Auto-generated method stub
		Session session= sessionFactory.getCurrentSession();
		
		session.save(collaborator);
		
	}
	
}
