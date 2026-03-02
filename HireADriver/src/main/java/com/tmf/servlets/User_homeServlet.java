package com.tmf.servlets;

import java.io.IOException;
import java.util.List;

import com.tmf.servlets.dao.BookingDAO;
import com.tmf.servlets.dao.BookingDAOImpl;
import com.tmf.servlets.dao.UserDAO;
import com.tmf.servlets.dao.UserDAOImpl;
import com.tmf.servlets.entity.Booking;
import com.tmf.servlets.entity.User;

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

		response.setContentType("text/html");

		HttpSession session = request.getSession(false);

		if (session == null || !"CUSTOMER".equals(session.getAttribute("userType"))) {
			response.sendRedirect("login.html");
			return;
		}

		int userId = (int) session.getAttribute("userId");
		String action = request.getParameter("action");

		try {

			UserDAO userDAO = new UserDAOImpl();
			BookingDAO bookingDAO = new BookingDAOImpl();

			User user = userDAO.getUserById(userId);

			StringBuffer sb = new StringBuffer();

			sb.append("<html><head>");
			sb.append("<title>User Home</title>");
			sb.append("</head><body>");

			sb.append("<h2>Welcome " + user.getFirstName() + "</h2>");

// 🔹 PROFILE SECTION
			sb.append("<h3>My Profile</h3>");
			sb.append("<p><b>Name:</b> " + user.getFirstName() + " " + user.getLastName() + "</p>");
			sb.append("<p><b>Email:</b> " + user.getEmail() + "</p>");
			sb.append("<p><b>Phone:</b> " + user.getPhoneNumber() + "</p>");

			sb.append("<a href='User_homeServlet?action=edit'>Update Profile</a>");
			sb.append("<br><br>");

// 🔹 EDIT PROFILE
			if ("edit".equals(action)) {

				sb.append("<h3>Edit Profile</h3>");
				sb.append("<form method='post'>");

				sb.append("First Name:<br>");
				sb.append("<input type='text' name='first_name' value='" + user.getFirstName() + "' required><br><br>");

				sb.append("Last Name:<br>");
				sb.append("<input type='text' name='last_name' value='" + user.getLastName() + "' required><br><br>");

				sb.append("Phone:<br>");
				sb.append("<input type='text' name='phone_number' value='" + user.getPhoneNumber()
						+ "' required><br><br>");

				sb.append("<button type='submit'>Update</button>");
				sb.append("</form>");
			}

// 🔹 BOOKING HISTORY
			sb.append("<h3>Previous Bookings</h3>");

			List<Booking> bookings = bookingDAO.getBookingsByCustomer(userId);

			sb.append("<table border='1' cellpadding='10'>");
			sb.append("<tr>");
			sb.append("<th>Booking ID</th>");
			sb.append("<th>Source</th>");
			sb.append("<th>Destination</th>");
			sb.append("<th>Status</th>");
			sb.append("</tr>");

			for (Booking b : bookings) {
				sb.append("<tr>");
				sb.append("<td>" + b.getBookingId() + "</td>");
				sb.append("<td>" + b.getSource() + "</td>");
				sb.append("<td>" + b.getDestination() + "</td>");
				sb.append("<td>" + b.getStatus() + "</td>");
				sb.append("</tr>");
			}

			sb.append("</table>");

			sb.append("<br><br>");
			sb.append("<a href='LogoutServlet'>Logout</a>");

			sb.append("</body></html>");

			response.getWriter().println(sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Error loading user dashboard");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);
		int userId = (int) session.getAttribute("userId");

		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String phone = request.getParameter("phone_number");

		try {

			UserDAO userDAO = new UserDAOImpl();
			userDAO.updateProfile(userId, firstName, lastName, phone);

			response.sendRedirect("User_homeServlet");

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Profile Update Failed");
		}
	}
}