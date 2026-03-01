package com.tmf.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

//import com.tmf.servlets.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Admin_DashboardServlet")
public class Admin_DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Admin_DashboardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	    protected void doGet(HttpServletRequest request,
	                         HttpServletResponse response)
	            throws IOException {

	        HttpSession session = request.getSession(false);

	        if (session == null ||
	            !"ADMIN".equals(session.getAttribute("userType"))) {
	            response.sendRedirect("login.html");
	            return;
	        }

	        try {
	        	Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection(
	                    "jdbc:mysql://localhost:3306/hireadriver",
	                    "root",
	                    "Hima@@491"
	            );
	            PrintWriter out = response.getWriter();

	            ResultSet rs = con.createStatement().executeQuery(
	                    "SELECT booking_id,status FROM bookings");

	            out.println("<h2>All Bookings</h2>");
	            while (rs.next()) {
	                out.println("Booking ID: " +
	                        rs.getInt("booking_id") +
	                        " | Status: " +
	                        rs.getString("status") + "<br>");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	}