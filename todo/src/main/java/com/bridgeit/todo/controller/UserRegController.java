 package com.bridgeit.todo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.User;
import com.bridgeit.todo.responsemsg.ErrorResponse;
import com.bridgeit.todo.responsemsg.Response;
import com.bridgeit.todo.responsemsg.UserResponse;
import com.bridgeit.todo.securepassword.HashSecurePassword;
import com.bridgeit.todo.service.UserRegService;
import com.bridgeit.todo.validation.EmailVarification;
import com.bridgeit.todo.validation.UserValidation;


/**
 * this rest controller have user registration related APIs 
 * @author sagar
 *
 */
@RestController
public class UserRegController 
{
	@Autowired
	UserRegService userRegService;
	
	@Autowired
	UserValidation userValidation;
	
	@Autowired
	HashSecurePassword securePassword;
	
	@Autowired
	EmailVarification emailVarification;
	
	private Logger logger=Logger.getLogger("UserRegController");
	
	UserResponse userResponse=new UserResponse();
	ErrorResponse errorResponse=new ErrorResponse();
	
	/* ....................User Registration..................... */
	
	/**
	 * this method is used to register the user
	 * @param user
	 * @param result
	 * @param request
	 * @return {@link ResponseEntity}
	 */
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public ResponseEntity<Response> userRegistration(@RequestBody User user,BindingResult result,HttpServletRequest request)
	{
			logger.debug("In userRegistration method");
		
			try 
			{
				userValidation.validate(user, result);
			} 
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			if(result.hasErrors())
			{
				System.out.println("error");
				
				List<FieldError> errorlist=result.getFieldErrors();
				errorResponse.setStatus(-1);
				errorResponse.setMessage("Error in user credentials");
				errorResponse.setErrorlist(errorlist);
				
				return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
			}
		
			try
			{
				user.setPassword(securePassword.getSecurePassword(user.getPassword()));
				
				user.setIsActive("false");
				
				userRegService.userRegService(user);
				
				//request.getSession().invalidate();
				
				String randomStr= RandomStringUtils.randomAlphanumeric(9);
				
				System.out.println("randomstr::"+randomStr);
				
				request.getSession().setAttribute(randomStr, user.getEmail());
				
				emailVarification.sendMailForVarification(user.getEmail(), randomStr);
				
				System.out.println("user add");
				userResponse.setStatus(1);
				userResponse.setMessage("User successfully registered");
				
				System.out.println("User added");
				
				logger.debug("User is added");
				
				return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Error");
				
				errorResponse.setStatus(-1);
				errorResponse.setMessage("Error occured while registering");
				
				return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
			}
		
	}
	
	/* ............................User Update..................... */
	
	/**
	 * this method is used to update user data
	 * @param user
	 * @param result
	 * @return {@link ResponseEntity}
	 */
	@RequestMapping(value="/userupdate",method=RequestMethod.POST)
	public ResponseEntity<Response> userUpdate(@RequestBody User user,BindingResult result)
	{
			System.out.println("in user update");
			System.out.println(user.toString());
			
			try 
			{
				userValidation.validate(user, result);
			} 
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			if(result.hasErrors())
			{
				System.out.println("error");
				
				List<FieldError> errorlist=result.getFieldErrors();
				
				errorResponse.setStatus(-1);
				errorResponse.setMessage("Error in user credentials");
				errorResponse.setErrorlist(errorlist);
				
				return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
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
				
				return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
			}
		
	}
	
	
	/*............................Get User By Id................................*/
	
	/**
	 * this method is used to get user by id
	 * @param uid
	 */
	@RequestMapping(value="/userbyid")
	public void getUserById(int uid)
	{
		userRegService.getUserById(uid);
	}
	
	
	
	/**
	 * this methosd is used to get user by email 
	 * @param email
	 * @return User {@link User}
	 */
	@RequestMapping(value="/userbyemail")
	public ResponseEntity<Response> getUpdatedUserByEmail(HttpServletRequest request)
	{
		User user= (User) request.getSession().getAttribute("userSession");
		
		try
		{
			User updatedUser=userRegService.getUserByEmail(user.getEmail());
			
			userResponse.setStatus(1);
			userResponse.setMessage("Updated user got successfully");
			userResponse.setUser(updatedUser);
			
			return new ResponseEntity<Response>(userResponse, HttpStatus.OK);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Error in getting updated user");
			
			return new ResponseEntity<Response>(errorResponse, HttpStatus.OK);
		}
	
	}
	
	
	
