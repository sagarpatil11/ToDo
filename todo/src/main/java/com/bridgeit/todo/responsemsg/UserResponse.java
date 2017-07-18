package com.bridgeit.todo.responsemsg;

import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.model.User;

public class UserResponse extends Response 
{
	User user;
	Token token;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
	
	
}
