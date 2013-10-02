package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.ServiceLocatorException;
import jdbc.DBConnectionFactory;

public class CommandRegisterConfirm implements Command {
	
	private static final String page = "/WEB-INF/registrationConfirm.jsp";
	private static final String errorPage = "/WEB-INF/error.jsp";

	public CommandRegisterConfirm() {
		super();
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String usermd5 = request.getParameter("confirm");
		if (usermd5 == null) {
			request.setAttribute("errorMsg", "Invalid confirmation string.");
			return errorPage;
		}

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			try {
				conn = DBConnectionFactory.getConnection();
				st = conn.prepareStatement("select id from Users where namemd5 = ?");
				st.setString(1, usermd5);
				rs = st.executeQuery();
				int uid = 0;
				if(rs.next()){
					uid = rs.getInt(1);
				} else {
					request.setAttribute("errorMsg", "Invalid confirmation string.");
					return errorPage;
				}
				st.close();
				rs.close();
				st = conn.prepareStatement("UPDATE Users SET confirmed=TRUE where id=?");
				st.setInt(1, uid);
				st.executeUpdate();
			} catch (ServiceLocatorException e) {
				e.printStackTrace();
			} catch (SQLException e) {
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

		return page;
	}


}
