package com.bridgeit.todo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.GoogleAccessToken;
import com.bridgeit.todo.model.GoogleProfile;
import com.bridgeit.todo.socialutilty.GoogleLoginUtility;

/**
 * @author sagar
 *
 */
@RestController
public class GoogleLoginController 
{	
	
	@Autowired
	GoogleLoginUtility googleLoginUtility;
	
	
	
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
		
		GoogleAccessToken googleAccessToken=googleLoginUtility.getAccessToken(code);
		
		GoogleProfile profile=googleLoginUtility.getUserProfile(googleAccessToken.getAccess_token());
		
		System.out.println(profile);
	}
	
}
