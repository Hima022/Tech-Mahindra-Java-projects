package com.servlets.util;
import java.sql.*;
public class DBConnection {
	
	    private static final String URL = "jdbc:mysql://localhost:3306/hireadriver";
	    private static final String USER = "root";
	    private static final String PASSWORD = "Hima@@491";

	    public static Connection getConnection() throws Exception {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }
	}
