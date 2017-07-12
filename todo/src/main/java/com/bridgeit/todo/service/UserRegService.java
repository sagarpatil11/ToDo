package com.bridgeit.todo.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.todo.dao.daointerface.UserRegDao;
import com.bridgeit.todo.model.User;

@Service
public class UserRegService 
{
	@Autowired
	UserRegDao userRegDao;
	
	
	@Transactional
	public void userRegService(User user)
	{
		userRegDao.userRegDao(user);
	}
	
	@Transactional
	public void userUpdateService(User user)
	{
		userRegDao.userUpdateDao(user);
	}
	
	@Transactional(readOnly=true)
	public User userLoginService(String email,String password,HttpServletRequest request)
	{
		return userRegDao.userLogin(email, password, request);
		
	}
	
	@Transactional(readOnly=true)
	public User getUserById(int uid)
	{
		return userRegDao.getUserById(uid);
	}
}
