package com.tmf.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/Driver_homeServlet")
public class Driver_homeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Driver_homeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


	        HttpSession session = request.getSession(false);

	        if (session == null || !"DRIVER".equals(session.getAttribute("userType"))) {
	            response.sendRedirect("login.html");
	            return;
	        }

	        int userId = (int) session.getAttribute("userId");

	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();

	        try {
	        	Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection(
	                    "jdbc:mysql://localhost:3306/hireadriver",
	                    "root",
	                    "Hima@@491"
	            );

	            // Profile
	            String sql = """
	                SELECT u.first_name, u.email, d.license_number, d.document_path
	                FROM users u
	                JOIN drivers d ON u.user_id = d.user_id
	                WHERE u.user_id=?
	            """;

	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setInt(1, userId);
	            ResultSet rs = ps.executeQuery();

	            out.println("<h2>Driver Home</h2>");

	            if (rs.next()) {
	                out.println("<h3>Profile</h3>");
	                out.println("Name: " + rs.getString("uname") + "<br>");
	                out.println("Email: " + rs.getString("email") + "<br>");
	                out.println("License: " + rs.getString("license_number") + "<br>");
	                out.println("Uploaded Document: " + rs.getString("document_path") + "<br>");
	            }

	            // Dummy Booking Data
	            out.println("<h3>Previous Bookings</h3>");
	            out.println("Booking ID: 101 | Status: COMPLETED<br>");
	            out.println("Booking ID: 102 | Status: PENDING<br>");

	            // Upload Form
	            out.println("""
	                <h3>Upload Document</h3>
	                <form action="UploadDocumentServlet" method="post" enctype="multipart/form-data">
	                    <input type="file" name="document">
	                    <button type="submit">Upload</button>
	                </form>
	            """);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
