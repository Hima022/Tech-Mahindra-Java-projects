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

	public List<Trip> getLiveTrips() {

		List<Trip> trips = new ArrayList<>();

		try {

			String sql = "SELECT * FROM trips WHERE status='LIVE'";

			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				
				
				Trip t = new Trip();

				t.setTripId(rs.getInt("trip_id"));
				t.setSource(rs.getString("source"));
				t.setDestination(rs.getString("destination"));
				t.setStartDate(rs.getDate("start_date"));
				t.setPrice(rs.getDouble("price"));
				trips.add(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return trips;
	}

	public void acceptTrip(int tripId, int driverId, double price) {

		try {

			String sql = "INSERT INTO driver_trip_requests(trip_id,driver_id,driver_price,status) VALUES(?,?,?,'ACCEPTED')";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, tripId);
			ps.setInt(2, driverId);
			ps.setDouble(3, price);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void rejectTrip(int tripId, int driverId) {

		try {

			String sql = "INSERT INTO driver_trip_requests(trip_id,driver_id,status) VALUES(?,?,'REJECTED')";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, tripId);
			ps.setInt(2, driverId);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

}