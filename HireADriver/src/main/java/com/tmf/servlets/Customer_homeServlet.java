package com.tmf.servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

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

		UserDAO userDAO = new UserDAOImpl();
		CustomerDAO customerDAO = new CustomerDAOImpl();
		BookingDAO bookingDAO = new BookingDAOImpl();

		try {

			User user = userDAO.getUserById(userId);

			List<Trip> trips = customerDAO.getTripsByCustomer(userId);

			List<Booking> bookings = bookingDAO.getBookingsByCustomer(userId);

			request.setAttribute("user", user);
			request.setAttribute("trips", trips);
			request.setAttribute("bookingsList", bookings);

			request.getRequestDispatcher("Customer.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);

		if (session == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		int userId = (int) session.getAttribute("userId");

		String action = request.getParameter("action");

		CustomerDAO customerDAO = new CustomerDAOImpl();

		try {

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

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect("Customer_homeServlet");
	}
}