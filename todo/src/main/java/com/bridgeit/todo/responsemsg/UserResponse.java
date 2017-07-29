package com.bridgeit.todo.responsemsg;

import java.util.List;

import com.bridgeit.todo.model.Task;
import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.model.User;

public class UserResponse extends Response 
{
	User user;
	Token token;
	List<Task> list;

	public List<Task> getList() {
		return list;
	}

	public void setList(List<Task> list) {
		this.list = list;
	}

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
