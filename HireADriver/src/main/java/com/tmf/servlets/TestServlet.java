package com.tmf.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/TestServlet")
@WebServlet("/test")

public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

 
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("op");
        Integer result = null;

        if (op != null) {

            int num1 = Integer.parseInt(request.getParameter("num1"));
            int num2 = Integer.parseInt(request.getParameter("num2"));

            if (op.equals("add")) {
                result = num1 + num2;
            } else if (op.equals("sub")) {
                result = num1 - num2;
            } else if (op.equals("mul")) {
                result = num1 * num2;
            } else if (op.equals("div") && num2 != 0) {
                result = num1 / num2;
            }
        }

        response.getWriter().append("<html><body>"
                + "<h1>Calculator Page</h1>"
                + "<form action='test' method='get'>"
                + "Num1 : <input name='num1' type='text'/><br><br>"
                + "Num2 : <input name='num2' type='text'/><br><br>"
                + "Operation : <select name='op'>"
                +"<option value='select'>Select the operation</operation>"
                + "<option value='add'>Add</option>"
                + "<option value='sub'>Subtract</option>"
                + "<option value='mul'>Multiply</option>"
                + "<option value='div'>Divide</option>"
                + "</select><br><br>"
                + "<input type='submit'/>"
                + "</form>"
                + (result != null ? "<h2>Result is : " + result + "</h2>" : "")
                + "</body></html>");
    }
}