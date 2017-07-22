package com.bridgeit.todo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.responsemsg.ErrorResponse;
import com.bridgeit.todo.responsemsg.Response;
import com.bridgeit.todo.responsemsg.UserResponse;
import com.bridgeit.todo.securepassword.HashSecurePassword;
import com.bridgeit.todo.service.TokenService;
import com.bridgeit.todo.service.UserRegService;
import com.bridgeit.todo.token.TokenUtility;
import com.bridgeit.todo.validation.UserValidation;

/**
 * 
 * @author bridgeit
 *
 */
@RestController
public class UserLoginController 
{
	@Autowired
	UserRegService userRegService;
	
	@Autowired
	UserValidation userValidation;
	
	@Autowired
	HashSecurePassword securePassword;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	TokenUtility tokenUtility;
	
	UserResponse userResponse=new UserResponse();
	ErrorResponse errorResponse=new ErrorResponse();
	
	private Logger logger=Logger.getLogger("UserLoginController");
	
	
	//...............................Login authentication................................//
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<Response> userLogin(@RequestBody User user,BindingResult result,HttpServletRequest request,HttpServletResponse response)
	{
		
		logger.debug("In userLogin method");
		
		userValidation.loginValidate(user, result);
		
		if(result.hasErrors())
		{
			logger.debug("Error in login credentials");
			
			List<FieldError> errorlist=result.getFieldErrors();
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Error in user credentials");
			errorResponse.setErrorlist(errorlist);
		
			return new ResponseEntity<Response>(errorResponse,HttpStatus.NOT_ACCEPTABLE);
		}
		
		user.setPassword(securePassword.getSecurePassword(user.getPassword()));
	
		try {
			User login_user=userRegService.userLoginService(user.getEmail(), user.getPassword());
			
			
			if(login_user != null)
			{
				logger.debug("Login Successfull");
				
				userResponse.setStatus(1);
				userResponse.setMessage("Login Successfull");
				

				HttpSession session2=request.getSession();
				session2.setAttribute("userSession", login_user);
				
				Token token=tokenUtility.tokenGenerator();
				
				token.setUser(login_user);
				
				try {
					tokenService.saveToken(token);
					
					System.out.println(token.toString());
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
				}
				
				response.setHeader("accesstoken", token.getAccessToken());
				return new ResponseEntity<Response>(userResponse,HttpStatus.ACCEPTED);
			}
			else
			{
				logger.debug("Login unsuccessfull");
				
				errorResponse.setStatus(-1);
				errorResponse.setMessage("Wrong Email or Password");
				
				return new ResponseEntity<Response>(errorResponse,HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorResponse.setStatus(-1);
			errorResponse.setMessage("DataBase Problem");
			return new ResponseEntity<Response>(errorResponse,HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	
	
	//............................User Logout.............................//
	
	
	@RequestMapping(value="/logout")
	public ResponseEntity<Response> logout(HttpServletRequest request) 
	{
		HttpSession session=request.getSession();
		
		userRegService.logout(session);
		
		userResponse.setStatus(1);
		userResponse.setMessage("User Logout Successfully");
		
		return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
	}
}
