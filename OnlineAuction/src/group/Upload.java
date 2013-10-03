package group;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.fileupload.util.Streams;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	    File tempdir = null;
	  
	  public Upload() {
	    super();
	  }     

	  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request,response);
	  }     
	  
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  RequestDispatcher rd = null;
		  HttpSession session = request.getSession();
		  String username = (String) session.getAttribute("username");
		  Item newitem = (Item) session.getAttribute("Item");
		  //if(request.getAttribute("Item")!=null) newitem = (Item) request.getAttribute("Item");
//		  String path = request.getParameter("path");
//		  String tmpPath = path+"image";
		  File baseDir = new File(getServletContext().getRealPath("/"));
		  File imgDir = new File(baseDir, "image");
//		  String picture = "";
	      if(!imgDir.isDirectory())
	          imgDir.mkdir();		  
			if(ServletFileUpload.isMultipartContent(request)){
			   DiskFileItemFactory factory = new DiskFileItemFactory();
			   factory.setRepository(imgDir);
			   ServletFileUpload upload = new ServletFileUpload(factory); 
			   upload.setSizeMax(1*1024*1024);
			   List items = new ArrayList();
			    try {
			     items = upload.parseRequest(request);
			    } catch (FileUploadException e) {
			     System.out.println(e.getMessage());
			     newitem.setErrorMsg("picture", e.getMessage());
			    }
			   Iterator it = items.iterator();
			   while(it.hasNext()){
			    FileItem fileItem = (FileItem) it.next();
			    if(!fileItem.isFormField()){   
			     if(fileItem.getName()!=null && fileItem.getSize()!=0){

			      //File fullFile = new File(fileItem.getName());
			      long time = System.currentTimeMillis();
			      String uploadtime = String.valueOf(time);
			      File newFile = new File(imgDir, username+uploadtime+"-"+fileItem.getName());
			      //to avoid user upload a same file mutiple times, add the upload time into new file name.
//			      picture=path+"image\\" + username+fullFile.getName();
			      System.out.println(newFile.getName());
			      try {
			        fileItem.write(newFile);
					newitem.setImageurl(newFile.getName());
					newitem.Insert(username);
					request.setAttribute("Item", newitem);
					UserBean user = new UserBean();
					user.Initialize(username);
					request.setAttribute("UserBean", user);
					rd=request.getRequestDispatcher("/UserIndex.jsp");
					rd.forward(request, response);
			      } catch (Exception e) {
			       e.printStackTrace();
			       newitem.setErrorMsg("picture", e.getMessage());
			       request.setAttribute("Item", newitem);
			       rd=request.getRequestDispatcher("/UploadImage.jsp");
				   rd.forward(request, response);
			      }
			     }
			    }

			   }
			}

	  }
	  
}
