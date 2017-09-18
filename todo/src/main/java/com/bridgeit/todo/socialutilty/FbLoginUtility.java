package com.bridgeit.todo.socialutilty;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.bridgeit.todo.model.FbAccessToken;
import com.bridgeit.todo.model.FbProfile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author sagar
 *
 */
public class FbLoginUtility 
{
	public static final String Fb_APP_ID="1136987416445864";
	public static final String Fb_App_SECRET="9860319a9ab564da8efe19beaa30263c";
	public static final String REDIRECT_URI="http://localhost:8080/todo/signin-facebook";
	
	
	
	/**
	 * @return
	 */
	public String getFbOauthUrl()
	{	
		String fbloginurl="";
		
		try 
		{
			fbloginurl="http://www.facebook.com/dialog/oauth?" + "client_id="+Fb_APP_ID + 
						"&redirect_uri="+ URLEncoder.encode(REDIRECT_URI, "UTF-8")
						+"&response_type=code &scope=email,public_profile";
		   
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			
			System.out.println("there is some error");
		}
		
		return fbloginurl;
	}
	
	
	
	/**
	 * @param code
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getAccessToken(String code) throws UnsupportedEncodingException
	{
		String AccessToken_URL	="https://graph.facebook.com/oauth/access_token?"
			       				+"client_id="+Fb_APP_ID+"&redirect_uri="+URLEncoder.encode(REDIRECT_URI, "UTF-8")
			       				+"&client_secret="+Fb_App_SECRET+"&code="+code;
		
		ResteasyClient client= new ResteasyClientBuilder().build();
		ResteasyWebTarget target=client.target(AccessToken_URL);
		Response response=target.request().accept(MediaType.APPLICATION_JSON).get();
		
		String token=response.readEntity(String.class);
		
		ObjectMapper objectMapper=new ObjectMapper();
		
		FbAccessToken fbAccessToken=null;
		
		try 
		{
			fbAccessToken=objectMapper.readValue(token, FbAccessToken.class);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		System.out.println(fbAccessToken.getAccess_token());
		
		return fbAccessToken.getAccess_token();
		
	}
	
	
	public JsonNode getFbProfile(String accessToken)
	{
		String getFbProfileURL="https://graph.facebook.com/v2.9/me?access_token="+accessToken+"&fields=id,name,email,picture";
		
		ResteasyClient client= new ResteasyClientBuilder().build();
		ResteasyWebTarget target=client.target(getFbProfileURL);
		Response response=target.request().accept(MediaType.APPLICATION_JSON).get();
		
		String profile=response.readEntity(String.class);
		
		ObjectMapper objectMapper=new ObjectMapper();
		
		JsonNode fbProfile=null;
		
		try 
		{
			//fbProfile=objectMapper.readValue(profile, FbProfile.class);
			fbProfile=objectMapper.readTree(profile);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return fbProfile;
	}
}
