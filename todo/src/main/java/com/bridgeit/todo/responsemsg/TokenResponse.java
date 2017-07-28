package com.bridgeit.todo.responsemsg;

import com.bridgeit.todo.model.Token;

public class TokenResponse 
{
	private int status;
	private String message;
	Token token;
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
	
	
}
