package com.bridgeit.todo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.FbProfile;
import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.service.TokenService;
import com.bridgeit.todo.service.UserRegService;
import com.bridgeit.todo.socialutilty.FbLoginUtility;

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
	
	@RequestMapping("/signin-facebook")
	public void afterRedirect(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("fb login success");
		
		String code=request.getParameter("code");
		
		try 
		{
			String accessToken=fbLoginUtility.getAccessToken(code);
			System.out.println("accessToken:"+accessToken);
			
			FbProfile fbProfile=fbLoginUtility.getFbProfile(accessToken);
			
			System.out.println("fbprofile::"+fbProfile);
			
			User user=userRegService.getUserByEmail(fbProfile.getEmail());
			
			if(user == null)
			{
				User newuser=new User();
				newuser.setEmail(fbProfile.getEmail());
				newuser.setFullname(fbProfile.getName());
				
				userRegService.userRegService(newuser);
				
				User fbuser=userRegService.getUserByEmail(fbProfile.getEmail());
				
				Token token=new Token();
				token.setAccessToken(accessToken);
				token.setAccessTokenCreation(new Date());
				token.setUser(fbuser);
				
				tokenService.saveToken(token);
				
				request.getSession().setAttribute("userSession", fbuser);
				
			}
			else
			{
				Token token=new Token();
				token.setAccessToken(accessToken);
				token.setAccessTokenCreation(new Date());
				token.setUser(user);
				
				tokenService.saveToken(token);
				
				request.getSession().setAttribute("userSession", user);
			}
			
			
			
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
