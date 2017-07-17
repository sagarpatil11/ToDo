package com.bridgeit.todo.dao.daointerface;

import javax.servlet.http.HttpSession;

import com.bridgeit.todo.model.User;

public interface UserRegDao 
{
	public void userRegDao(User user);
	
	public void userUpdateDao(User user);
	
	public User userLogin(String email,String password);
	
	public User getUserById(int uid);
	
	public void logout(HttpSession session);
	
	public void deleteToken(User user);
}
