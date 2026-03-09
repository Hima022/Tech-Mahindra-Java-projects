package com.tmf.servlets;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.tmf.servlets.dao.BookingDAO;
import com.tmf.servlets.dao.BookingDAOImpl;
import com.tmf.servlets.dao.DriverDAO;
import com.tmf.servlets.dao.DriverDAOImpl;
import com.tmf.servlets.dao.UserDAO;
import com.tmf.servlets.dao.UserDAOImpl;
import com.tmf.servlets.entity.Booking;

import com.tmf.servlets.entity.Trip;
import com.tmf.servlets.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
@MultipartConfig
@WebServlet("/Driver_homeServlet")
public class Driver_homeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Driver_homeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || !"DRIVER".equals(session.getAttribute("userType"))) {
			response.sendRedirect("login.jsp");
			return;
		}

		int driverId = (int) session.getAttribute("userId");

		try {

			UserDAO userDAO = new UserDAOImpl();
			DriverDAO driverDAO = new DriverDAOImpl();
			BookingDAO bookingDAO = new BookingDAOImpl();

			// Logged-in driver (from users table)
			User driver = userDAO.getUserById(driverId);

			// Live trips available
			List<Trip> trips = driverDAO.getLiveTrips();

			// Driver's bookings
			List<Booking> bookings = bookingDAO.getBookingsByDriver(driverId);

			request.setAttribute("driver", driver);
			request.setAttribute("trips", trips);
			request.setAttribute("bookings", bookings);

			request.getRequestDispatcher("driverhome.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Handle Accept / Reject
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		HttpSession session = request.getSession(false);

		if (session == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		int driverId = (int) session.getAttribute("userId");

		String action = request.getParameter("action");

		DriverDAO dao = new DriverDAOImpl();

		try {

			if ("acceptTrip".equals(action)) {

				int tripId = Integer.parseInt(request.getParameter("tripId"));
				double price = Double.parseDouble(request.getParameter("price"));

				dao.acceptTrip(tripId, driverId, price);
			}

			else if ("rejectTrip".equals(action)) {

				int tripId = Integer.parseInt(request.getParameter("tripId"));

				dao.rejectTrip(tripId, driverId);
			}
			else if("updateProfile".equals(action)){

				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String phone = request.getParameter("phone");
				int experience = Integer.parseInt(request.getParameter("experience"));

				Part license = request.getPart("license");
				Part idproof = request.getPart("idproof");

				String uploadPath = getServletContext().getRealPath("") + "documents";

				File uploadDir = new File(uploadPath);
				if(!uploadDir.exists()){
				uploadDir.mkdir();
				}

				String licenseName = license.getSubmittedFileName();
				String idName = idproof.getSubmittedFileName();

				license.write(uploadPath + "/" + licenseName);
				idproof.write(uploadPath + "/" + idName);

				UserDAO.updateDriverProfile(driverId,name,email,phone,experience,licenseName,idName);

				}

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect("Driver_homeServlet");
	}
}