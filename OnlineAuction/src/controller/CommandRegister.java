package controller;

import exceptions.MailSenderException;
import exceptions.ServiceLocatorException;
import group.Md5;
import group.UserBean;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.DBConnectionFactory;
import jdbc.MailSender;

public class CommandRegister implements Command {
	
	private static final String registerPage = "/Register.jsp";
	private static final String confirmPage = "/Regsuc.jsp";

	public CommandRegister() {
		super();
	}
	

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("txtUname");
		String userpwd = request.getParameter("txtUpwd");
		String useremail = request.getParameter("txtUemail");

		UserBean user = new UserBean();
		user.setUsername(username);
		user.setUserpwd(userpwd);
		user.setUseremail(useremail);

//		request.setAttribute("RegisterForm",new_user);
		request.setAttribute("RegisterForm",user);
//		new_user.setUsername(username);
//		new_user.setUserpwd(userpwd);
//		new_user.setUseremail(useremail);

/*		if(!new_user.validate()){
			System.out.println("invalid");
			return registerPage;
		}
*/
		if(!user.validateForRegistration()){
			System.out.println("invalid");
			return registerPage;
		}

		Connection conn = null;
		try {
			try {
				conn = DBConnectionFactory.getConnection();
				boolean isUnique = user.isUnique(conn);
				if (isUnique == false) {
					return registerPage;
				}
				user.insertDatabase(conn);
			} catch (ServiceLocatorException e2) {
				e2.printStackTrace();
			} catch (SQLException e2) {
				e2.printStackTrace();
			} finally {
				if (conn != null)
					conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// send email
		try {
			MailSender mail = MailSender.getMailSender();
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("Complete your registration:\n");
			String link = request.getScheme() + "://"
					+ request.getServerName() +":" + request.getServerPort()
					+ request.getContextPath() + "/"
					+ "controller?action=regConfirm&confirm="
					+ user.getNameMd5();
			sbuffer.append(link);
			mail.sendMessage(useremail, "Complete your registration", sbuffer);
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		} catch (MailSenderException e) {
			e.printStackTrace();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		String msg = "Please login to your email account and click the provided URL to confirm registration";
	    request.setAttribute("msg", msg);
		
		return confirmPage;
	}


}
