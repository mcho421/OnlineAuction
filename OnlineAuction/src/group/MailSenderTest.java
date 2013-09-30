package group;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Session;
import javax.mail.URLName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

//import edu.unsw.comp9321.common.ServiceLocatorException;
//import edu.unsw.comp9321.jdbc.MailSender;
//import edu.unsw.comp9321.jdbc.MailSenderException;

import junit.framework.TestCase;

public class MailSenderTest extends TestCase {

	static Logger logger = Logger.getLogger(MailSenderTest.class.getName());
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// To test JNDI outside Tomcat, we need to set these
		 // values manually ... (just for testing purposes)
		 System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
            "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, 
            "org.apache.naming");            

        // Create InitialContext with java:/comp/env/mail
        InitialContext ic = new InitialContext();

        ic.createSubcontext("java:");
        ic.createSubcontext("java:comp");
        ic.createSubcontext("java:comp/env");
        ic.createSubcontext("java:comp/env/mail");
       
        //Construct a naming reference
        Reference ref = new Reference("javax.mail.Session", "org.apache.naming.factory.MailSessionFactory", null);
        ref.add(new StringRefAddr("auth", "Container"));
        //You can also use other smtp providers
        ref.add(new StringRefAddr("mail.smtp.host","smtp.cse.unsw.edu.au"));
        ref.add(new StringRefAddr("mail.smtp.auth","true"));
        //Replace as necessary
        ref.add(new StringRefAddr("mail.smtp.user","your_username"));
        ref.add(new StringRefAddr("mail.smtp.password","your_password"));
        //Turn this off to avoid username and password appearing in log files.
        ref.add(new StringRefAddr("mail.debug","false"));
        ref.add(new StringRefAddr("mail.smtp.starttls.enable","true"));
        
        ic.bind("java:comp/env/mail/Session", ref);
	}
	
	public void testSession(){
		MailSender sender = null;
		try {
			sender = MailSender.getMailSender();
			assertNotNull(sender);
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
			fail("Could not obtain session");
		} catch (MailSenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Could not obtain session");
		}
	}
	
	public void testSendMessage(){
		MailSender sender = null;
		try{
			sender = MailSender.getMailSender();
			//Replace as necessary
			String fromAddress = "your_addr@cse.unsw.edu.au";
			String toAddress = "target_address@target_domain.com";
			String subject = "test";
			StringBuffer mailBody = new StringBuffer();
			mailBody.append("Dear Mailer, Why you email me?!!");
			sender.sendMessage(fromAddress, toAddress, subject, mailBody);
			assertTrue(true);
 		}catch(Exception e){
			e.printStackTrace();
			fail("MailSender did not send message");
		}
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		InitialContext ic = new InitialContext();
		Context subContext = (Context) ic.lookup("java:comp");
		logger.info("Found sub-context");
		subContext.destroySubcontext("/env/mail");
		ic.destroySubcontext("java:comp");
		ic.destroySubcontext("java:");
	}

}
