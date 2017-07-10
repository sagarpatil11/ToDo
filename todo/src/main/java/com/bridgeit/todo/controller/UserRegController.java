package com.bridgeit.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class UserRegController 
{
	@Autowired
	UserRegService userRegService;
	
	@Autowired
	UserValidation userValidation;
	
	
	/* ....................User Registration..................... */
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public ResponseEntity<String> userRegistration(@RequestBody User user,BindingResult result)
	{
		
			userValidation.validate(user, result);
		
			if(result.hasErrors())
			{
				System.out.println("error");
			
				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
			}
			
			try
			{
				userRegService.userRegService(user);
				
				System.out.println("User added");
				
				return new ResponseEntity<String>("User added successfully",HttpStatus.OK);
			}
			catch(Exception e)
			{
				System.out.println("Error");
				
				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
			}
		
	}
	
	/* ............................User Update..................... */
	
	@RequestMapping(value="/userupdate",method=RequestMethod.POST)
	public ResponseEntity<String> userUpdate(@RequestBody User user,BindingResult result)
	{
			System.out.println(user.toString());
			
			userValidation.validate(user, result);
		
			if(result.hasErrors())
			{
				System.out.println("error");
			
				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
			}
		
			try
			{
				userRegService.userUpdateService(user);
				
				return new ResponseEntity<String>("User updated",HttpStatus.OK);
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				System.out.println("Error");
				
				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
			}
		
	}
	
	
	/*............................Get User By Id................................*/
	
	@RequestMapping(value="/userbyid")
	public void getUserById(int uid)
	{
		userRegService.getUserById(uid);
	}
	
}
