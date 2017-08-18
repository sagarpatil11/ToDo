package com.bridgeit.todo.socialutilty;

import java.net.URLEncoder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.bridgeit.todo.model.GoogleAccessToken;

/**
 * @author sagar
 *
 */
public class GoogleLoginUtility 
{
	public static final String GOOGLE_APP_ID="856547620727-d74gcfkibgc4lc5k6min731f6v0lshuo.apps.googleusercontent.com";
	public static final String GOOGLE_App_SECRET="09DXRe6DlNRjZZkoQ5JCwyqX";
	public static final String REDIRECT_URI="http://localhost:8080/todo/gmail-login";
	
	
	/**
	 * @return
	 */
	public String getGoogleOauthUrl()
	{	
		String 	googleloginurl="";
		
		try 
		{
			googleloginurl="https://accounts.google.com/o/oauth2/v2/auth?client_id="
					+ GOOGLE_APP_ID +"&redirect_uri="
					+ URLEncoder.encode(REDIRECT_URI, "UTF-8")
					+"&response_type=code &scope=profile email&approval_prompt=force&access_type=offline";
		   
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			
			System.out.println("there is some error");
		}
		
		return googleloginurl;
	}
	
	
	/**
	 * @param code
	 */
	public String getAccessToken(String code)
	{
		String accessTokenURL = "https://www.googleapis.com/oauth2/v4/token";
		
		ResteasyClient restCall = new ResteasyClientBuilder().build();
		
		ResteasyWebTarget target = restCall.target(accessTokenURL);
		
		Form f = new Form();
		f.param("client_id", GOOGLE_APP_ID);
		f.param("client_secret", GOOGLE_App_SECRET);
		f.param("redirect_uri",REDIRECT_URI );
		f.param("code", code);
		f.param("grant_type","authorization_code");
		
		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(f));
		
		GoogleAccessToken accessToken = response.readEntity(GoogleAccessToken.class);
		
		System.out.println("accesstoken"+accessToken);
		
		restCall.close();
		
		return accessToken.getAccess_token();
	}
	
	
}
