package controller;

import java.io.IOException;
import group.Message;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CommandMessage implements Command {

	private static final String success = "/controller?action=messagepage";
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
        List<Message> result = Message.getMsg(username);
        request.setAttribute("result", result);
		return success;
	}

}
