package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.ServiceLocatorException;
import jdbc.MailSender;
import exceptions.MailSenderException;
import jdbc.DBConnectionFactory;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(Controller.class.getName());
	private static Map<String, Command> commandMap;
       
    /**
     * @throws ServletException 
     * @see HttpServlet#HttpServlet()
     */
    public Controller() throws ServletException {
        super();
        commandMap = new HashMap<String, Command>();
        commandMap.put(null, new CommandIndexPage());
        commandMap.put("registerPage", new CommandRegisterPage());
        commandMap.put("register", new CommandRegister());
        commandMap.put("regConfirm", new CommandRegisterConfirm());
        commandMap.put("loginPage", new CommandLoginPage());
        commandMap.put("login", new CommandLogin());
        commandMap.put("logout", new CommandLogout());
        commandMap.put("updateUser", new CommandUpdateUser());
        commandMap.put("userIndexPage", new CommandUserIndexPage());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		Command command = commandMap.get(action);
		String page = "/WEB-INF/error.jsp";
		if (command != null) {
			try {
				page = command.execute(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("errorMsg", "Database error");
				page = "/WEB-INF/error.jsp";
			}
		} else {
			request.setAttribute("errorMsg", "Invalid action '" + action + "'.");
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}
	
	public static boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null) {
			return false;
		}
		return true;
	}

	public static String getUsername(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null) {
			return null;
		}
		return (String) session.getAttribute("username");
	}

	public static void invalidateSession(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
	}

}
