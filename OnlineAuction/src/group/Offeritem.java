package group;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		//HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		//String username = request.getParameter("username");
		newitem.setTitle(request.getParameter("title"));
		//System.out.println(request.getParameter("title"));
		newitem.setCategory(request.getParameter("category"));
		System.out.println(request.getParameter("category"));
		//newitem.setImageurl(request.getParameter("picture"));
		newitem.setDescription(request.getParameter("description"));
		//System.out.println(request.getParameter("description"));
		newitem.setPostage(request.getParameter("postage"));
		//System.out.println(request.getParameter("postage"));
		newitem.setRprice(request.getParameter("rprice"));
		//System.out.println(request.getParameter("rprice"));
		newitem.setSprice(request.getParameter("sprice"));
		//System.out.println(request.getParameter("sprice"));
		newitem.setBincre(request.getParameter("bincre"));
		//System.out.println(request.getParameter("bincre"));
		newitem.setCtime(request.getParameter("ctime"));
		//System.out.println(request.getParameter("ctime"));
		if(newitem.validate())
		{
			request.setAttribute("Item", newitem);
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
