package com.bridgeit.todo.dao.daointerface;

import javax.servlet.http.HttpServletRequest;

import com.bridgeit.todo.model.User;

public interface UserRegDao 
{
	public void userRegDao(User user);
	
	public void userUpdateDao(User user);
	
	public User userLogin(String email,String password,HttpServletRequest request);
	
	public User getUserById(int uid);
}
