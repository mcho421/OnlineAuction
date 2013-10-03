package controller;

import group.DBconn;
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
	
	private static final String invalid = "/Login.jsp";
	private static final String success = "/UserIndex.jsp";

	public CommandLogin() {
		super();
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in service method");
		String un = request.getParameter("username");
		String pw = request.getParameter("password");
		
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			boolean loginResult = UserBean.login(conn, request, un, pw);
			if (loginResult == true)
				return success;
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
