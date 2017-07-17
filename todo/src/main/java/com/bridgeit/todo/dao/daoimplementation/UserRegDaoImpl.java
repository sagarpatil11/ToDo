package com.bridgeit.todo.dao.daoimplementation;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.todo.dao.daointerface.UserRegDao;
import com.bridgeit.todo.model.User;

@Repository
public class UserRegDaoImpl implements UserRegDao
{
	@Autowired
	SessionFactory sessionFactory;
	
	public void userRegDao(User user) 
	{
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		
		session.save(user);
	}
	
	
	public User userLogin(String email,String password) 
	{
		// TODO Auto-generated method stub
		
		Session session=sessionFactory.getCurrentSession();
		
		/*String hql="from User where email=:email and password=:password";
		
		Query query=session.createQuery(hql);
		
		query.setParameter("email", email);
		query.setParameter("password", password);
		
		User user=(User) query.uniqueResult();*/
		
		Criteria criteria=session.createCriteria(User.class);
		
		Criterion emailid=Restrictions.eq("email", email);
		Criterion pwd=Restrictions.eq("password", password);
		
		LogicalExpression andexp=Restrictions.and(emailid, pwd);
		
		criteria.add(andexp);
		
		User user=(User) criteria.uniqueResult();
		
		return user;
	}


	public void userUpdateDao(User user) 
	{
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		
		session.update(user);
	}


	public User getUserById(int uid) 
	{
		// TODO Auto-generated method stub
		
		return null;
	}
	
	public void logout(HttpSession session)
	{
		
		session.invalidate();
	}


	public void deleteToken(User user) 
	{
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		
		Query query=session.createQuery("delete from Token where uid=:uid");
		query.setParameter("uid", user.getId());
		query.executeUpdate();
	}
	
}
