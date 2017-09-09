package com.bridgeit.todo.dao.daoimplementation;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.todo.dao.daointerface.TaskDao;
import com.bridgeit.todo.model.Collaborator;
import com.bridgeit.todo.model.Task;
import com.bridgeit.todo.model.User;

@Repository
public class TaskDaoImpl implements TaskDao
{
	@Autowired
	SessionFactory sessionFactory;
	
	public int  addNote(Task task) 
	{
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		
		Serializable id=session.save(task);
		
		return (int) id;
	}

	public void updateNote(Task task) 
	{
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		
		session.update(task);
	}

	public void deleteTask(int tid) 
	{
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		
		String hql="delete from Task where tid=:tid";
		
		Query query=session.createQuery(hql);
		query.setParameter("tid", tid);
		
		query.executeUpdate();
	}
	
	public List<Task> getNotes(User user)
	{
		Session session=sessionFactory.getCurrentSession();
		
		Query query=session.createQuery("FROM Task where user=:user");
		query.setParameter("user", user);
		
		List<Task> tasklist= query.list();
		
		return tasklist;
		
	}
	
	
	public Task getNoteById(int tid)
	{
		Session session=sessionFactory.getCurrentSession();
		
		Query query=session.createQuery("FROM Task where tid=:tid");
		query.setParameter("tid", tid);
		
		Task task=(Task) query.uniqueResult();
		
		return task;
	}

	
}