package group;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MailVerify
 */
@WebServlet("/MailVerify")
public class MailVerify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailVerify() {
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
		String namemd5=request.getParameter("namemd5");
		System.out.println(namemd5);
		String msg = "";
		Connection conn = null;
		RequestDispatcher rd = null;
		String url = "jdbc:postgresql://localhost:5432/JDBC";
		String driver = "org.postgresql.Driver";
		String username = "postgres";
		String password = "1234";		
		
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url,username, password);
			if(conn!=null){System.out.println("fuck!!");}
			String sqlQuery = "select username from user_info where namemd5 =  '"+namemd5+"'";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			if(rs.next()){
				String sqlQuery2 = "update user_info set confirmation = 'false' where namemd5 =  '"+namemd5+"'";
				rs = st.executeQuery(sqlQuery2);
				msg = "register succesfully!";				
			}
			else {
				msg = "confirmation failed";
			}
			st.close();
			rs.close();
		}catch  (Exception e) {
			e.printStackTrace();
		}
			request.setAttribute("msg",msg);
			rd =request.getRequestDispatcher("/Regsuc.jsp");
			rd.forward(request, response);
			

	}

}
