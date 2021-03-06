package com.bridgeit.todo.service;

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
	public User userLoginService(String email,String password)
	{
		return userRegDao.userLogin(email, password);
		
	}
	
	@Transactional(readOnly=true)
	public User getUserById(int uid)
	{
		return userRegDao.getUserById(uid);
	}
	
	@Transactional(readOnly=true)
	public User getUserByEmail(String email)
	{
		return userRegDao.getUserByEmail(email);
	}
	
	@Transactional
	public void logout(String accessToken)
	{
		userRegDao.deleteToken(accessToken);
	}
	
	@Transactional
	public int activateUserAccount(String email)
	{
		return userRegDao.activateUserAccount(email);
	}
	
	@Transactional
	public int resetPassword(String newPwd,String email)
	{
		return userRegDao.resetPassword(newPwd, email);
	}
	
	@Transactional
	public int saveProfilePic(String imgurl,String email)
	{
		return userRegDao.saveProfilePic(imgurl, email);
	}
	
}
