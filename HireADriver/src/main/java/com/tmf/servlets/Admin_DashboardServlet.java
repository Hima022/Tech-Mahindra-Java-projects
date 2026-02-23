package com.tmf.servlets;

import java.io.IOException;


import com.tmf.servlets.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Admin_DashboardServlet")
public class Admin_DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Admin_DashboardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
	 	User admin = (User)request.getAttribute("loggedinUser");
		System.out.println(request.getParameter("user_name"));
		System.out.println(request.getParameter("password"));

	        response.setContentType("text/html");

	        response.getWriter().append("<!DOCTYPE html>\n"
	        		+ "	            <html>\n"
	        		+ "	            <head>\n"
	        		+ "	                <title>Admin Dashboard - Hire A Driver</title>\n"
	        		+ "	                <link rel=\"stylesheet\" href=\"adminStyle.css\">\n"
	        		+ "	            </head>\n"
	        		+ "	            <body>\n"
	        		+ "	            \n"
	        		+ "	                <div class=\"navbar\">\n"
	        		+ "	                    <h2>Hire A Driver - Admin</h2>\n"
	        		+ "	                    <button onclick=\"location.href='login.html'\">Logout</button>\n"
	        		+ "	                </div>\n"
	        		+ admin.getUserName()+ "<br/>"
	        		+ admin.getEmail()+ "<br/>"
	        		+ admin.getContactNo()+ "<br/>"
	        		+ "				\n"
	        		+ "	                <div class=\"content\">\n"
	        		+ "	                    <h2>Welcome Admin</h2>\n"
	        		+ "	                    <p>Here you can manage the system.</p>\n"
	        		+ "	                </div>\n"
	        		+ "	            </body>\n"
	        		+ "	            </html>\n");
 
	        
	    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	}