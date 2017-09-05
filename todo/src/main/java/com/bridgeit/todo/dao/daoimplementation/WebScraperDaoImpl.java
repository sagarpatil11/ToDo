package com.bridgeit.todo.dao.daoimplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.todo.dao.daointerface.WebScraperDao;
import com.bridgeit.todo.model.WebScraper;

@Repository
public class WebScraperDaoImpl implements WebScraperDao
{
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void saveWebScraper(WebScraper webScraper) 
	{
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		
		session.save(webScraper);
		
	}

	@Override
	public List<WebScraper> getWebScraper(int tid) {
		// TODO Auto-generated method stub
		
		Session session=sessionFactory.getCurrentSession();
		
		Query query=session.createQuery("FROM WebScraper where tid=:tid");
		query.setParameter("tid", tid);
		List<WebScraper> list= query.list();
		
		return list;
	}
	
	public void deleteScraper(int id) {
		// TODO Auto-generated method stub
		
		Session session=sessionFactory.getCurrentSession();
		
		String hql="delete from WebScraper where id=:id";
		
		Query query=session.createQuery(hql);
		query.setParameter("id", id);
		
		query.executeUpdate();
		
	}

}
