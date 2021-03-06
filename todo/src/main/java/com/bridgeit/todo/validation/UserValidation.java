package com.bridgeit.todo.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bridgeit.todo.model.User;

public class UserValidation implements Validator
{
	 private Pattern pattern;  
	 private Matcher matcher;  
	 
	private static final String EMAIL_PATTERN = "^[_a-z-\\+]+(\\.[_a-z0-9-]+)*@" 
													+ "[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$"; 
	
	
	
	String STRING_PATTERN = "^[a-zA-Z\\s]*$";  
	String MOBILE_PATTERN = "[0-9]{10}";
	String PASSWORD_PATTERN="[a-zA-z0-9]{6,15}";
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate(Object object, Errors error) 
	{
		// TODO Auto-generated method stub
		User user=(User) object;
		
		
		//Name validation
		
		ValidationUtils.rejectIfEmpty(error, "fullname", "required.fullname", "Name is Required");
		
		  
		if (!(user.getFullname() != null && user.getFullname().isEmpty())) 
		{  
			  pattern = Pattern.compile(STRING_PATTERN);  
			  matcher = pattern.matcher(user.getFullname());  
			  
			  if (!matcher.matches()) 
			  {  
				  error.rejectValue("fullname", "fullname.containNonChar", "Enter a valid name");  
			  }  
		  } 
		 
		  
		// Email validation 
		  
		  ValidationUtils.rejectIfEmptyOrWhitespace(error, "email", "required.email", "Email is required");  
				  
			  
		  if (!(user.getEmail() != null && user.getEmail().isEmpty())) 
		  {  
				 pattern = Pattern.compile(EMAIL_PATTERN);  
				 matcher = pattern.matcher(user.getEmail());  
				 
				 if (!matcher.matches()) 
				 {  
				    error.rejectValue("email", "email.incorrect", "Enter a correct Email");  
				 }  
		  } 
		  
		// Mobile number validation 
		
		  ValidationUtils.rejectIfEmptyOrWhitespace(error, "mobile", "required.mobile", "Phone is required");  
				  
				 
		  if (!(user.getMobile() != null && user.getMobile().isEmpty())) 
		  {  
				pattern = Pattern.compile(MOBILE_PATTERN);  
				matcher = pattern.matcher(user.getMobile());  
				   
				if (!matcher.matches()) 
				{  
				    error.rejectValue("mobile", "mobile.incorrect", "Enter a Correct Mobile Number");  
				}  
		  }
		  
		 
		  //Password validation
		  
		  ValidationUtils.rejectIfEmptyOrWhitespace(error, "password", "required.password", "Password is required");  
				  
		  if (!(user.getPassword() != null && user.getPassword().isEmpty())) 
		  {  
				pattern = Pattern.compile(PASSWORD_PATTERN);  
				matcher = pattern.matcher(user.getPassword());  
				   
				if (!matcher.matches()) 
				{  
				    error.rejectValue("password", "password.incorrect", "Password contain atleast 6 characters or digits and maximum 15");  
				}  
		  }
	}
	
	
	public void loginValidate(Object object,Errors error)
	{
		
		User user=(User) object;
		
		// Email validation 
		  
		  ValidationUtils.rejectIfEmptyOrWhitespace(error, "email", "required.email", "Email is required");  
				  
			  
		  if (!(user.getEmail() != null && user.getEmail().isEmpty())) 
		  {  
				 pattern = Pattern.compile(EMAIL_PATTERN);  
				 matcher = pattern.matcher(user.getEmail());  
				 
				 if (!matcher.matches()) 
				 {  
				    error.rejectValue("email", "email.incorrect", "Enter a correct Email");  
				 }  
		  } 
		  
		  
		  //Password validation
		  
		  ValidationUtils.rejectIfEmptyOrWhitespace(error, "password", "required.password", "Password is required");  
				  
		  if (!(user.getPassword() != null && user.getPassword().isEmpty())) 
		  {  
				pattern = Pattern.compile(PASSWORD_PATTERN);  
				matcher = pattern.matcher(user.getPassword());  
				   
				if (!matcher.matches()) 
				{  
				    error.rejectValue("password", "password.incorrect", "Password contain atleast 6 characters or digits and maximum 15");  
				}  
		  }
	}

}
