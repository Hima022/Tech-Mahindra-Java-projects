
package com.tmf.servlets;

import java.io.IOException;

import com.tmf.servlets.dao.RoleDAO;
import com.tmf.servlets.dao.RoleDAOImpl;
import com.tmf.servlets.dao.UserDAO;
import com.tmf.servlets.dao.UserDAOImpl;
import com.tmf.servlets.entity.User;

//import jakarta.servlet.ServletException;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		StringBuffer sb = new StringBuffer();

		sb.append("<!DOCTYPE html>");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta charset='UTF-8'>");
		sb.append("<title>Register</title>");
		sb.append("<link rel='stylesheet' href='RegistrationStyle.css'>");
		sb.append("</head>");
		sb.append("<body>");

		sb.append("<h1>Registration For Hire A Driver</h1>");

		sb.append("<form action='RegistrationServlet' method='post'>");

		sb.append("<label>Uname</label>");
		sb.append("<input type='text' name='uname' required>");

		sb.append("<label>First Name</label>");
		sb.append("<input type='text' name='first_name' required>");

		sb.append("<label>Last Name</label>");
		sb.append("<input type='text' name='last_name' required>");

		sb.append("<label>Email</label>");
		sb.append("<input type='email' name='email' required>");

		sb.append("<label>Phone</label>");
		sb.append("<input type='text' name='phone_number' required>");

		sb.append("<label>Password</label>");
		sb.append("<input type='password' name='password' required>");

		sb.append("<label>Gender</label>");
		sb.append("<select name='gender' required>");
		sb.append("<option value='MALE'>Male</option>");
		sb.append("<option value='FEMALE'>Female</option>");
		sb.append("<option value='OTHERS'>Others</option>");
		sb.append("</select>");

		sb.append("<label>User Type</label>");
		sb.append("<select name='user_type'>");
		sb.append("<option value='CUSTOMER'>Customer</option>");
		sb.append("<option value='DRIVER'>Driver</option>");
		sb.append("</select>");

		sb.append("<label>License Number (for Driver only)</label>");
		sb.append("<input type='text' name='license_number'>");

		sb.append("<label>Date of Birth</label>");
		sb.append("<input type='date' name='dob' required>");

		sb.append("<button type='submit'>Register</button>");

		sb.append("</form>");

		sb.append("</body>");
		sb.append("</html>");

		response.getWriter().println(sb.toString());

		response.sendRedirect("login.html");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");

		try {

// 🔹 Read all form values
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

// 🔹 Create User object
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

// 🔹 Insert into users table
			int userId = userDAO.registerUser(user);

			if (userId == 0) {
				response.getWriter().println("Registration Failed!");
				return;
			}

// 🔹 Handle role logic
			if ("CUSTOMER".equals(userType)) {

				roleDAO.createCustomer(userId);

			} else if ("DRIVER".equals(userType)) {

				// License validation only for driver
				if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
					response.getWriter().println("License number is required for Driver!");
					return;
				}

				roleDAO.createDriver(userId, licenseNumber);
			}

// 🔹 Send welcome email
			EmailUtil.sendEmail(email, "Registration Successful", "Welcome to Hire A Driver Platform 🚗");

// 🔹 Success message
			response.getWriter()
					.println("<h3>Registration Successful!</h3>" + "<a href='login.html'>Click here to Login</a>");

		} catch (Exception e) {

// Duplicate email handling
			if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate")) {

				response.getWriter().println("Email already registered!");
			} else {
				e.printStackTrace();
				response.getWriter().println("Registration Error!");
			}
		}
	}
}
