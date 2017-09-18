package com.bridgeit.todo.controller;

import java.util.List;
import java.util.Map;

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
import com.bridgeit.todo.responsemsg.TokenResponse;
import com.bridgeit.todo.responsemsg.UserResponse;
import com.bridgeit.todo.securepassword.HashSecurePassword;
import com.bridgeit.todo.service.TokenService;
import com.bridgeit.todo.service.UserRegService;
import com.bridgeit.todo.token.TokenUtility;
import com.bridgeit.todo.validation.UserValidation;

/**
 * this rest controller have user login authentication method,user logout method and get new access token method 
 * @author sagar
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
	
	
	
	
	/**
	 * this method is used for user login authentication
	 * @param user
	 * @param result
	 * @param request
	 * @param response
	 * @return ResponseEntity {@link Response}
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<Response> userLogin(@RequestBody User user,BindingResult result,HttpServletRequest request,HttpServletResponse response)
	{
		
		logger.debug("In userLogin method");
		
		try {
			userValidation.loginValidate(user, result);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(result.hasErrors())
		{
			logger.debug("Error in login credentials");
			
			List<FieldError> errorlist=result.getFieldErrors();
			errorResponse.setStatus(-2);
			errorResponse.setMessage("Error in user credentials");
			errorResponse.setErrorlist(errorlist);
		
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
		}
		
		user.setPassword(securePassword.getSecurePassword(user.getPassword()));
	
		try 
		{
			User login_user=userRegService.userLoginService(user.getEmail(), user.getPassword());
			
			
			if(login_user != null)
			{
				logger.debug("Login Successfull");
					

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
				
				token.setUser(null);
				
				userResponse.setStatus(1);
				userResponse.setMessage("Login Successfull");
				userResponse.setToken(token);
				
				return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
			}
			else
			{
				logger.debug("Login unsuccessfull");
				
				errorResponse.setStatus(-1);
				errorResponse.setMessage("Wrong Email or Password");
				
				return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			errorResponse.setStatus(-3);
			errorResponse.setMessage("DataBase Problem");
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
		}
	}
	
	

	
	
	/**
	 * this method is used for user logout
	 * @param request
	 * @return ResponseEntity {@link Response}
	 */
	@RequestMapping(value="/logout")
	public ResponseEntity<Response> logout(HttpServletRequest request) 
	{
		/*HttpSession session=request.getSession();*/
		
		String accessToken=request.getHeader("accessToken");
		System.out.println("in logout "+accessToken);
		
		try 
		{
			
			userRegService.logout(accessToken);
			
			userResponse.setStatus(1);
			userResponse.setMessage("User Logout Successfully");
			
			return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			errorResponse.setStatus(-1);
			errorResponse.setMessage("DataBase Problem");
			
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
		}
	}
	
	
	
	
	
	/**
	 * this method is used to get new access token if refresh token is valid
	 * @param request
	 * @return ResponseEntity {@link TokenResponse}
	 */
	@RequestMapping(value="/newAccessToken",method=RequestMethod.POST)
	public ResponseEntity<TokenResponse> getNewAccessTokenByRefreshToken(HttpServletRequest request)
	{
		String refreshToken=request.getHeader("refreshToken");
		
		System.out.println("new access token "+refreshToken);
		
		TokenResponse tokenResponse=tokenUtility.validateRefreshToken(refreshToken);
		
		
		/*if(token != null)
		{
			userResponse.setStatus(1);
			userResponse.setMessage("New Access Token generated");
			userResponse.setToken(token);
		
			return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
		}
		
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Refresh token expired");*/
			
		return new ResponseEntity<TokenResponse>(tokenResponse,HttpStatus.OK);
		
	}
	
	
	/**
	 * @param tokenMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getTokenByUrl",method=RequestMethod.POST)
	public ResponseEntity<Response> getTokenByUrl(@RequestBody Map<String, String> tokenMap,HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("tokenstr "+tokenMap.get("urlString"));
		
		String tokenurl= tokenMap.get("urlString");
		
		Token token=(Token) request.getSession().getAttribute(tokenurl);
		
		System.out.println("token:: "+token);
		
		User user= (User) request.getSession().getAttribute("userSession");
		
		if(token != null)
		{	
			userResponse.setStatus(5);
			userResponse.setMessage("token received.Login successfull");
			userResponse.setToken(token);
			userResponse.setUser(user);
			
			return new ResponseEntity<Response>(userResponse, HttpStatus.OK);
			
		}
		
		
		return null;
	}
}
