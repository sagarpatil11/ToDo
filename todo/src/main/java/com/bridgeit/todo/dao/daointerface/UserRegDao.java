package com.bridgeit.todo.dao.daointerface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bridgeit.todo.model.User;

public interface UserRegDao 
{
	public void userRegDao(User user);
	
	public void userUpdateDao(User user);
	
	public User userLogin(String email,String password,HttpServletRequest request);
	
	public User getUserById(int uid);
	
	public void logout(HttpSession session);
}
