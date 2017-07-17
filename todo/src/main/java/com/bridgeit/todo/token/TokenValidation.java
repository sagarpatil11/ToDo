package com.bridgeit.todo.token;


import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.service.TokenService;

public class TokenValidation 
{
	@Autowired
	TokenService tokenService;
	
	Token token;
	
	
}
