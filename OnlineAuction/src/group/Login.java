package group;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		String msg = " ";
		
		Connection conn = null;
		String url = "jdbc:postgresql://localhost:5432/JDBC";
		String driver = "org.postgresql.Driver";
		String username = "postgres";
		String password = "1234";
		
		
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url,username, password);
			if(conn!=null){System.out.println("fuck!!");}
			String sqlQuery = "select * from user_info where username =  '"+un+"' and userpwd = '"+pw+"'";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			if(rs.next()){
				msg = "hello " + un +" suc!";
			}
			else {
				msg = "hello "+un+" failed";
			}
			st.close();
			rs.close();
		}catch  (Exception e) {
			e.printStackTrace();
		}
		/*if(un.equals(username)&&pw.equals(password)) {
		msg = "hello" + un +"suc!";
		}
		else {
			msg = "failed";
		}*/
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<font size = '6' color =red>"+msg+"</font>");
	}

}
