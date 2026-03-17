package com.tmf.servlets.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.servlets.util.DBConnection;
import com.tmf.servlets.entity.Trip;

public class CustomerDAOImpl implements CustomerDAO {

	Connection con = DBConnection.getConnection();


	    @Override
	    public void createTrip(Trip trip) {

	        try {

	            String sql =
	            "INSERT INTO trips(customer_id,source,destination,start_date,end_date,duration_hrs,price,status) VALUES(?,?,?,?,?,?,?,?)";

	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setInt(1, trip.getCustomerId());
	            ps.setString(2, trip.getSource());
	            ps.setString(3, trip.getDestination());
	            ps.setTimestamp(4, trip.getStartDate());
	            ps.setTimestamp(5, trip.getEndDate());
	            ps.setInt(6, trip.getDurationHrs());
	            ps.setDouble(7, trip.getPrice());
	            ps.setString(8, "LIVE");

	            ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public List<Trip> getTripsByCustomer(int customerId) {

	        List<Trip> trips = new ArrayList<>();

	        try {

	            String sql = "SELECT * FROM trips WHERE customer_id=?";

	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setInt(1, customerId);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {

	                Trip t = new Trip();

	                t.setTripId(rs.getInt("trip_id"));
	                t.setCustomerId(rs.getInt("customer_id"));
	                t.setSource(rs.getString("source"));
	                t.setDestination(rs.getString("destination"));
	                t.setStartDate(rs.getTimestamp("start_date"));
	                t.setEndDate(rs.getTimestamp("end_date"));
	                t.setDurationHrs(rs.getInt("duration_hrs"));
	                t.setPrice(rs.getDouble("price"));

	                trips.add(t);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return trips;
	    }

	    @Override
	    public void updateTripStatus(int tripId, String status) {

	        try {

	            String sql = "UPDATE trips SET status=? WHERE trip_id=?";

	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setString(1, status);
	            ps.setInt(2, tripId);

	            ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}