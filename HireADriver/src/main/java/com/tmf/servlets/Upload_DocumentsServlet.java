package com.tmf.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//@MultipartConfig
@WebServlet("/Upload_DocumentsServlet")
public class Upload_DocumentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Upload_DocumentsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

            HttpSession session = request.getSession(false);
            int userId = (int) session.getAttribute("userId");

            Part part = request.getPart("document");
            String fileName = part.getSubmittedFileName();

            String uploadPath =
                    getServletContext().getRealPath("") + "/uploads";

            part.write(uploadPath + "/" + fileName);

            try {
            	Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection(
	                    "jdbc:mysql://localhost:3306/hireadriver",
	                    "root",
	                    "Hima@@491"
	            );
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE drivers\r\n"
                        + "SET license_number=?, document_path=?\r\n"
                        + "WHERE user_id=?");
                ps.setString(1, fileName);
                ps.setInt(2, userId);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }

            response.sendRedirect("Driver_homeServlet");
        }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

		// TODO Auto-generated method stub
		//doGet(request, response);
	

}
