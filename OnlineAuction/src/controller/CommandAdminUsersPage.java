
package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandAdminUsersPage implements Command {
	private static final String page = "/WEB-INF/AdminUsers.jsp";
	public CommandAdminUsersPage() {
		super();
	}
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		return page;
	}

}