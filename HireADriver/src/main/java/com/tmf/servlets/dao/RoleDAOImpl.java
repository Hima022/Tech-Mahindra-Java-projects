package com.tmf.servlets.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.servlets.util.DBConnection;
import com.tmf.servlets.entity.Driver; 

public class RoleDAOImpl implements RoleDAO {

	    @Override
	    public void createCustomer(int userId) throws Exception {

	        String sql = "INSERT INTO customers(user_id) VALUES(?)";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, userId);
	            ps.executeUpdate();
	        }
	    }

	    @Override
	    public void createDriver(int userId, String licenseNumber) throws Exception {

	        String sql = "INSERT INTO drivers(user_id, license_number) VALUES(?,?)";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, userId);
	            ps.setString(2, licenseNumber);
	            ps.executeUpdate();
	        }
	    }

	    @Override
	    public int getCustomerId(int userId) throws Exception {

	        String sql = "SELECT customer_id FROM customers WHERE user_id=?";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, userId);
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                return rs.getInt("customer_id");
	            }
	        }

	        return 0;
	    }

	    @Override
	    public Driver getDriverByUserId(int userId) throws Exception {

	        Driver driver = null;

	        String sql = "SELECT * FROM drivers WHERE user_id=?";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, userId);
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {

	                driver = new Driver();
	                driver.setDriverId(rs.getInt("driver_id"));
	                driver.setUserId(rs.getInt("user_id"));
	                driver.setLicenseNumber(rs.getString("license_number"));
	                driver.setDocumentPath(rs.getString("document_path"));
	            }
	        }

	        return driver;
	    }

	    @Override
	    public void uploadDocument(int userId, String path) throws Exception {

	        String sql = "UPDATE drivers SET document_path=? WHERE user_id=?";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, path);
	            ps.setInt(2, userId);
	            ps.executeUpdate();
	        }
	    }

	    @Override
	    public int getDriverId(int userId) throws Exception {

	        String sql = "SELECT driver_id FROM drivers WHERE user_id=?";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, userId);
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                return rs.getInt("driver_id");
	            }
	        }

	        return 0;
	    }
	}