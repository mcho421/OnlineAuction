package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedInputStream bis = null;
		BufferedOutputStream output = null;
		try {
			String fileName = request.getParameter("image");             
			if (fileName.indexOf("/") > -1 || fileName.indexOf("\\") > -1) {
				throw new RuntimeException();
			}
			File baseDir = new File(getServletContext().getRealPath("/"));
			File imgDir = new File(baseDir, "image");
			File imgFile = new File(imgDir, fileName);

			FileInputStream fis = new FileInputStream(imgFile);
			bis = new BufferedInputStream(fis);             
			response.setContentType(URLConnection.guessContentTypeFromName(fileName));
			output = new BufferedOutputStream(response.getOutputStream());
			for (int data; (data = bis.read()) > -1;) {
				output.write(data);
			}             
		} catch(IOException e){
//			e.printStackTrace();
			System.out.println("file not found");
		} catch(RuntimeException e){
			System.out.println("no dodgy stuff please");
		}finally{
			if (output != null) 
				output.close();
            if (bis != null)
            	bis.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
