 package com.bridgeit.todo.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.User;
import com.bridgeit.todo.responsemsg.ErrorResponse;
import com.bridgeit.todo.responsemsg.Response;
import com.bridgeit.todo.responsemsg.UserResponse;
import com.bridgeit.todo.securepassword.HashSecurePassword;
import com.bridgeit.todo.service.UserRegService;
import com.bridgeit.todo.validation.UserValidation;

@RestController
public class UserRegController 
{
	@Autowired
	UserRegService userRegService;
	
	@Autowired
	UserValidation userValidation;
	
	@Autowired
	HashSecurePassword securePassword;
	
	private Logger logger=Logger.getLogger("UserRegController");
	
	UserResponse userResponse=new UserResponse();
	ErrorResponse errorResponse=new ErrorResponse();
	
	/* ....................User Registration..................... */
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public ResponseEntity<Response> userRegistration(@RequestBody User user,BindingResult result)
	{
			logger.debug("In userRegistration method");
		
			userValidation.validate(user, result);
		
			if(result.hasErrors())
			{
				System.out.println("error");
				
				List<FieldError> errorlist=result.getFieldErrors();
				errorResponse.setStatus(-1);
				errorResponse.setMessage("Error in user credentials");
				errorResponse.setErrorlist(errorlist);
				
				return new ResponseEntity<Response>(errorResponse,HttpStatus.NOT_ACCEPTABLE);
			}
		
			try
			{
				user.setPassword(securePassword.getSecurePassword(user.getPassword()));
				
				userRegService.userRegService(user);
				
				System.out.println("user add");
				userResponse.setStatus(1);
				userResponse.setMessage("User successfully registered");
				
				System.out.println("User added");
				
				logger.debug("User is added");
				
				return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
				
			}
			catch(Exception e)
			{
				System.out.println("Error");
				
				errorResponse.setStatus(-1);
				errorResponse.setMessage("Error occured while registering");
				
				return new ResponseEntity<Response>(errorResponse,HttpStatus.NOT_ACCEPTABLE);
			}
		
	}
	
	/* ............................User Update..................... */
	
	@RequestMapping(value="/userupdate",method=RequestMethod.POST)
	public ResponseEntity<Response> userUpdate(@RequestBody User user,BindingResult result)
	{
			System.out.println(user.toString());
			
			userValidation.validate(user, result);
		
			if(result.hasErrors())
			{
				System.out.println("error");
				
				List<FieldError> errorlist=result.getFieldErrors();
				
				errorResponse.setStatus(-1);
				errorResponse.setMessage("Error in user credentials");
				errorResponse.setErrorlist(errorlist);
				
				return new ResponseEntity<Response>(errorResponse,HttpStatus.NOT_ACCEPTABLE);
			}
		
			try
			{
				userRegService.userUpdateService(user);
				
				userResponse.setStatus(1);
				userResponse.setMessage("User successfully updated");
				
				logger.debug("User is updated");
				return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				System.out.println("Error");
				
				errorResponse.setStatus(-1);
				errorResponse.setMessage("Error occured while registering");
				
				return new ResponseEntity<Response>(errorResponse,HttpStatus.NOT_ACCEPTABLE);
			}
		
	}
	
	
	/*............................Get User By Id................................*/
	
	@RequestMapping(value="/userbyid")
	public void getUserById(int uid)
	{
		userRegService.getUserById(uid);
	}
	
}
