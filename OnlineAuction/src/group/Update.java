package group;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdbc.DBConnectionFactory;

/**
 * Servlet implementation class Update
 */
@WebServlet(description = "update user infomation", urlPatterns = { "/update" })
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String userpwd = request.getParameter("txtUpwd");
		String useremail = request.getParameter("txtUemail");
		String fname = request.getParameter("txtFname");
		String lname = request.getParameter("txtLname");
		String yearofbirth = request.getParameter("txtYOB");
		String creditcard = request.getParameter("txtCCN");
		String address = request.getParameter("txtAddress");
		RequestDispatcher rd = null;		
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
			rd =request.getRequestDispatcher("/UserIndex.jsp");
			rd.forward(request, response);
			return;
		}
		try {

			Connection conn = DBConnectionFactory.getConnection();
			String sqlQuery = "select * from users where email =  '"+useremail+"'";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			if(rs.next()){
				String un = rs.getString(1);
				System.out.println(un);
				System.out.println(username);
				if(!un.equals(username)) {
			    System.out.println("buxiangdeng");
				user.setErrorMsg("email", "Sorry. This email address is already existed");
				st.close();
				rs.close();
				System.out.println("invalid");
				rd =request.getRequestDispatcher("/UserIndex.jsp");
				rd.forward(request, response);
				return;
				}
			}
			st.close();
			rs.close();
		}catch  (Exception e) {
			e.printStackTrace();
		}
		try {
			Connection conn = DBConnectionFactory.getConnection();
			String sqlQuery = "UPDATE users SET password='"+userpwd+"', email='"+useremail+"',fname='"+fname+"', lname='"+lname+"', yearofbirth='"+yearofbirth+"', fulladdress='"+address+"', creditcard='"+creditcard+"'WHERE username='"+username+"';";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			st.close();
			rs.close();
		}catch  (Exception e) {
			e.printStackTrace();
		}
	}
	

}
