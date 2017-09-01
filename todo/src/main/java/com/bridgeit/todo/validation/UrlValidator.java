package com.bridgeit.todo.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator 
{
	public static final String URL_REGEX= "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*"+
			"[-a-zA-Z0-9+&@#/%=~_|]";
	
	public static String isValidUrl(String url)
	{
		Pattern pattern= Pattern.compile(URL_REGEX);
		Matcher matcher= pattern.matcher(url);
		
		if(matcher.find())
		{
			int start= matcher.start();
			int end= matcher.end();
			
			return url.substring(start, end);
		}
		return null;
		
		
	}
}
