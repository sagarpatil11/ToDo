package com.bridgeit.todo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.FbProfile;
import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.responsemsg.ErrorResponse;
import com.bridgeit.todo.responsemsg.Response;
import com.bridgeit.todo.responsemsg.UserResponse;
import com.bridgeit.todo.service.TokenService;
import com.bridgeit.todo.service.UserRegService;
import com.bridgeit.todo.socialutilty.FbLoginUtility;
import com.bridgeit.todo.token.TokenUtility;

/**
 * 
 * @author sagar
 *
 */
@RestController
public class FbLoginController 
{
	@Autowired
	FbLoginUtility fbLoginUtility;
	
	@Autowired
	UserRegService userRegService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	TokenUtility tokenUtility;
	
	UserResponse userResponse=new UserResponse();
	ErrorResponse errorResponse=new ErrorResponse();
	
	/**
	 * this controller method redirects to the facebook login page
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/loginwithfb")
	public void fbLogin(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("in login controller");
		
		String fbloginurl=fbLoginUtility.getFbOauthUrl();
		
		try 
		{
			response.sendRedirect(fbloginurl);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/signin-facebook")
	public void afterRedirect(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		System.out.println("fb login success");
		
		String code=request.getParameter("code");
		
		try 
		{
			String accessToken=fbLoginUtility.getAccessToken(code);
			System.out.println("accessToken:"+accessToken);
			
			FbProfile fbProfile=fbLoginUtility.getFbProfile(accessToken);
			
			//System.out.println("fbprofile::"+fbProfile);
			
			User user=userRegService.getUserByEmail(fbProfile.getEmail());
			
			if(user == null)
			{
				System.out.println("new fb user");
				
				User newuser=new User();
				newuser.setEmail(fbProfile.getEmail());
				newuser.setFullname(fbProfile.getName());
			//	newuser.setProfileImage(fbProfile.);
				newuser.setIsActive("true");
				
				userRegService.userRegService(newuser);
				
				User fbuser=userRegService.getUserByEmail(fbProfile.getEmail());
				
				Token token= tokenUtility.tokenGenerator();
					
				token.setUser(fbuser);
				
				tokenService.saveToken(token);
				
				request.getSession().setAttribute("userSession", fbuser);
				
				request.getSession().setAttribute("tokenObj", token);
				
				response.sendRedirect("http://localhost:8080/todo/#!/socialRedirect?token=tokenObj");
				
			}
			else
			{	
				System.out.println("user already exits");
				
				Token token= tokenUtility.tokenGenerator();
				
				token.setUser(user);
				
				tokenService.saveToken(token);
				
				request.getSession().setAttribute("userSession", user);
			
				request.getSession().setAttribute("tokenObj", token);
				
				response.sendRedirect("http://localhost:8080/todo/#!/socialRedirect?token=tokenObj");
			}
			
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
