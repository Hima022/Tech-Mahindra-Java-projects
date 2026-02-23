package com.tmf.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/User_homeServlet")
public class User_homeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public User_homeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 response.sendRedirect("user_home.html");

}
}
