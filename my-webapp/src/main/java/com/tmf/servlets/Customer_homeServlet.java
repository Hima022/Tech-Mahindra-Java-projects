package com.tmf.servlets;

import java.io.IOException;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.tmf.servlets.dao.*;
import com.tmf.servlets.entity.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

@MultipartConfig
@WebServlet("/Customer_homeServlet")
public class Customer_homeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Customer_homeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("userId") == null
				|| !"CUSTOMER".equals(session.getAttribute("userType"))) {

			response.sendRedirect("login.jsp");
			return;
		}

		int userId = (int) session.getAttribute("userId");
		String action = request.getParameter("action");

		UserDAO userDAO = new UserDAOImpl();
		CustomerDAO customerDAO = new CustomerDAOImpl();
		BookingDAO bookingDAO = new BookingDAOImpl();

		try {

			User user = userDAO.getUserById(userId);
			request.setAttribute("user", user);

			// 👉 PROFILE PAGE
			if ("profile".equals(action)) {
				request.getRequestDispatcher("profile.jsp").forward(request, response);
				return;
			}

			List<Trip> trips = customerDAO.getTripsByCustomer(userId);
			List<Booking> bookings = bookingDAO.getBookingsByCustomer(userId);

			request.setAttribute("trips", trips);
			request.setAttribute("bookingsList", bookings);

			request.getRequestDispatcher("Customer.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession(false);

		if (session == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		int userId = (int) session.getAttribute("userId");
		String action = request.getParameter("action");

		CustomerDAO customerDAO = new CustomerDAOImpl();
		UserDAO userDAO = new UserDAOImpl();

		try {

			// ✅ CREATE TRIP
			if ("createTrip".equals(action)) {

				Trip trip = new Trip();

				trip.setCustomerId(userId);
				trip.setSource(request.getParameter("source"));
				trip.setDestination(request.getParameter("destination"));

				trip.setStartDate(Timestamp.valueOf(request.getParameter("start_date") + " 00:00:00"));
				trip.setEndDate(Timestamp.valueOf(request.getParameter("end_date") + " 00:00:00"));

				trip.setDurationHrs(Integer.parseInt(request.getParameter("duration")));
				trip.setPrice(Double.parseDouble(request.getParameter("price")));

				customerDAO.createTrip(trip);
			}

			// ✅ UPDATE PROFILE (🔥 THIS IS WHAT YOU NEEDED)
			else if ("updateProfile".equals(action)) {

				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String phone = request.getParameter("phone");
				int age = Integer.parseInt(request.getParameter("age"));

				String password = request.getParameter("password"); // optional

				Part filePart = request.getPart("profileImage");
				String fileName = filePart.getSubmittedFileName();

				String imagePath = null;

				// 👉 Save image if uploaded
				if (fileName != null && !fileName.isEmpty()) {

					String uploadPath = getServletContext().getRealPath("") + "images";
					File uploadDir = new File(uploadPath);

					if (!uploadDir.exists()) {
						uploadDir.mkdir();
					}

					filePart.write(uploadPath + "/" + fileName);
					imagePath = fileName;
				}

				if (imagePath != null) {
				    userDAO.updateProfile(userId, name, email, phone, age, password, imagePath);
				} else {
				    userDAO.updateProfileWithoutImage(userId, name, email, phone, age, password);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect("Customer_homeServlet?action=profile&success=updated");
	}
}