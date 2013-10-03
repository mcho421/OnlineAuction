package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.DBConnectionFactory;
import exceptions.ServiceLocatorException;
import group.Item;

public class CommandItemPage implements Command {
	
	private static final String page = "/ItemPage.jsp";
	private static final String error = "/WEB-INF/error.jsp";

	public CommandItemPage() {
		super();
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {

		String itemIdString = request.getParameter("item");
		int itemId = 0;
		try {
		    itemId = Integer.parseInt(itemIdString);
		} catch ( Exception e ) {
		    itemId = 0;
		}

		Item item = new Item();
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			item = Item.initializeFromId(conn, itemId);
			request.setAttribute("item", item);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "Could not find item id "+itemId);
			return error;
		} finally {
			if (conn != null)
				conn.close();
		}
		return page;
	}


}
