package com.tmf.servlets;

import java.io.IOException;

import com.tmf.servlets.dao.BookingDAO;
import com.tmf.servlets.dao.BookingDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/CreateBookingServlet")
public class CreateBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateBookingServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request,
	                          HttpServletResponse response)
	            throws ServletException, IOException {

	        HttpSession session = request.getSession(false);

	        if (session == null) {
	            response.sendRedirect("login.jsp");
	            return;
	        }

	        int customerId = (int) session.getAttribute("userId");

	        int tripId =
	                Integer.parseInt(request.getParameter("tripId"));

	        int driverId =
	                Integer.parseInt(request.getParameter("driverId"));

	        BookingDAO bookingDAO = new BookingDAOImpl();

	        bookingDAO.createBooking(tripId, customerId, driverId);

	        response.sendRedirect("Customer_homeServlet");
	    }
	}