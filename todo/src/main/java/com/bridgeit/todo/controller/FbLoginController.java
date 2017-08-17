package com.bridgeit.todo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.FbProfile;
import com.bridgeit.todo.model.User;
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
	
	/**
	 * this controller method redirects to the facebook login page
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/loginwithfb")
	public void fbLogin(HttpServletRequest request,HttpServletResponse response)
	{
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
		System.out.println("after login success");
		
		String code=request.getParameter("code");
		
		try 
		{
			String accessToken=fbLoginUtility.getAccessToken(code);
			System.out.println("accessToken:"+accessToken);
			
			FbProfile fbProfile=fbLoginUtility.getFbProfile(accessToken);
			
			User user=userRegService.getUserByEmail(fbProfile.getEmail());
			
			if(user == null)
			{
				user=new User();
				user.setEmail(fbProfile.getEmail());
				user.setFullname(fbProfile.getName());
				
				userRegService.userRegService(user);
			}
			
			
			
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
