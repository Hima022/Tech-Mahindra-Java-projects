package com.tmf.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //  Delete cookie properly
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("uname".equals(c.getName())) {

                    c.setValue("");
                    c.setPath("/");      
                    c.setMaxAge(0);      
                    response.addCookie(c);
                }
            }
        }

        // Invalidate session
        //request.getSession().invalidate();

        // Redirect to login page
        response.sendRedirect("login.html");
    }
}