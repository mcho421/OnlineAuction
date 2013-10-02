package jdbc;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import exceptions.MailSenderException;
import exceptions.ServiceLocatorException;


public class MailSender{
	 
	static Logger logger = Logger.getLogger(MailSender.class.getName());
	static MailSender sender;
	private Session session;
	private InitialContext ctx;
	
	/**
	 * This contructor is invoked according to singleton pattern. It looks up the mail session from 
	 * the context, initialises it and then  
	 * @throws MailSenderException
	 * @throws ServiceLocatorException
	 */	
	private MailSender() throws MailSenderException, ServiceLocatorException{ 
		try{
			ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:comp/env");
			session = (Session) envContext.lookup("mail/Session");
			logger.info("Mailer Session obtained"+session.toString());
			Properties mailProps = session.getProperties();
			String username = (String)mailProps.get("mail.smtp.user");
			String password = (String)mailProps.get("mail.smtp.password");
			if((username==null || password==null ) || 
					(username.isEmpty()||password.isEmpty()))
				throw new MailSenderException("Authentication data is not provided") ;
			this.setAuthData(mailProps, username, password);
		}catch(NamingException e){
			logger.severe("Cannot find context, throwing exception"+e.getMessage());
			e.printStackTrace();
			throw new ServiceLocatorException();
		}
	}
	
	/**
	 * This function stores a username/password combination into the mail session. The authentication is 
	 * only performed when a mail is sent.
	 */
	public void setAuthData(final Properties mailProperties, final String username, final String password){
		session = Session.getInstance(mailProperties,
				  new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username,password);
					}
				  });
	}
	
	public void sendMessage(String fromAddress, String toAddress, String mailSubject, StringBuffer text) throws AddressException, MessagingException{
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddress));
		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toAddress));
		message.setSubject(mailSubject);
		message.setText(text.toString());

		Transport.send(message);

		logger.info("Sent message "+toAddress+" with subject "+mailSubject);
	}
	
	public static MailSender getMailSender() throws ServiceLocatorException, MailSenderException{
		if(sender==null)
			sender = new MailSender();	
		return sender;
	}

}
