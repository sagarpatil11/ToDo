package com.bridgeit.todo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bridgeit.todo.responsemsg.TokenResponse;
import com.bridgeit.todo.token.TokenUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet Filter implementation class Filter
 */
public class AuthFilter implements Filter {

	public AuthFilter()
	{
		
	}
   
    /**
	 * @see AuthFilter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * @see AuthFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		// TODO Auto-generated method stub
		// place your code here
		
		HttpServletRequest req=(HttpServletRequest) request;
		//HttpServletResponse resp=(HttpServletResponse) response;
		
		WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		
		TokenUtility tokenUtility=context.getBean(TokenUtility.class);
		
		TokenResponse tokenResponse=context.getBean(TokenResponse.class);
		
		String accesstoken=req.getHeader("accesstoken");
		
		
		System.out.println(accesstoken);
		
		tokenResponse=tokenUtility.validateAccessToken(accesstoken);

		ObjectMapper mapper = new ObjectMapper();
		
		if(tokenResponse.getStatus() == -4)
		{
			System.out.println("Access Token is Expired");
			
			//String result="Access Token expired";
			//String jsonResp = "{\"status\":-4,\"errorMessage\":\"Access token is expired. Generate new Access Tokens\"}";
			
			String jsonResponse= mapper.writeValueAsString(tokenResponse);
			
			response.getWriter().write(jsonResponse);
			
			return;
		}
		
		chain.doFilter(request, response);
		
	}

	
	/**
	 * @see AuthFilter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}


}
