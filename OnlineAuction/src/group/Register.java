package group;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register" )
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
		String username = request.getParameter("txtUname");
		String userpwd = request.getParameter("txtUpwd");
		String useremail = request.getParameter("txtUemail");
		RequestDispatcher rd = null;		
		RegisterForm new_user = new RegisterForm();
		request.setAttribute("RegisterForm",new_user);
		new_user.setUsername(username);
		new_user.setUserpwd(userpwd);
		new_user.setUseremail(useremail);
		if(!new_user.validte()){
			System.out.println("invalid");
			rd =request.getRequestDispatcher("/Register.jsp");
			rd.forward(request, response);
			return;
		}
		Connection conn = null;
		String url = "jdbc:postgresql://localhost:5432/JDBC";
		String driver = "org.postgresql.Driver";
		String dbname = "postgres";
		String password = "1234";
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url,dbname, password);
			String sqlQuery = "select * from user_info where username =  '"+username+"'";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			if(rs.next()){
				new_user.setErrorMsg("name", "Sorry. This username is already existed");
				st.close();
				rs.close();
				System.out.println("invalid");
				rd =request.getRequestDispatcher("/Register.jsp");
				rd.forward(request, response);
				return;
			}
			st.close();
			rs.close();
		}catch  (Exception e) {
			e.printStackTrace();
		}
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url,dbname, password);
			String sqlQuery = "select * from user_info where useremail =  '"+useremail+"'";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			if(rs.next()){
				new_user.setErrorMsg("email", "Sorry. This email address is already existed");
				st.close();
				rs.close();
				System.out.println("invalid");
				rd =request.getRequestDispatcher("/Register.jsp");
				rd.forward(request, response);
				return;
			}
			st.close();
			rs.close();
		}catch  (Exception e) {
			e.printStackTrace();
		}
		//UserBean user = new UserBean();
		//request.setAttribute("user", user);
		//user.setUsername(new_user.getUsername());
		//user.setUserpwd(new_user.getUserpwd());
		//user.setUseremail(new_user.getUseremail());
		Md5 md5 = new Md5();
		String namemd5 ="" ;
		try {
			namemd5= md5.getMD5Str(username);
		} catch (NoSuchAlgorithmException e1) {
			System.out.println("No MD5 generated");
			e1.printStackTrace();
		}
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url,dbname, password);
			String sqlQuery = "INSERT INTO user_info(username, userpwd, useremail,namemd5)VALUES ('"+username+"', '"+userpwd+"', '"+useremail+"', '"+namemd5+"');";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			st.close();
			rs.close();
		}catch  (Exception e) {
			e.printStackTrace();
		}
		/*EmailCfm ec = new EmailCfm();
		try {
			ec.sendVerify(useremail, namemd5);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			System.out.println("unsuc email sent");
			e.printStackTrace();
		}*/
		String msg = "Please login your email account and click the provided URL to confirm registration";
	    request.setAttribute("msg", msg);
		rd =request.getRequestDispatcher("/Regsuc.jsp");
		rd.forward(request, response);
	}

}
