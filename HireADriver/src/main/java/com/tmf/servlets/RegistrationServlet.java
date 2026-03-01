
package com.tmf.servlets;

import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.getWriter().append("Served at: ").append(request.getContextPath());

		response.sendRedirect("login.html");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

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
		

		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hireadriver", "root", "Hima@@491");

			con.setAutoCommit(false); // transaction start

			// Insert into users
			String userSql = "INSERT INTO users(uname, first_name, last_name, email, phone_number, password, user_type, gender, dob) VALUES (?,?,?,?,?,?,?,?,?)";

			// PreparedStatement ps = con.prepareStatement(userSql,
			// Statement.RETURN_GENERATED_KEYS);
			PreparedStatement ps = con.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, uname);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, email);
			ps.setString(5, phone);
			ps.setString(6, password);
			ps.setString(7, userType);
			ps.setString(8, gender);
			ps.setDate(9, Date.valueOf(dob));

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			int userId = rs.getInt(1);

			// Insert into role table
			if ("DRIVER".equals(userType)) {

			    if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
			        response.getWriter().println("License number is required for Driver registration.");
			        con.rollback();
			        return;
			    }

			    PreparedStatement dps = con.prepareStatement(
			        "INSERT INTO drivers(user_id, license_number) VALUES(?,?)"
			    );

			    dps.setInt(1, userId);
			    dps.setString(2, licenseNumber);
			    dps.executeUpdate();

			} else if ("CUSTOMER".equals(userType)) {

			    PreparedStatement cps = con.prepareStatement(
			        "INSERT INTO customers(user_id) VALUES(?)"
			    );

			    cps.setInt(1, userId);
			    cps.executeUpdate();
			}
			

			con.commit(); // commit transaction

			// Send Email
			EmailUtil.sendEmail(email, "Registration Successful", "Welcome to Hire A Driver!");

			response.sendRedirect("login.html");

		} catch (Exception e) {

			try {
				if (con != null)
					con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			e.printStackTrace();
			response.getWriter().println("Registration Failed");

		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}