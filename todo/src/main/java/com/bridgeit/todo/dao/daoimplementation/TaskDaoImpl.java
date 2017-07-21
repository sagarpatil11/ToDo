package com.bridgeit.todo.dao.daoimplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.todo.dao.daointerface.TaskDao;
import com.bridgeit.todo.model.Task;

@Repository
public class TaskDaoImpl implements TaskDao
{
	@Autowired
	SessionFactory sessionFactory;
	
	public void addNote(Task task) 
	{
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		
		session.save(task);
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
	
	public List<Task> getNotes(int uid)
	{
		Session session=sessionFactory.getCurrentSession();
		
		Query query=session.createQuery("FROM Task where uid=:uid");
		query.setParameter("uid", uid);
		
		List<Task> list=query.list();
		
		return list;
		
	}
	

}