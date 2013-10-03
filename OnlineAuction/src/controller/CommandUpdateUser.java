package controller;

import group.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdbc.DBConnectionFactory;

public class CommandUpdateUser implements Command {
	
	private static final String page = "/UserIndex.jsp";

	public CommandUpdateUser() {
		super();
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

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
		try {
			Connection conn = DBConnectionFactory.getConnection();
			String sqlQuery = "select * from Users where email = ?";
			PreparedStatement st = conn.prepareStatement(sqlQuery);
			st.setString(1, useremail);
			ResultSet rs = st.executeQuery();
			if(rs.next()){
				String un = rs.getString(2);
				System.out.println(un);
				System.out.println(username);
				if(!un.equals(username)) {
			    System.out.println("buxiangdeng");
				user.setErrorMsg("email", "Sorry. This email address is already in use");
				st.close();
				rs.close();
				System.out.println("invalid");
				return page;
				}
			}
			st.close();
			rs.close();
		}catch  (Exception e) {
			e.printStackTrace();
		}
		try {
			Connection conn = DBConnectionFactory.getConnection();
			String sqlQuery = "UPDATE Users SET password=?, email=?,fname=?, lname=?, yearofbirth=?, fulladdress=?, creditcard=? WHERE username=?";
			PreparedStatement st = conn.prepareStatement(sqlQuery);
			st.setString(1, userpwd);
			st.setString(2, useremail);
			st.setString(3, fname);
			st.setString(4, lname);
			st.setString(5, yearofbirth);
			st.setString(6, address);
			st.setString(7, creditcard);
			st.setString(8, username);
			st.executeUpdate();
			st.close();
		}catch  (Exception e) {
			e.printStackTrace();
		}
		return page;
	}


}
