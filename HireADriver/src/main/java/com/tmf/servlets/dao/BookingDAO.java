package com.tmf.servlets.dao;
import java.util.List;

import com.tmf.servlets.entity.Booking;

public interface BookingDAO {


    int createTrip(String source, String destination) throws Exception;

    void createBooking(int customerId, int driverId, int tripId) throws Exception;

    List<Booking> getBookingsByCustomer(int userId) throws Exception;

    List<Booking> getBookingsByDriver(int userId) throws Exception;

    List<Booking> getAllBookings() throws Exception;


}
