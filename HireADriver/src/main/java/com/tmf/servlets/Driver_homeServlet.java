package com.tmf.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/Driver_homeServlet")
public class Driver_homeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Driver_homeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);

		if (session == null || !"DRIVER".equals(session.getAttribute("userType"))) {
			response.sendRedirect("login.html");
			return;
		}

		int userId = (int) session.getAttribute("userId");

		String license = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hireadriver", "root",
					"Hima@@491");

			PreparedStatement ps = con.prepareStatement("SELECT u.first_name,u.email,d.license_number,d.document_path "
					+ "FROM users u JOIN drivers d ON u.user_id=d.user_id WHERE u.user_id=?");

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				rs.getString("first_name");
				rs.getString("email");
				license = rs.getString("license_number");
				rs.getString("document_path");
			}

			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setContentType("text/html");

		response.getWriter().append("            <!DOCTYPE html>\r\n" + "            <html>\r\n"
				+ "            <head>\r\n" + "                <title>Driver Home - Hire A Driver</title>\r\n"
				+ "                <link rel=\"stylesheet\" href=\"DriverStyle.css\">\r\n" + "            </head>\r\n"
				+ "            <body>\r\n" + "\r\n" + "            <div class=\"navbar\">\r\n"
				+ "                <h2>Hire A Driver</h2>\r\n"
				+ "                <button onclick=\"location.href='logoutServlet'\">Logout</button>\r\n"
				+ "            </div>\r\n" + "\r\n" + "            <div class=\"welcome-section\">\r\n"
				+ "                <h2>Welcome, \"\"\" + firstName + \"\"\"</h2>\r\n" + "            </div>\r\n"
				+ "\r\n" + "            <div class=\"container\">\r\n" + "\r\n"
				+ "                <div class=\"card\">\r\n" + "                    <h3>Profile</h3>\r\n"
				+ "                    <p>Email: \"\"\" + email + \"\"\"</p>\r\n"
				+ "                    <p>License: \"\"\" + \r\n"
				+ "                    (license == null || license.isEmpty() ? \"Not Submitted\" : license) + \"\"\"\r\n"
				+ "                    </p>\r\n" + "                    <p>Document: \"\"\" + \r\n"
				+ "                    (documentPath == null ? \"Not Uploaded\" : documentPath) + \"\"\"\r\n"
				+ "                    </p>\r\n" + "                </div>");

		// 🔹 If License Not Submitted
		if (license == null || license.isEmpty()) {

			response.getWriter()
					.append(" <div class=\"card\">\r\n" + "                        <h3>Submit License Number</h3>\r\n"
							+ "                        <form action=\"UploadDocumentServlet\" method=\"post\">\r\n"
							+ "                            <input type=\"text\" name=\"license_number\"\r\n"
							+ "                                   placeholder=\"Enter License Number\" required>\r\n"
							+ "                            <button type=\"submit\">Submit</button>\r\n"
							+ "                        </form>\r\n" + "                    </div>");
		}

		// 🔹 Upload Document Section
		response.getWriter()
				.append("  <div class=\"card\">\r\n" + "                    <h3>Upload Document</h3>\r\n"
						+ "                    <form action=\"UploadDocumentServlet\"\r\n"
						+ "                          method=\"post\"\r\n"
						+ "                          enctype=\"multipart/form-data\">\r\n"
						+ "                        <input type=\"file\" name=\"document\" required>\r\n"
						+ "                        <button type=\"submit\">Upload</button>\r\n"
						+ "                    </form>\r\n" + "                </div>\r\n" + "\r\n"
						+ "            </div>\r\n" + "\r\n" + "            </body>\r\n" + "            </html>");
	}

	/*
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
