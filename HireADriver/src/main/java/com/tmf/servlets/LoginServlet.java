package com.tmf.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        
        if ("admin".equals(username) && "1234".equals(password)) {
            out.println("<h2>Login Successful!</h2>");
            response.sendRedirect("admin_dashboard.html");
            
        } else if("user".equals(username) && "1234".equals(password)) {
     
            response.sendRedirect("user_home.html");
        }
        else {
            out.println("<h2>Invalid Username or Password</h2>");
            response.sendRedirect("User_homeServlet");
        }
    }

}

