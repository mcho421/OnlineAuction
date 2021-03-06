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
import javax.servlet.ServletContext;
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
        commandMap.put("offeritem", new CommandOfferItem());
        commandMap.put("uploadimage", new CommandUploadImage());
        commandMap.put("userIndexPage", new CommandUserIndexPage());
        commandMap.put("search", new CommandSearch());
        commandMap.put("banuser", new CommandBanUser());
        commandMap.put("haltitem", new CommandHaltItem());
        commandMap.put("itemPage", new CommandItemPage());
        commandMap.put("confirmBid", new CommandConfirmBid());
        commandMap.put("adminpage", new CommandAdminPage());
        commandMap.put("adminuserspage", new CommandAdminUsersPage());
        commandMap.put("adminitemspage", new CommandAdminItemsPage());
        commandMap.put("message", new CommandMessage());
        commandMap.put("messagepage", new CommandMessagePage());
        commandMap.put("deletemsg", new CommandDeleteMsg());
        commandMap.put("acceptRejectBid", new CommandAcceptRejectBid());
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
		//if(action==null) action = "";
		if(!isAdmin(request, response)){
		String page = "/WEB-INF/error.jsp";
		System.out.println("not admin");
		//String action = request.getParameter("action");
		if(action==null) {
			Command command = commandMap.get(action);
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
		}
		else if(!action.equals("adminpage")&&!action.equals("adminuserspage")&&!action.equals("adminitemspage")&&!action.equals("banuser")&&!action.equals("haltitem")) {
		Command command = commandMap.get(action);
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
		}
		else{
			request.setAttribute("errorMsg", "Invalid action '" + action + "'.");
			page = "/WEB-INF/error.jsp";
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
		dispatcher.forward(request, response);}
		else {
			String page="/WEB-INF/error.jsp";
			System.out.println("admin login");
			if(action==null) {
				Command command = commandMap.get(action);
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
			}
			else if(action.equals("adminpage")||action.equals("adminuserspage")||action.equals("adminitemspage")||action.equals("banuser")||action.equals("haltitem")
				||action.equals("logout")||action.equals("userIndexPage")) {
			Command command = commandMap.get(action);
			page = "/WEB-INF/error.jsp";
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
			}
			else {
				request.setAttribute("errorMsg", "Invalid action '" + action + "'.");
				page = "/WEB-INF/error.jsp";
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
			dispatcher.forward(request, response);
		}
	}
	
	public static boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null) {
			return false;
		}
		return true;
	}
	public static boolean isAdmin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute("admin") == null) {
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
	
	public static String getBaseUrl() {
		return "http://localhost:8080/OnlineAuction/";
	}

}
