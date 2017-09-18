package com.bridgeit.todo.dao.daoimplementation;

import java.util.List;

import org.hibernate.Query;
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

	@Override
	public List getColList(int uid) {
		// TODO Auto-generated method stub
		Session session= sessionFactory.getCurrentSession();
		
		Query query=session.createQuery("Select task from Collaborator where noteSharedWith=:id");
		query.setParameter("id", uid);
		
		List colList= query.list();
		
		return colList;
	}
	
}
