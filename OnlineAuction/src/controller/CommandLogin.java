package controller;

import group.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdbc.DBConnectionFactory;

public class CommandLogin implements Command {
	
	private static final String invalid = "/controller?action=loginPage";
	private static final String success = "/controller?action=userIndexPage";
    private static final String admin = "/controller?action=adminpage";
	public CommandLogin() {
		super();
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in service method");
		String un = request.getParameter("username");
		String pw = request.getParameter("password");
		HttpSession session = request.getSession();
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			boolean loginResult = UserBean.login(conn, request, un, pw);
			if (loginResult == true){
				if(UserBean.getstatus(conn, un, request)==2) {
					session.setAttribute("username", un);
					session.setAttribute("admin", "1");
					return admin;
				}
				if(UserBean.getstatus(conn, un, request)==0) {
					if(UserBean.getconfirmation(conn, un)) {
						session.setAttribute("username", un);
						return success;
					}
					else {
						String msg = "Please login to your email account and click the provided URL to confirm registration";
						request.setAttribute("msg", msg);
						return invalid;
					}
				}
				if(UserBean.getstatus(conn, un, request)==1) {
					String msg = "you have been banned, please contact adminastrators";
					request.setAttribute("msg", msg);
					return invalid;
				}
			}
			else
				return invalid;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return invalid;
	}


}
