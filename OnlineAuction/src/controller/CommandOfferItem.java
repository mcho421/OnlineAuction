package controller;

import group.DBconn;
import group.Item;
import group.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdbc.DBConnectionFactory;

public class CommandOfferItem implements Command {
	
	private static final String invalid = "/OfferItem.jsp";
	private static final String success = "/UploadImage.jsp";

	public CommandOfferItem() {
		super();
	}

	@Override
	public String execute(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		Item newitem = new Item();
		HttpSession session = request.getSession();
		//RequestDispatcher rd = null;
		newitem.setTitle(request.getParameter("title"));
		newitem.setCategory(Integer.parseInt(request.getParameter("category")));
		System.out.println(request.getParameter("category"));
		newitem.setImageurl(request.getParameter("picture"));
		newitem.setDescription(request.getParameter("description"));
		newitem.setPostage(request.getParameter("postage"));
		String rprice = request.getParameter("rprice");
		String sprice = request.getParameter("sprice");
		String bprice = request.getParameter("rprice");
		newitem.setRprice(Integer.parseInt(rprice));
		newitem.setSprice(Integer.parseInt(sprice));
		newitem.setBincre(Integer.parseInt(bprice));
		int ctime = Integer.parseInt(request.getParameter("closingtime"));
		if(ctime == 1){
			long retryDate = System.currentTimeMillis();
	        int sec = 3600;
	        Timestamp original = new Timestamp(retryDate);
	        Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(original.getTime());
	        cal.add(Calendar.SECOND, sec);
	        Timestamp later = new Timestamp(cal.getTime().getTime());
		    newitem.setCtime(later);
		}
		if(ctime == 2){
			long retryDate = System.currentTimeMillis();
	        int sec = 7200;
	        Timestamp original = new Timestamp(retryDate);
	        Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(original.getTime());
	        cal.add(Calendar.SECOND, sec);
	        Timestamp later = new Timestamp(cal.getTime().getTime());
		    newitem.setCtime(later);
			}
		if(ctime == 3){
			long retryDate = System.currentTimeMillis();
	        int sec = 10800;
	        Timestamp original = new Timestamp(retryDate);
	        Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(original.getTime());
	        cal.add(Calendar.SECOND, sec);
	        Timestamp later = new Timestamp(cal.getTime().getTime());
		    newitem.setCtime(later);
			}
		if(newitem.validate())
		{
			session.setAttribute("Item", newitem);
			//rd=request.getRequestDispatcher("/UploadImage.jsp");
			//rd.forward(request, response);
			return success;
		}
		else {
		request.setAttribute("Item", newitem);
		//rd=request.getRequestDispatcher("/OfferItem.jsp");
		//rd.forward(request, response);
		return invalid;
		}
	}

}