package controller;

import group.Message;
import group.UserBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandDeleteMsg implements Command {
	private static final String page = "/controller?action=userIndexPage";
	private static final String valid = "/controller?action=messagepage";
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if(Message.deleteMsg(username)){
			UserBean user = new UserBean();
			user.Initialize(username);
			request.setAttribute("UserBean", user);
			return page;
		}
		else return valid;
	}

}