	/**
	 * this method is used to activate user's account
	 * @param email
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/activateUser")
	public void activateUser(@RequestParam String email,HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("in activate User");
		System.out.println(email);
		String getemail= (String) request.getSession().getAttribute(email);
		System.out.println("getemail value ::"+getemail);
		
		try
		{
			int result=userRegService.activateUserAccount(getemail);
		
			if(result == 1)
			{
		
				try 
				{
						response.sendRedirect("http://localhost:8080/todo/#!/login");
				} 
				catch (IOException e) 
				{
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			}
			else
			{
					System.out.println("user account activation failed");
			}
		}
		catch(Exception e)
		{
				e.printStackTrace();
		}
	}
	
	
	/**
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/forgetPassword",method=RequestMethod.POST)
	public ResponseEntity<Response> forgetPassword(@RequestBody Map<String, String> map, HttpServletRequest request)
	{
		String email=map.get("userEmail");
		
		System.out.println("in forgetpwd:email is::"+email);
		
		request.getSession().setAttribute("emailForResetPwd", email);
		
		try
		{
		
			emailVarification.sendEmailToResetPassword(email, "emailForResetPwd");
		
			userResponse.setStatus(7);
			userResponse.setMessage("email sent successfully");
		
			return new ResponseEntity<Response>(userResponse, HttpStatus.OK); 
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			errorResponse.setStatus(-7);
			errorResponse.setMessage("Error while sending email");
			
			return new ResponseEntity<Response>(errorResponse, HttpStatus.OK);
		}
		
	}
	
	/**
	 * @param email
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/redirectToResetPassword")
	public void redirectToResetPassword(@RequestParam String email,HttpServletRequest request,HttpServletResponse response)
	{
		String getemail= (String) request.getSession().getAttribute(email);
		System.out.println("getemail value ::"+getemail);
		
		if(getemail != null)
		{
			try 
			{
				response.sendRedirect("http://localhost:8080/todo/#!/resetPassword");
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("worng email key");
			}
		}
		
	}
	
	
	/**
	 * @param pwdmap
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/resetPassword",method=RequestMethod.POST)
	public ResponseEntity<Response> resetPassword(@RequestBody Map<String, String> pwdmap,HttpServletRequest request)
	{
		String newPassword= pwdmap.get("newPassword");
		
		String encriptedpwd=securePassword.getSecurePassword(newPassword);
		
		System.out.println("new pwd::"+newPassword+"::"+encriptedpwd);
		
		String emailToChangePwd=(String) request.getSession().getAttribute("emailForResetPwd");
		
		try
		{
			int result=userRegService.resetPassword(encriptedpwd, emailToChangePwd);
			
			if(result == 1)
			{
				System.out.println("reset password successfull");
				
				userResponse.setStatus(8);
				userResponse.setMessage("Reset Password successfull");
				
				return new ResponseEntity<Response>(userResponse, HttpStatus.OK);
			}
			else
			{
					System.out.println("Reset password unsucessfull");
					
					errorResponse.setStatus(-8);
					errorResponse.setMessage("Reset password unsucessfull");
					
					return new ResponseEntity<Response>(errorResponse, HttpStatus.OK);
					
			}
			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			errorResponse.setStatus(-8);
			errorResponse.setMessage("Reset password unsucessfull");
			
			return new ResponseEntity<Response>(errorResponse, HttpStatus.OK);
			
		}
		
	}
	
	
	/**
	 * @param picmap
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveProfilePic")
	public ResponseEntity<Response> saveProfilePic(@RequestBody Map<String, String> picmap,HttpServletRequest request)
	{
		String imgurl=picmap.get("profileImage");
		System.out.println("imgurl::"+imgurl);
		
		User user= (User) request.getSession().getAttribute("userSession");
		
		try
		{
				int result=userRegService.saveProfilePic(imgurl, user.getEmail());

				if(result == 1)
				{
						User updatedUser=userRegService.getUserByEmail(user.getEmail());
						
						userResponse.setStatus(10);
						userResponse.setMessage("Profile Photo updated successfully");
						userResponse.setUser(updatedUser);
						
						return new ResponseEntity<Response>(userResponse, HttpStatus.OK);
				}
				else
				{
					errorResponse.setStatus(-10);
					errorResponse.setMessage("Error while updating Profile Photo");
					
					return new ResponseEntity<Response>(errorResponse, HttpStatus.OK);
				}
		}
		catch (Exception e) 
		{
				// TODO: handle exception
				errorResponse.setStatus(-10);
				errorResponse.setMessage("Error while updating Profile Photo");
			
				return new ResponseEntity<Response>(errorResponse, HttpStatus.OK);
		}
	
	}
	
	
}
