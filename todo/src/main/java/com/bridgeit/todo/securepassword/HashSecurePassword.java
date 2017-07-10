package com.bridgeit.todo.securepassword;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSecurePassword 
{
	 String salt="to-do_app";
	
	 public String getSecurePassword(String passwordToHash)
	    {
	        String generatedPassword = null;
	        String password=salt+passwordToHash;
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-1");
	            
	            byte[] bytes = md.digest(password.getBytes());
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            generatedPassword = sb.toString();
	        } 
	        catch (NoSuchAlgorithmException e) 
	        {
	            e.printStackTrace();
	        }
	        return generatedPassword;
	    }

	/*private byte[] getSalt() {
		// TODO Auto-generated method stub
		SecureRandom sr = null;
		try {
			sr = SecureRandom.getInstance("to-do_app");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        salt = new byte[16];
        sr.nextBytes(salt);
		return salt;
	}*/
}
