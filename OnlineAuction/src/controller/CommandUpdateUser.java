package controller;

import group.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdbc.DBConnectionFactory;

public class CommandUpdateUser implements Command {
	
	private static final String page = "/UserIndex.jsp";
	private static final String login = "/controller?action=loginPage";

	public CommandUpdateUser() {
		super();
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {

		if (!Controller.isLoggedIn(request, response))
			return login;

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String userpwd = request.getParameter("txtUpwd");
		String useremail = request.getParameter("txtUemail");
		String fname = request.getParameter("txtFname");
		String lname = request.getParameter("txtLname");
		String yearofbirth = request.getParameter("txtYOB");
		String creditcard = request.getParameter("txtCCN");
		String address = request.getParameter("txtAddress");

		UserBean user = new UserBean();
		request.setAttribute("UserBean",user);
		user.setUsername(username);
		user.setUserpwd(userpwd);
		user.setUseremail(useremail);
		user.setFname(fname);
		user.setLname(lname);
		user.setYearofbirth(yearofbirth);
		user.setFulladdress(address);
		user.setCreditcard(creditcard);		
		if(!user.validate()){
			System.out.println("invalid");
			return page;
		}
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			boolean res = user.canUpdateEmail(conn, useremail);
			if (res == false)
				return page;
			user.updateDatabase(conn);
		}catch  (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return page;
	}


}
