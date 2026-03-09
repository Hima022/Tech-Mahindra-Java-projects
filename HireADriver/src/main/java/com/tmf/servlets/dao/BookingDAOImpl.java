package com.tmf.servlets.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.servlets.util.DBConnection;
import com.tmf.servlets.entity.Booking;

public class BookingDAOImpl implements BookingDAO {

	Connection con = DBConnection.getConnection();

	@Override
	public void createBooking(int tripId, int customerId, int driverId) {

		try {

			String sql = "INSERT INTO bookings(trip_id,customer_id,driver_id) VALUES(?,?,?)";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, tripId);
			ps.setInt(2, customerId);
			ps.setInt(3, driverId);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateBookingStatus(int bookingId, String status) {

		try {

			String sql = "UPDATE bookings SET status=? WHERE booking_id=?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, status);
			ps.setInt(2, bookingId);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Booking> getAllBookings() {

		List<Booking> list = new ArrayList<>();

		try {

			String sql = "SELECT * FROM bookings";

			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Booking b = new Booking();

				b.setBookingId(rs.getInt("booking_id"));
				b.setTripId(rs.getInt("trip_id"));
				b.setDriverId(rs.getInt("driver_id"));
				b.setStatus(rs.getString("status"));

				list.add(b);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Booking> getBookingsByCustomer(int userId) {

		List<Booking> bookings = new ArrayList<>();

		try {

			String sql = "SELECT b.booking_id, b.trip_id, b.driver_id, b.status, " + "t.source, t.destination "
					+ "FROM bookings b JOIN trips t ON b.trip_id = t.trip_id " + "WHERE b.customer_id=?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Booking b = new Booking();

				b.setBookingId(rs.getInt("booking_id"));
				b.setTripId(rs.getInt("trip_id"));
				b.setDriverId(rs.getInt("driver_id"));
				b.setStatus(rs.getString("status"));

				b.setSource(rs.getString("source"));
				b.setDestination(rs.getString("destination"));

				bookings.add(b);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bookings;
	}

	@Override
	public List<Booking> getBookingsByDriver(int driverId) {

		List<Booking> bookings = new ArrayList<>();

		try {

			String sql = "SELECT \r\n"
					+ "b.booking_id,\r\n"
					+ "t.source,\r\n"
					+ "t.destination,\r\n"
					+ "u.name AS customer_name,\r\n"
					+ "t.start_date\r\n"
					+ "FROM bookings b\r\n"
					+ "JOIN trips t ON b.trip_id = t.trip_id\r\n"
					+ "JOIN users u ON b.customer_id = u.user_id\r\n"
					+ "WHERE b.driver_id = ?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, driverId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Booking b = new Booking();

				b.setBookingId(rs.getInt("booking_id"));
				b.setTripId(rs.getInt("trip_id"));
				b.setDriverId(rs.getInt("driver_id"));
				b.setStatus(rs.getString("status"));
				b.setCustomerName(rs.getString("customerName"));
				b.setSource(rs.getString("source"));
				b.setDestination(rs.getString("destination"));

				bookings.add(b);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bookings;
	}

}