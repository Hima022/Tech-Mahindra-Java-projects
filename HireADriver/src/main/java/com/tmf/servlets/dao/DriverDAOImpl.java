package com.tmf.servlets.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.servlets.util.DBConnection;

import com.tmf.servlets.entity.Trip;



	public class DriverDAOImpl implements DriverDAO {

	    Connection con = DBConnection.getConnection();

	    @Override
	    public List<Trip> getLiveTrips() {

	        List<Trip> trips = new ArrayList<>();

	        try {

	            String sql = "SELECT * FROM trips WHERE status='LIVE'";

	            PreparedStatement ps = con.prepareStatement(sql);

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
	    public void acceptTrip(int tripId, int driverId) {

	        try {

	            String sql =
	            "UPDATE bookings SET status='CONFIRMED', driver_id=? WHERE trip_id=?";

	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setInt(1, driverId);
	            ps.setInt(2, tripId);

	            ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }

	    @Override
	    public void rejectTrip(int tripId, int driverId) {

	        try {

	            String sql =
	            "UPDATE bookings SET status='REJECTED', driver_id=? WHERE trip_id=?";

	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setInt(1, driverId);
	            ps.setInt(2, tripId);

	            ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }
	}