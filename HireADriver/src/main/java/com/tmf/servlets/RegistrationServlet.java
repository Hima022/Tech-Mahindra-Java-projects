
package com.tmf.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.getWriter().append("Served at: ").append(request.getContextPath());

		response.sendRedirect("login.html");

	}

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       response.setContentType("text/html");
       PrintWriter out = response.getWriter();

       String firstName = request.getParameter("first_name");
       String lastName = request.getParameter("last_name");
       String uname = request.getParameter("uname");
       String email = request.getParameter("email");
       String phone = request.getParameter("phone_number");
       String password = request.getParameter("password");
       String userType = request.getParameter("user_type");
       String gender = request.getParameter("gender");
       String dob = request.getParameter("dob");

       try {

           Class.forName("com.mysql.cj.jdbc.Driver");

           Connection con = DriverManager.getConnection(
                   "jdbc:mysql://localhost:3306/hireadriver",
                   "root",
                   "Hima@@491"
           );

           String sql = "INSERT INTO users "
                   + "(first_name,last_name,uname,email,phone_number,password,user_type,gender,dob) "
                   + "VALUES (?,?,?,?,?,?,?,?,?)";

           PreparedStatement ps = con.prepareStatement(sql);

           ps.setString(1, firstName);
           ps.setString(2, lastName);
           ps.setString(3, uname);
           ps.setString(4, email);
           ps.setString(5, phone);
           ps.setString(6, password);
           ps.setString(7, userType);
           ps.setString(8, gender);
           ps.setDate(9, Date.valueOf(dob));

           int result = ps.executeUpdate();

           if (result > 0) {
               out.println("Registration successful! Please login.");
           } else {
               out.println("Registration failed.");
           }

           con.close();

       } catch (Exception e) {
           e.printStackTrace();
           out.println("Error: " + e.getMessage());
       }
   }
}