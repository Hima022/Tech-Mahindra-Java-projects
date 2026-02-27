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

	    User admin = (User) request.getSession().getAttribute("loggedinUser");

	    if (admin == null) {
	        response.sendRedirect("login.html");
	        return;
	    }

	    response.setContentType("text/html");

	    response.getWriter().append("<!DOCTYPE html>\n"
	            + "<html>\n"
	            + "<head>\n"
	            + "<title>Admin Dashboard</title>\n"
	            + "</head>\n"
	            + "<body>\n"
	            + "<h2>Welcome Admin</h2>"
	            + admin.getUserName() + "<br/>"
	            + admin.getEmail() + "<br/>"
	            + admin.getContactNo() + "<br/>"
	            + "<br/><a href='logoutServlet'>Logout</a>"
	            + "</body>\n"
	            + "</html>");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	}