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
		
		String msg = " ";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			try {
				conn = DBConnectionFactory.getConnection();
				String sqlQuery = "select id, username from Users where username = ? and password = ?";
				st = conn.prepareStatement(sqlQuery);
				st.setString(1, un);
				st.setString(2, pw);
				rs = st.executeQuery();
				if(rs.next()) {
					int uid = rs.getInt(1);
					String username = rs.getString(2);
					HttpSession session = request.getSession();
					session.setAttribute("username", username);
					UserBean user = new UserBean();
					user.Initialize(username);
					request.setAttribute("UserBean",user);
					System.out.println("SUCCESS");
					return success;
				} else {
					msg = "invalid username or password!";
					request.setAttribute("msg", msg);
					return invalid;
				}
			} catch (Exception e) {
				e.printStackTrace();
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

		return invalid;
	}


}
