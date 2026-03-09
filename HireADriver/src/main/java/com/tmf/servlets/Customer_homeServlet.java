package com.tmf.servlets;

import java.io.IOException;
import java.sql.Date;
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

	    protected void doGet(HttpServletRequest request,
	                         HttpServletResponse response)
	            throws ServletException, IOException {

	        HttpSession session = request.getSession(false);

	        if(session == null ||
	           session.getAttribute("userId") == null ||
	           !"CUSTOMER".equals(session.getAttribute("userType"))){

	            response.sendRedirect("login.jsp");
	            return;
	        }

	        int userId = (int) session.getAttribute("userId");
	        String action = request.getParameter("action");

	        UserDAO userDAO = new UserDAOImpl();
	        CustomerDAO customerDAO = new CustomerDAOImpl();
	        BookingDAO bookingDAO = new BookingDAOImpl();

	        try{

	            User user = userDAO.getUserById(userId);
	            request.setAttribute("user", user);

	            if("profile".equals(action)){
	                request.getRequestDispatcher("profile.jsp")
	                       .forward(request,response);
	                return;
	            }

	            List<Trip> trips =
	                    customerDAO.getTripsByCustomer(userId);

	            List<Booking> bookings =
	                    bookingDAO.getBookingsByCustomer(userId);

	            request.setAttribute("trips", trips);
	            request.setAttribute("bookings", bookings);

	            request.getRequestDispatcher("Customer.jsp")
	                   .forward(request,response);

	        }catch(Exception e){
	            e.printStackTrace();
	        }
	    }


	    protected void doPost(HttpServletRequest request,
	                          HttpServletResponse response)
	            throws IOException {

	        HttpSession session = request.getSession(false);

	        if(session == null){
	            response.sendRedirect("login.jsp");
	            return;
	        }

	        int userId = (int) session.getAttribute("userId");
	        String action = request.getParameter("action");

	        CustomerDAO customerDAO = new CustomerDAOImpl();
	        BookingDAO bookingDAO = new BookingDAOImpl();
	        UserDAO userDAO = new UserDAOImpl();

	        try{

	            if("createTrip".equals(action)){

	                Trip trip = new Trip();

	                trip.setCustomerId(userId);
	                trip.setSource(request.getParameter("source"));
	                trip.setDestination(request.getParameter("destination"));
	                trip.setStartDate(Date.valueOf(request.getParameter("start_date")));
	                trip.setEndDate(Date.valueOf(request.getParameter("end_date")));
	                trip.setDurationDays(Integer.parseInt(request.getParameter("duration")));
	                trip.setPrice(Double.parseDouble(request.getParameter("price")));

	                customerDAO.createTrip(trip);
	                System.out.println("Start Date = " + request.getParameter("start_date"));
		            System.out.println("End Date = " + request.getParameter("end_date"));
	            }
	           

	            else if("updateProfile".equals(action)){

	                String name = request.getParameter("name");
	                String email = request.getParameter("email");
	                String phone = request.getParameter("phone");
	                int age = Integer.parseInt(request.getParameter("age"));

	                Part filePart = request.getPart("profileImage");
	                String fileName = filePart.getSubmittedFileName();

	                String uploadPath = getServletContext().getRealPath("") + "images";

	                filePart.write(uploadPath + "/" + fileName);

	                userDAO.updateProfile(userId,name,email,phone,age,fileName);
	            }

	            else if("selectDriver".equals(action)){

	                int tripId = Integer.parseInt(request.getParameter("tripId"));
	                int driverId = Integer.parseInt(request.getParameter("driverId"));

	                bookingDAO.createBooking(tripId,userId,driverId);
	                customerDAO.updateTripStatus(tripId,"BOOKED");
	                
	            }

	        }catch(Exception e){
	            e.printStackTrace();
	        }

	        response.sendRedirect("Customer_homeServlet");
	    }
	}