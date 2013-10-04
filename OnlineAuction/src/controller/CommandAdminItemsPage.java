
package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandAdminItemsPage implements Command {
	private static final String page = "/WEB-INF/AdminItems.jsp";
	public CommandAdminItemsPage() {
		super();
	}
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		return page;
	}

}
