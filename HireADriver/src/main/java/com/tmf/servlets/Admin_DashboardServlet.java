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

@WebServlet("/Admin_DashboardServlet")
public class Admin_DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Admin_DashboardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		BookingDAO dao = new BookingDAOImpl();

		List<Booking> bookings = dao.getAllBookings();

		for (Booking b : bookings) {

			response.getWriter().println("Booking ID: " + b.getBookingId() + " Status: " + b.getStatus() + "<br>");

		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}