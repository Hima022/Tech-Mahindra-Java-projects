package com.tmf.servlets;

import java.io.IOException;
import java.util.List;

import com.tmf.servlets.dao.BookingDAO;
import com.tmf.servlets.dao.BookingDAOImpl;
import com.tmf.servlets.entity.Booking;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);

		if (session == null || !"ADMIN".equals(session.getAttribute("userType"))) {
			response.sendRedirect("login.html");
			return;
		}

		try {

			BookingDAO bookingDAO = new BookingDAOImpl();
			List<Booking> bookings = bookingDAO.getAllBookings();

			StringBuffer sb = new StringBuffer();
			sb.append("<h2>All Bookings</h2>");

			for (Booking b : bookings) {
				sb.append("Booking ID: ").append(b.getBookingId()).append(" | Status: ").append(b.getStatus())
						.append("<br>");
			}

			response.getWriter().println(sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}