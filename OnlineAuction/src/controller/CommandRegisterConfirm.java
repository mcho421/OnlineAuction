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
import group.UserBean;
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
		try {
			try {
				conn = DBConnectionFactory.getConnection();
				boolean res = UserBean.confirmRegistration(conn, request, usermd5);
				if (res == false)
					return errorPage;
			} catch (ServiceLocatorException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return page;
	}


}
