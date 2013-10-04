package controller;

import group.Item;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.DBConnectionFactory;

public class CommandHaltItem implements Command {
	private static final String success = "/controller?action=adminitemspage";
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		Connection conn = null;
		int id = Integer.parseInt(request.getParameter("itemid"));		
	try{
		conn = DBConnectionFactory.getConnection();
		if(Item.HaltItem(conn, id)) {
			request.setAttribute("msg", "halted successfully");
			return success;
		}
		else {
			request.setAttribute("msg", "halted successfully");
			return success;
		}
	}catch (Exception e) {
		e.printStackTrace();
		request.setAttribute("msg", "halted successfully");
		return success;
	} finally{
		if(conn!=null) 
		{
		conn.close();
		System.out.println("conn closed");
		}
	}
	}

}
