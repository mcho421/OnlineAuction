package controller;

import group.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.DBConnectionFactory;

public class CommandBanUser implements Command {
	private static final String success = "/AdminUsers.jsp";
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("userid"));		
		try{
			Connection conn = DBConnectionFactory.getConnection();
			if(UserBean.BanUser(conn, id)) {
				request.setAttribute("msg", "banned successfully");
				return success;
			}
			else {
				request.setAttribute("msg", "banned failed");
				return success;
			}
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "banned failed");
			return success;
		} 
	}

}
