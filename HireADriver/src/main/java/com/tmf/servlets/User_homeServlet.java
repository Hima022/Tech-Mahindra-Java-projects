package com.tmf.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.tmf.servlets.entity.User;
@WebServlet("/User_homeServlet")
public class User_homeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public User_homeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("loggedinUser");

        if (user == null) {
            response.sendRedirect("login.html");
            return;
        }

        response.setContentType("text/html");

        response.getWriter().append("<!DOCTYPE html>\r\n"
        		+ "                    <html>\r\n"
        		+ "                    <head>\r\n"
        		+ "                        <title>User Home - Hire A Driver</title>\r\n"
        		+ "                        <link rel=\"stylesheet\" href=\"UserStyle.css\">\r\n"
        		+ "                    </head>\r\n"
        		+ "                    <body>\r\n"
        		+ "\r\n"
        		+ "                        <div class=\"navbar\">\r\n"
        		+ "                            <h2>Hire A Driver</h2>\r\n"
        		+				"	<button onclick=\"location.href='logoutServlet'\">Logout</button>\r\n"
        		+ "                        </div>\r\n"
        		+ "\r\n"
        		+ "                        <div>\r\n"
        		+ "                            <h3>User Details</h3>\r\n"
        		+ user.getUserName() + "<br/>"
				+ user.getEmail() + "<br/>"
				+ user.getContactNo() + "<br/>"
        		
        		+ "                        </div>\r\n"
        		+ "\r\n"
        		+ "                        <div class=\"welcome-section\">\r\n"
        		+ "                            <h2>Welcome User</h2>\r\n"
        		+ "                        </div>\r\n"
        		+ "\r\n"
        		+ "                    </body>\r\n"
        		+ "                    </html>");
        
    }
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	doGet(request, response);
}
}