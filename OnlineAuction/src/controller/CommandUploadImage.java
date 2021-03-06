package controller;

import group.DBconn;
import group.Item;
import group.UserBean;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import jdbc.DBConnectionFactory;

public class CommandUploadImage implements Command {
	
	private static final String invalid = "/UploadImage.jsp";
	private static final String success = "/UserIndex.jsp";

	public CommandUploadImage() {
		super();
	}

	@Override
	public String execute(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		  //RequestDispatcher rd = null;
		  File tempdir = null;
		  HttpSession session = request.getSession();
		  String username = (String) session.getAttribute("username");
		  Item newitem = (Item) session.getAttribute("Item");
		  //if(request.getAttribute("Item")!=null) newitem = (Item) request.getAttribute("Item");
		  String path = request.getParameter("path");
		  String tmpPath = path+"image";
		  String picture = "";
		  tempdir = new File(tmpPath);
	      if(!tempdir.isDirectory())
	          tempdir.mkdir();		  
			if(ServletFileUpload.isMultipartContent(request)){
			   DiskFileItemFactory factory = new DiskFileItemFactory();
			   factory.setRepository(new File(path+"image\\"));
			   ServletFileUpload upload = new ServletFileUpload(factory); 
			   upload.setSizeMax(1*1024*1024);
			   List items = new ArrayList();
			    try {
			     items = upload.parseRequest(request);
			    } catch (FileUploadException e) {
			     System.out.println(e.getMessage());
			    }
			   Iterator it = items.iterator();
			   while(it.hasNext()){
			    FileItem fileItem = (FileItem) it.next();
			    if(!fileItem.isFormField()){   
			     if(fileItem.getName()!=null && fileItem.getSize()!=0){
			      File fullFile = new File(fileItem.getName());
			      long time = System.currentTimeMillis();
			      String uploadtime = String.valueOf(time);
			      File newFile = new File(path+"image\\" + username+uploadtime+fullFile.getName());
			      picture=path+"image\\" + username+uploadtime+fullFile.getName();
			      System.out.println(picture);
			      try {
			        fileItem.write(newFile);
			        System.out.println("cunshangle");
					newitem.setImageurl(picture);
					newitem.Insert(username);
					request.setAttribute("Item", newitem);
					UserBean user = new UserBean();
					user.Initialize(username);
					request.setAttribute("UserBean", user);
					return success;
					//rd=request.getRequestDispatcher("/UserIndex.jsp");
					//rd.forward(request, response);
			      } catch (Exception e) {
			       e.printStackTrace();
			       newitem.setErrorMsg("picture", e.getMessage());
			       request.setAttribute("Item", newitem);
			       //rd=request.getRequestDispatcher("/UploadImage.jsp");
				   //rd.forward(request, response);
			       return invalid;
			      }
			     }
			    }

			   }
			}
			return invalid;

	}


}
