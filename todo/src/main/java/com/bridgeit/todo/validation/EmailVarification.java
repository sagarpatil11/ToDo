package com.bridgeit.todo.validation;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailVarification 
{
	
	/**
	 * this method is used to send email for user email varification
	 * @param sendMailTo
	 * @param useremail
	 */
	public void sendMailForVarification(String sendMailTo, String useremail)
	{
		final String username = "bridgeitjdbl@gmail.com";
		final String password = "fundoonotes";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try 
		{

			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(username));
			
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(sendMailTo));
			
			message.setSubject("Activate Fundoo Account");
			
			message.setContent("Dear user,"
				+ "\n\n click on the below link to activate your Fundoo Notes account.","text/html");
			message.setContent("\n\n <a href='http://localhost:8080/todo/activateUser?email="+useremail+"'>Fundoo Notes</a>","text/html");
			

			Transport.send(message);

			System.out.println("Done");

		} 
		catch (MessagingException e) 
		{
			System.out.println("email not sent");
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * this method is used to send email for reset password
	 * @param sendMailTo
	 * @param useremail
	 */
	public void sendEmailToResetPassword(String sendMailTo, String useremail)
	{
		final String username = "bridgeitjdbl@gmail.com";
		final String password = "fundoonotes";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try 
		{

			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(username));
			
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(sendMailTo));
			
			message.setSubject("Activate Fundoo Account");
			
			message.setContent("Dear user,"
				+ "\n\n click on the below link to reset the password.","text/html");
			message.setContent("\n\n <a href='http://localhost:8080/todo/activateUser?email="+useremail+"'>Fundoo Notes</a>","text/html");
			

			Transport.send(message);

			System.out.println("Done");

		} 
		catch (MessagingException e) 
		{
			System.out.println("email not sent");
			throw new RuntimeException(e);
		}
	}
	
}
