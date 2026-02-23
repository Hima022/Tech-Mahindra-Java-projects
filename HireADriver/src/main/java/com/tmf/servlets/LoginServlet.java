package com.tmf.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
//import java.io.PrintWriter;

import com.tmf.servlets.entity.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			response.getWriter().append("Served at: ").append(request.getContextPath());
			
			Cookie ck[] = request.getCookies();
			String username = null;
		
			if (ck != null) {
			    for (Cookie c : ck) {
			        if ("uname".equals(c.getName())) {
			            username = c.getValue();
			        }
			    }
			}
			User user = new User(username, "Client", "", "");
			request.setAttribute("loggedinUser", user);
			RequestDispatcher rd=request.getRequestDispatcher("User_home");
			rd.forward(request, response);
		}
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    	String uname = request.getParameter("uname");
			String password = request.getParameter("password");
			
			Cookie ck=new Cookie("uname",uname);//creating cookie object  
			response.addCookie(ck); 
			
			
			Cookie ck1 = new Cookie("uname", uname);
			ck1.setPath("/");        
			//ck1.setMaxAge(60*60);    
			response.addCookie(ck1);
			
			
			if("admin".equals(uname) && "admin".equals(password)) {

			    User adminUser = new User("ADMIN", "Administrator", "989898", "admin@xyz.com");

			    request.getSession().setAttribute("loggedinUser", adminUser); 

			    response.sendRedirect("Admin_DashboardServlet");  // 
			}
			else {
			    if(uname.equals(password)) {

			        User user = new User(uname, "Client", "76767676", uname+"@gmail.com");

			        request.getSession().setAttribute("loggedinUser", user); 

			        response.sendRedirect("User_homeServlet");  
			    }
			    else {
			        response.sendRedirect("login.html");
			    }
			}
			}
			//doGet(request, response);
		}

	    
	    