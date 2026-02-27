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

//import com.tmf.servlets.entity.User;
@WebServlet("/User_homeServlet")
public class User_homeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public User_homeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String userType = (String) session.getAttribute("userType");

        if (!"CUSTOMER".equals(userType)) {
            response.sendRedirect("login.html");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<title>User Home</title>");
        out.println("<link rel='stylesheet' href='UserStyle.css'>");
        out.println("</head><body>");

        out.println("<div class='navbar'>");
        out.println("<h2>Hire A Driver</h2>");
        out.println("<button onclick=\"location.href='LogoutServlet'\">Logout</button>");
        out.println("</div>");

        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hireadriver",
                    "root",
                    "Hima@@491"
            );

            // Fetch Profile
            String profileSql = "SELECT * FROM users WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(profileSql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<h3>Profile</h3>");
                out.println("Name: " + rs.getString("first_name") + " "
                        + rs.getString("last_name") +" " 
                                + rs.getString("uname") +"<br>");
                out.println("Email: " + rs.getString("email") + "<br>");
                out.println("Phone: " + rs.getString("phone_number") + "<br>");
                out.println("DOB: " + rs.getDate("dob") + "<br><br>");
            }

            // Fetch Previous Bookings
            String bookingSql = """
                SELECT b.booking_id, b.booking_date, b.pickup_location,
                       b.drop_location, b.status
                FROM bookings b
                JOIN customers c ON b.customer_id = c.customer_id
                WHERE c.user_id = ?
            """;

            PreparedStatement bps = con.prepareStatement(bookingSql);
            bps.setInt(1, userId);
            ResultSet brs = bps.executeQuery();

            out.println("<h3>Previous Bookings</h3>");

            boolean hasBookings = false;

            while (brs.next()) {
                hasBookings = true;
                out.println("Booking ID: " + brs.getInt("booking_id")
                        + " | Date: " + brs.getDate("booking_date")
                        + " | From: " + brs.getString("pickup_location")
                        + " | To: " + brs.getString("drop_location")
                        + " | Status: " + brs.getString("status")
                        + "<br>");
            }

            if (!hasBookings) {
                out.println("No bookings found.<br>");
            }

            // Update Profile Button
            out.println("<br><button onclick=\"location.href='UpdateProfile.html'\">Update Profile</button>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error loading data.");
        }

        out.println("</body></html>");
    }
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	doGet(request, response);
}
}