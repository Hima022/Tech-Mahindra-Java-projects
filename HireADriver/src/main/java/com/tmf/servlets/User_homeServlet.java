package com.tmf.servlets;

import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//import com.tmf.servlets.entity.User;
@WebServlet("/User_homeServlet")
public class User_homeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public User_homeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);

		if (session == null || !"CUSTOMER".equals(session.getAttribute("userType"))) {
			response.sendRedirect("login.html");
			return;
		}

		int userId = (int) session.getAttribute("userId");
		String action = request.getParameter("action");

		String firstName = "";
		String lastName = "";
		String phone = "";

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hireadriver", "root",
					"Hima@@491");

			PreparedStatement ps = con
					.prepareStatement("SELECT first_name, last_name, phone_number FROM users WHERE user_id=?");

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				firstName = rs.getString("first_name");
				lastName = rs.getString("last_name");
				phone = rs.getString("phone_number");
			}

			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setContentType("text/html");

		// 🔹 If edit button clicked
		if ("edit".equals(action)) {

			response.getWriter().append("<h2>Edit Profile</h2>" + "<form method='post'>" +

					"First Name: <input type='text' name='first_name' value='" + firstName + "' required><br><br>"
					+ "Last Name: <input type='text' name='last_name' value='" + lastName + "' required><br><br>"
					+ "Phone: <input type='text' name='phone_number' value='" + phone + "' required><br><br>" +

					"<button type='submit'>Save Changes</button>" + "</form><br>" +

					"<button onclick=\"location.href='User_homeServlet'\">Cancel</button>");

			return;
		}
		 if ("history".equals(action)) {

		        response.getWriter().append(
		            "<h2>Previous Bookings</h2>" +
		            "<table border='1' cellpadding='10'>" +
		            "<tr><th>ID</th><th>Date</th><th>Pickup</th><th>Drop</th><th>Status</th></tr>" +
		            "<tr><td>101</td><td>2026-02-20</td><td>Hyderabad</td><td>Airport</td><td>COMPLETED</td></tr>" +
		            "<tr><td>102</td><td>2026-02-25</td><td>Gachibowli</td><td>Secunderabad</td><td>CANCELLED</td></tr>" +
		            "</table><br>" +
		            "<button onclick=\"location.href='User_homeServlet'\">Back</button>"
		        );
		        return;
		    }

		// 🔹 Normal Dashboard
		response.getWriter().append("<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ "    <title>User Home - Hire A Driver</title>\r\n"
				+ "    <link rel=\"stylesheet\" href=\"UserStyle.css\">\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "\r\n"
				+ "<div class=\"navbar\">\r\n"
				+ "    <h2>Hire A Driver</h2>\r\n"
				+ "    <button onclick=\"location.href='logoutServlet'\">Logout</button>\r\n"
				+ "</div>\r\n"
				+ "\r\n"
				+ "<div class=\"welcome-section\">\r\n"
				+ "    <h2>Welcome, "+ firstName +"</h2>\r\n"
				+ "</div>\r\n"
				+ "\r\n"
				+ "<div class=\"container\">\r\n"
				+ "\r\n"
				+ "    <div class=\"card\">\r\n"
				+ "        <h3>Book a Driver</h3>\r\n"
				+ "        <p>Search and hire a driver instantly.</p>\r\n"
				+ "        <button onclick=\"location.href='BookDriverServlet'\">Book Now</button>\r\n"
				+ "    </div>\r\n"
				+ "\r\n"
				+ "    <div class=\"card\">\r\n"
				+ "        <h3>My Bookings</h3>\r\n"
				+ "        <p>View your previous bookings.</p>\r\n"
				+"        <button onclick=\"location.href='User_homeServlet?action=history'\">View History\r\n"
				+ "    </div>\r\n"
				+ "\r\n"
				+ "    <div class=\"card\">\r\n"
				+ "        <h3>Update Profile</h3>\r\n"
				+ "        <p>Edit your personal details.</p>\r\n"
				+ "        <button onclick=\"location.href='User_homeServlet?action=edit'\">\r\n"
				+ "            Update Profile\r\n"
				+ "        </button>\r\n"
				+ "    </div>\r\n"
				+ "\r\n"
				+ "</div>\r\n"
				+ "\r\n"
				+ "</body>\r\n"
				+ "</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);
		int userId = (int) session.getAttribute("userId");

		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String phone = request.getParameter("phone_number");

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hireadriver", "root",
					"Hima@@491");

			PreparedStatement ps = con
					.prepareStatement("UPDATE users SET first_name=?, last_name=?, phone_number=? WHERE user_id=?");

			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, phone);
			ps.setInt(4, userId);

			ps.executeUpdate();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect("User_homeServlet");
	}
}