package com.bridgeit.todo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.GoogleAccessToken;
import com.bridgeit.todo.model.GoogleProfile;
import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.responsemsg.ErrorResponse;
import com.bridgeit.todo.responsemsg.UserResponse;
import com.bridgeit.todo.service.TokenService;
import com.bridgeit.todo.service.UserRegService;
import com.bridgeit.todo.socialutilty.GoogleLoginUtility;
import com.bridgeit.todo.token.TokenUtility;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author sagar
 *
 */
@RestController
public class GoogleLoginController 
{	
	
	@Autowired
	GoogleLoginUtility googleLoginUtility;
	
	@Autowired
	UserRegService userRegService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	TokenUtility tokenUtility;
	
	UserResponse userResponse=new UserResponse();
	ErrorResponse errorResponse=new ErrorResponse();
	
	/**
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/loginwithgoogle")
	public void googleLogin(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("in google-login controller");
		
		String fbloginurl=googleLoginUtility.getGoogleOauthUrl();
		
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
	 */
	@RequestMapping(value="/gmail-login")
	public void afterRedirect(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("google login success");
		
		String code=request.getParameter("code");
		
		try
		{
		
				GoogleAccessToken googleAccessToken=googleLoginUtility.getAccessToken(code);
		
				JsonNode googleprofile=googleLoginUtility.getUserProfile(googleAccessToken.getAccess_token());
		
			//	System.out.println(profile);
				System.out.println(googleprofile.get("displayName").asText());
				JsonNode emailNode= googleprofile.get("emails");
				System.out.println(emailNode.get(0).get("value").asText());
				
				User user=userRegService.getUserByEmail(emailNode.get(0).get("value").asText());
				
				if(user == null)
				{
					System.out.println("new gmail user");
					
					User newuser=new User();
					newuser.setEmail(emailNode.get(0).get("value").asText());
					newuser.setFullname(googleprofile.get("displayName").asText());
					
					userRegService.userRegService(newuser);
					
					User gmailuser=userRegService.getUserByEmail(emailNode.get(0).get("value").asText());
					
					Token token= tokenUtility.tokenGenerator();
						
					token.setUser(gmailuser);
					
					tokenService.saveToken(token);
					
					request.getSession().setAttribute("userSession", gmailuser);
					
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
		catch (Exception e) 
		{
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
}
