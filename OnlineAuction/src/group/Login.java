package group;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

/**
 * Servlet implementation class Login
 */
@WebServlet(description = "Login Service", urlPatterns = { "/login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in service method");
		String un = request.getParameter("username");
		String pw = request.getParameter("password");
		RequestDispatcher rd = null;
		
		String msg = " ";
		try {
			DBconn newdb = new DBconn();
			Connection conn = newdb.getConn();
			if(conn!=null){System.out.println("fuck!!");}
			String sqlQuery = "select * from user_info where username =  '"+un+"' and userpwd = '"+pw+"'";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			if(rs.next()) {
			String username = rs.getString(1);
			String password = rs.getString(2);
			if(un.equals(username)&&pw.equals(password)) {
				//msg = "hello" + un +"suc!";
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				UserBean user = new UserBean();
				user.Initialize(username);
				request.setAttribute("UserBean",user);
				rd =request.getRequestDispatcher("/UserIndex.jsp");
				rd.forward(request, response);
				}
			}
				else {
					msg = "invalid username or password!";
					request.setAttribute("msg", msg);
					rd =request.getRequestDispatcher("/Login.jsp");
					rd.forward(request, response);
				}
			st.close();
			rs.close();
		}catch  (Exception e) {
			e.printStackTrace();
		}

		/*response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<font size = '6' color =red>"+msg+"</font>");*/
	}

}
