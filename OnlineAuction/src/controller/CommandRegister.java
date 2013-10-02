package controller;

import exceptions.MailSenderException;
import exceptions.ServiceLocatorException;
import group.Md5;
import group.RegisterForm;

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
	
	private static final String registerPage = "/WEB-INF/register.jsp";
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
		String namemd5 ="" ;

		RegisterForm new_user = new RegisterForm();
		request.setAttribute("RegisterForm",new_user);
		new_user.setUsername(username);
		new_user.setUserpwd(userpwd);
		new_user.setUseremail(useremail);

		if(!new_user.validate()){
			System.out.println("invalid");
			return registerPage;
		}

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			try {
				conn = DBConnectionFactory.getConnection();

				// check for unique usernames
				st = conn.prepareStatement("select * from Users where username = ?");
				st.setString(1, username);
				rs = st.executeQuery();
				if(rs.next()){
					new_user.setErrorMsg("name", "Sorry. This username is already in use");
					System.out.println("invalid");
					return registerPage;
				}
				st.close();
				rs.close();
				
				//check for unique emails
				st = conn.prepareStatement("select * from Users where email = ?");
				st.setString(1, useremail);
				rs = st.executeQuery();
				if(rs.next()){
					new_user.setErrorMsg("email", "Sorry. This email address is already in use");
					System.out.println("invalid");
					return registerPage;
				}
				st.close();
				rs.close();
				
				//insert
				Md5 md5 = new Md5();
				try {
					namemd5= md5.getMD5Str(username);
				} catch (NoSuchAlgorithmException e1) {
					System.out.println("No MD5 generated");
					e1.printStackTrace();
				}
				st = conn.prepareStatement("INSERT INTO Users (username, password, email, nameMd5, status, confirmed) VALUES (?, ?, ?, ?, '0', false);");
				st.setString(1, username);
				st.setString(2, userpwd);
				st.setString(3, useremail);
				st.setString(4, namemd5);
				st.executeUpdate();
				
			} catch (ServiceLocatorException e2) {
				e2.printStackTrace();
			} catch (SQLException e2) {
				e2.printStackTrace();
			} finally {
				if (st != null)
					st.close();
				if (rs != null)
					rs.close();
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
					+ namemd5;
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
