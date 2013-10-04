package jdbc;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import exceptions.MailSenderException;
import exceptions.ServiceLocatorException;

class MailTask implements Runnable{
	
	String fromAddress;
	String toAddress;
	String mailSubject; 
	StringBuffer text;
	MailSender sender;
	
	public MailTask(String fromAddress, String toAddress, String mailSubject,
			StringBuffer text, MailSender sender) {
		super();
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.mailSubject = mailSubject;
		this.text = text;
		this.sender = sender;
	}

	@Override
	public void run() {
		
		try {
			sender.sendMessage(fromAddress, toAddress, mailSubject, text);
		} catch (AddressException e) {
			Logger.getAnonymousLogger().severe("Sending mail from "+fromAddress+" to "+toAddress+" failed");
			Logger.getAnonymousLogger().severe(e.getLocalizedMessage());
		} catch (MessagingException e) {
			Logger.getAnonymousLogger().severe("Sending mail from "+fromAddress+" to "+toAddress+" failed");
			Logger.getAnonymousLogger().severe(e.getLocalizedMessage());
		}
		
	}
	
}

public class MailSenderService {
	
	static Logger logger = Logger.getLogger(MailSenderService.class.getName());
	static MailSenderService sender;
	private MailSender mailSender;
	private ThreadPoolExecutor executor;
	private Session session;
	private InitialContext ctx;
	
	private MailSenderService() throws MailSenderException, ServiceLocatorException{
		try {
			ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:comp/env");
			session = (Session) envContext.lookup("mail/Session");
			logger.info("Mailer Session obtained"+session.toString());
			BlockingQueue<Runnable> mailQueue = new ArrayBlockingQueue<Runnable>(10);
			executor = new ThreadPoolExecutor(5, 20, 20, TimeUnit.SECONDS, mailQueue);
//			mailSender = new MailSender();
			mailSender = MailSender.getMailSender();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static MailSenderService getMailSender() throws ServiceLocatorException, MailSenderException{
		if(sender==null)
			sender = new MailSenderService();	
		return sender;
	}
	
	public void sendMessage(String toAddress, String mailSubject,
			StringBuffer text) throws AddressException, MessagingException{
		
		Properties mailProps = session.getProperties();
		String fromAddress = (String)mailProps.get("mail.smtp.user");
		MailTask task = new MailTask(fromAddress, toAddress, mailSubject, text, mailSender);
		executor.execute(task);
		
	}

}
