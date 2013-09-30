package group;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailCfm {
	String username="lantiansoul";
	
	private Message getMessage(){

		Properties p=new Properties();
		p.put("mail.transport.protocol","smtp");
		p.put("mail.smtp.host","smtp.gmail.com");
		p.put("mail.smtp.port","465");
		p.put("mail.smtp.auth","true");
		
		
		String password="lantian056911";
		MyAuthenticator auth=new MyAuthenticator(username,password);
		Session session=Session.getDefaultInstance(p,auth);
		Message message=new MimeMessage(session);
		
		return message;
	}	
	public void sendVerify(String email,String namemd5)	throws MessagingException {
		
		Message message=getMessage();
		message.setFrom(new InternetAddress(username));
		message.setRecipient(RecipientType.TO,new InternetAddress(email));
		message.setSentDate(new Date());
		
		message.setSubject("Registration Confirmation");
		String m="<a href=\"http://localhost:8080/MailVerify?namemd5="+namemd5+"\">" +
		         "http://localhost:8080/MailVerify?namemd5="+namemd5+"</a>";		
		message.setContent(m,"text/html;charset=utf-8");
		Transport.send(message);
			
		}
}
