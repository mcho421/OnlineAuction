package group;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Offeritem
 */
@WebServlet("/offeritem")
public class Offeritem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Offeritem() {
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
		Item newitem = new Item();
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		newitem.setTitle(request.getParameter("title"));
		newitem.setCategory(Integer.parseInt(request.getParameter("category")));
		System.out.println(request.getParameter("category"));
		newitem.setImageurl(request.getParameter("picture"));
		newitem.setDescription(request.getParameter("description"));
		newitem.setPostage(request.getParameter("postage"));
		newitem.setRprice(Integer.parseInt(request.getParameter("rprice")));
		newitem.setSprice(Integer.parseInt(request.getParameter("sprice")));
		newitem.setBincre(Integer.parseInt(request.getParameter("bincre")));
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
			rd=request.getRequestDispatcher("/UploadImage.jsp");
			rd.forward(request, response);
		}
		else {
		request.setAttribute("Item", newitem);
		rd=request.getRequestDispatcher("/OfferItem.jsp");
		rd.forward(request, response);
		}
	}

}
