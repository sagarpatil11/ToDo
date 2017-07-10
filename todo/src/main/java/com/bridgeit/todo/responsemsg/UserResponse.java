package com.bridgeit.todo.responsemsg;

import com.bridgeit.todo.model.User;

public class UserResponse extends Response 
{
	User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
