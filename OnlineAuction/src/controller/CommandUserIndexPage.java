package controller;

import group.UserBean;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandUserIndexPage implements Command {
	
	private static final String page = "/UserIndex.jsp";
	private static final String login = "/controller?action=loginPage";

	public CommandUserIndexPage() {
		super();
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (!Controller.isLoggedIn(request, response))
			return login;
		UserBean user = new UserBean();
		user.Initialize(Controller.getUsername(request, response));
		request.setAttribute("UserBean", user);
		return page;
	}


}
