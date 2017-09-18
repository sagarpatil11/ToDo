package com.bridgeit.todo.responsemsg;

import java.util.List;

import org.springframework.validation.FieldError;

public class ErrorResponse extends Response 
{
	Exception exception;
	List<FieldError> errorlist;
	
	public Exception getException() {
		return exception;
	}
	
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	public List<FieldError> getErrorlist() {
		return errorlist;
	}
	
	public void setErrorlist(List<FieldError> errorlist) {
		this.errorlist = errorlist;
	}
	
	
}
