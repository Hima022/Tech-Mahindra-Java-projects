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

		HttpSession session = request.getSession(false);

		if (session == null || !"CUSTOMER".equals(session.getAttribute("userType"))) {
			response.sendRedirect("login.jsp");
			return;
		}

		int userId = (int) session.getAttribute("userId");
		String action = request.getParameter("action");

		try {

			UserDAO userDAO = new UserDAOImpl();
			BookingDAO bookingDAO = new BookingDAOImpl();

			User user = userDAO.getUserById(userId);
			List<Booking> bookings = bookingDAO.getBookingsByCustomer(userId);

// 🔥 Send data to JSP
			request.setAttribute("user", user);
			request.setAttribute("bookings", bookings);
			request.setAttribute("action", action);

			request.getRequestDispatcher("User_home.jsp").forward(request, response);

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