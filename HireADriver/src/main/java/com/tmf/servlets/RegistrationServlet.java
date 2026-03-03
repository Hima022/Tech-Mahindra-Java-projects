
package com.tmf.servlets;

import java.io.IOException;

import com.servlets.util.EmailUtil;
import com.tmf.servlets.dao.RoleDAO;
import com.tmf.servlets.dao.RoleDAOImpl;
import com.tmf.servlets.dao.UserDAO;
import com.tmf.servlets.dao.UserDAOImpl;
import com.tmf.servlets.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegistrationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Load Registration Page
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("RegistrationPage.jsp").forward(request, response);
	}

	// Handle Form Submission
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		try {

			// Read form values
			String uname = request.getParameter("uname");
			String firstName = request.getParameter("first_name");
			String lastName = request.getParameter("last_name");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone_number");
			String password = request.getParameter("password");
			String userType = request.getParameter("user_type");
			String gender = request.getParameter("gender");
			String dob = request.getParameter("dob");
			String licenseNumber = request.getParameter("license_number");

			// Create User object
			User user = new User();
			user.setUname(uname);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setPhoneNumber(phone);
			user.setPassword(password);
			user.setUserType(userType);
			user.setGender(gender);
			user.setDob(java.sql.Date.valueOf(dob));

			UserDAO userDAO = new UserDAOImpl();
			RoleDAO roleDAO = new RoleDAOImpl();

			// Insert into users table
			int userId = userDAO.registerUser(user);

			if (userId == 0) {
				request.setAttribute("error", "Registration Failed!");
				request.getRequestDispatcher("RegistrationPage.jsp").forward(request, response);
				return;
			}

			// Role logic
			if ("CUSTOMER".equals(userType)) {

				roleDAO.createCustomer(userId);

			} else if ("DRIVER".equals(userType)) {

				if (licenseNumber == null || licenseNumber.trim().isEmpty()) {

					request.setAttribute("error", "License number is required for Driver!");

					request.getRequestDispatcher("RegistrationPage.jsp").forward(request, response);
					return;
				}

				roleDAO.createDriver(userId, licenseNumber);
			}

			// Send welcome email
			EmailUtil.sendEmail(email, "Registration Successful", "Welcome to Hire A Driver Platform 🚗");

			// Redirect to login page after success
			response.sendRedirect("login.jsp");

		} catch (Exception e) {

			if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate")) {

				request.setAttribute("error", "Email already registered!");

			} else {

				request.setAttribute("error", "Registration Error! Please try again.");
			}

			request.getRequestDispatcher("RegistrationPage.jsp").forward(request, response);
		}
	}
}