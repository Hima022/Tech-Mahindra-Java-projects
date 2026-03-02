package com.tmf.servlets;
import java.io.IOException;
import java.util.List;

import com.tmf.servlets.dao.BookingDAO;
import com.tmf.servlets.dao.BookingDAOImpl;
import com.tmf.servlets.dao.RoleDAO;
import com.tmf.servlets.dao.RoleDAOImpl;
import com.tmf.servlets.dao.UserDAO;
import com.tmf.servlets.dao.UserDAOImpl;
import com.tmf.servlets.entity.Booking;
import com.tmf.servlets.entity.Driver;
import com.tmf.servlets.entity.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Driver_homeServlet")
public class Driver_homeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Driver_homeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	    protected void doGet(HttpServletRequest request,
	                         HttpServletResponse response)
	            throws IOException {

	        response.setContentType("text/html");

	        HttpSession session = request.getSession(false);

	        if (session == null ||
	            !"DRIVER".equals(session.getAttribute("userType"))) {
	            response.sendRedirect("login.html");
	            return;
	        }

	        int userId = (int) session.getAttribute("userId");
	        String action = request.getParameter("action");

	        try {

	            UserDAO userDAO = new UserDAOImpl();
	            RoleDAO roleDAO = new RoleDAOImpl();
	            BookingDAO bookingDAO = new BookingDAOImpl();

	            User user = userDAO.getUserById(userId);
	            Driver driver = roleDAO.getDriverByUserId(userId);
	            List<Booking> bookings =
	                    bookingDAO.getBookingsByDriver(userId);

	            if (user == null) {
	                response.getWriter().println("User not found.");
	                return;
	            }

	            StringBuilder sb = new StringBuilder();

	            sb.append("<html><head>");
	            sb.append("<title>Driver Dashboard</title>");
	            sb.append("<style>");
	            sb.append("body{font-family:Arial;padding:30px;background:#f5f5f5;}");
	            sb.append("table{border-collapse:collapse;width:100%;}");
	            sb.append("th,td{border:1px solid #ccc;padding:8px;text-align:center;}");
	            sb.append("th{background:#1e3c72;color:white;}");
	            sb.append(".card{background:white;padding:20px;margin-bottom:20px;border-radius:8px;}");
	            sb.append("</style>");
	            sb.append("</head><body>");

	            // 🔹 Welcome
	            sb.append("<div class='card'>");
	            sb.append("<h2>Welcome ").append(user.getFirstName()).append("</h2>");
	            sb.append("</div>");

	            // 🔹 Profile
	            sb.append("<div class='card'>");
	            sb.append("<h3>My Profile</h3>");
	            sb.append("<p><b>Name:</b> ")
	              .append(user.getFirstName()).append(" ")
	              .append(user.getLastName()).append("</p>");
	            sb.append("<p><b>Email:</b> ")
	              .append(user.getEmail()).append("</p>");
	            sb.append("<p><b>Phone:</b> ")
	              .append(user.getPhoneNumber()).append("</p>");

	            sb.append("<p><b>License:</b> ");

	            if (driver == null || driver.getLicenseNumber() == null) {
	                sb.append("Not Submitted");
	            } else {
	                sb.append(driver.getLicenseNumber());
	            }

	            sb.append("</p>");
	            sb.append("<a href='Driver_homeServlet?action=edit'>Edit Profile</a>");
	            sb.append("</div>");

	            // 🔹 Edit Profile
	            if ("edit".equals(action)) {

	                sb.append("<div class='card'>");
	                sb.append("<h3>Edit Profile</h3>");
	                sb.append("<form method='post'>");

	                sb.append("First Name:<br>");
	                sb.append("<input type='text' name='first_name' value='")
	                  .append(user.getFirstName())
	                  .append("' required><br><br>");

	                sb.append("Last Name:<br>");
	                sb.append("<input type='text' name='last_name' value='")
	                  .append(user.getLastName())
	                  .append("' required><br><br>");

	                sb.append("Phone:<br>");
	                sb.append("<input type='text' name='phone_number' value='")
	                  .append(user.getPhoneNumber())
	                  .append("' required><br><br>");

	                sb.append("<button type='submit'>Update</button>");
	                sb.append("</form>");
	                sb.append("</div>");
	            }

	            // 🔹 Upload Document
	            sb.append("<div class='card'>");
	            sb.append("<h3>Upload License Document</h3>");
	            sb.append("<form action='UploadDocumentServlet' method='post' enctype='multipart/form-data'>");
	            sb.append("<input type='file' name='document' required><br><br>");
	            sb.append("<button type='submit'>Upload</button>");
	            sb.append("</form>");
	            sb.append("</div>");

	            // 🔹 Booking History
	            sb.append("<div class='card'>");
	            sb.append("<h3>My Bookings</h3>");

	            if (bookings == null || bookings.isEmpty()) {
	                sb.append("<p>No bookings found.</p>");
	            } else {

	                sb.append("<table>");
	                sb.append("<tr>");
	                sb.append("<th>ID</th>");
	                sb.append("<th>Source</th>");
	                sb.append("<th>Destination</th>");
	                sb.append("<th>Status</th>");
	                sb.append("</tr>");

	                for (Booking b : bookings) {
	                    sb.append("<tr>");
	                    sb.append("<td>").append(b.getBookingId()).append("</td>");
	                    sb.append("<td>").append(b.getSource()).append("</td>");
	                    sb.append("<td>").append(b.getDestination()).append("</td>");
	                    sb.append("<td>").append(b.getStatus()).append("</td>");
	                    sb.append("</tr>");
	                }

	                sb.append("</table>");
	            }

	            sb.append("</div>");
	            sb.append("<br><a href='LogoutServlet'>Logout</a>");
	            sb.append("</body></html>");

	            response.getWriter().println(sb.toString());

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.getWriter().println("Error loading driver dashboard.");
	        }
	    }

	    protected void doPost(HttpServletRequest request,
	                          HttpServletResponse response)
	            throws IOException {

	        HttpSession session = request.getSession(false);
	        if (session == null) {
	            response.sendRedirect("login.html");
	            return;
	        }

	        int userId = (int) session.getAttribute("userId");

	        String firstName = request.getParameter("first_name");
	        String lastName = request.getParameter("last_name");
	        String phone = request.getParameter("phone_number");

	        try {

	            UserDAO userDAO = new UserDAOImpl();
	            userDAO.updateProfile(userId, firstName, lastName, phone);

	            response.sendRedirect("Driver_homeServlet");

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.getWriter().println("Profile update failed.");
	        }
	    }
	}