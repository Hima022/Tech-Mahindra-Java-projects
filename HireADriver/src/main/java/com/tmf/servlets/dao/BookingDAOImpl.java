package com.tmf.servlets.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.servlets.util.DBConnection;
import com.tmf.servlets.entity.Booking;

public class BookingDAOImpl implements BookingDAO {

	// CREATE TRIP

	@Override
	public int createTrip(String source, String destination) throws Exception {

		String sql = "INSERT INTO trips(source, destination) VALUES(?, ?)";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, source);
			ps.setString(2, destination);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1); // trip_id
			}
		}

		return 0;
	}

	// 2️⃣ CREATE BOOKING

	@Override
	public void createBooking(int customerId, int driverId, int tripId) throws Exception {

		String sql = "INSERT INTO bookings(customer_id, driver_id, trip_id, booking_date) VALUES(?,?,?,CURDATE())";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, customerId);
			ps.setInt(2, driverId);
			ps.setInt(3, tripId);

			ps.executeUpdate();
		}
	}

	// CUSTOMER HISTORY

	@Override
	public List<Booking> getBookingsByCustomer(int userId) throws Exception {

		List<Booking> list = new ArrayList<>();

		String sql = "SELECT b.booking_id, b.trip_id, t.source, t.destination, t.status, b.booking_date "
				+ "FROM bookings b " + "JOIN customers c ON b.customer_id = c.customer_id "
				+ "JOIN trips t ON b.trip_id = t.trip_id " + "WHERE c.user_id = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Booking booking = new Booking();

				booking.setBookingId(rs.getInt("booking_id"));
				booking.setTripId(rs.getInt("trip_id"));
				booking.setSource(rs.getString("source"));
				booking.setDestination(rs.getString("destination"));
				booking.setStatus(rs.getString("status"));
				booking.setBookingDate(rs.getString("booking_date"));

				list.add(booking);
			}
		}

		return list;
	}

	// DRIVER HISTORY

	@Override
	public List<Booking> getBookingsByDriver(int userId) throws Exception {

		List<Booking> list = new ArrayList<>();

		String sql = "SELECT b.booking_id, b.trip_id, t.source, t.destination, t.status, b.booking_date "
				+ "FROM bookings b " + "JOIN drivers d ON b.driver_id = d.driver_id "
				+ "JOIN trips t ON b.trip_id = t.trip_id " + "WHERE d.user_id = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Booking booking = new Booking();

				booking.setBookingId(rs.getInt("booking_id"));
				booking.setTripId(rs.getInt("trip_id"));
				booking.setSource(rs.getString("source"));
				booking.setDestination(rs.getString("destination"));
				booking.setStatus(rs.getString("status"));
				booking.setBookingDate(rs.getString("booking_date"));

				list.add(booking);
			}
		}

		return list;
	}

	// ADMIN - ALL BOOKINGS

	@Override
	public List<Booking> getAllBookings() throws Exception {

		List<Booking> list = new ArrayList<>();

		String sql = "SELECT b.booking_id, b.trip_id, t.source, t.destination, t.status, b.booking_date "
				+ "FROM bookings b " + "JOIN trips t ON b.trip_id = t.trip_id";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Booking booking = new Booking();

				booking.setBookingId(rs.getInt("booking_id"));
				booking.setTripId(rs.getInt("trip_id"));
				booking.setSource(rs.getString("source"));
				booking.setDestination(rs.getString("destination"));
				booking.setStatus(rs.getString("status"));
				booking.setBookingDate(rs.getString("booking_date"));

				list.add(booking);
			}
		}

		return list;
	}
}