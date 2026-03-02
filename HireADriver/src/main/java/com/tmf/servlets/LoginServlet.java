package com.tmf.servlets;

import java.io.IOException;

import com.tmf.servlets.dao.UserDAO;
import com.tmf.servlets.dao.UserDAOImpl;
import com.tmf.servlets.entity.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//import com.tmf.servlets.entity.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher("login.html");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String email = request.getParameter("uname");
        String password = request.getParameter("password");

        try {

            UserDAO userDAO = new UserDAOImpl();
            User user = userDAO.loginUser(email, password);

            if (user != null) {

                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userType", user.getUserType());

                if ("CUSTOMER".equals(user.getUserType())) {
                    response.sendRedirect("User_homeServlet");
                } else if ("DRIVER".equals(user.getUserType())) {
                    response.sendRedirect("Driver_homeServlet");
                } else if ("ADMIN".equals(user.getUserType())) {
                    response.sendRedirect("Admin_DashboardServlet");
                }

            } else {
                response.getWriter().println("Invalid Credentials");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}