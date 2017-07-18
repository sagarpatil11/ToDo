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

import com.bridgeit.todo.token.TokenUtility;

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
		HttpServletResponse resp=(HttpServletResponse) response;
		
		WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		TokenUtility tokenUtility=context.getBean(TokenUtility.class);
		
		String accesstoken=req.getHeader("accesstoken");
	//	String refreshtoken=req.getHeader("refreshtoken");
		String tokencreationtime=req.getHeader("tokencreationtime");
		long creationtime=Long.parseLong(tokencreationtime);
		
		System.out.println(accesstoken +" "+ creationtime);
		
		Boolean isExpire=tokenUtility.validateToken(accesstoken,creationtime);

		if(isExpire == false)
		{
			System.out.println("Access Token is Expired");
		}
		else
		{
			chain.doFilter(request, response);
		}
	}

	
	/**
	 * @see AuthFilter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}


}
