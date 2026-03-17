package com.tmf.servlets.dao;

import java.util.List;
import com.tmf.servlets.entity.Booking;


public interface BookingDAO {

    void createBooking(int tripId, int customerId, int driverId);

    void updateBookingStatus(int bookingId, String status);

    List<Booking> getBookingsByCustomer(int customerId);

    List<Booking> getBookingsByDriver(int driverId);

}