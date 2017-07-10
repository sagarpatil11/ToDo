package com.bridgeit.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.User;
import com.bridgeit.todo.service.UserRegService;
import com.bridgeit.todo.validation.UserValidation;

@RestController
public class UserLoginController 
{
	@Autowired
	UserRegService userRegService;
	
	@Autowired
	UserValidation userValidation;
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<String> userLogin(@RequestBody User user,BindingResult result)
	{
		System.out.println(user.getEmail());
		
		userValidation.validate(user, result);
		
		if(result.hasErrors())
		{
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		User login_user=userRegService.userLoginService(user.getEmail(),user.getPassword());
		
		if(login_user != null)
		{
			return new ResponseEntity<String>("Login successfull",HttpStatus.ACCEPTED);
		}
		else
		{
			return new ResponseEntity<String>("Login not Successfull",HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
