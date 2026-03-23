package com.tmf.servlets.dao;

import java.util.List;

import com.tmf.servlets.entity.Trip;

public interface CustomerDAO {

	void createTrip(Trip trip);
	

	List<Trip> getTripsByCustomer(int customerId);

	void updateTripStatus(int tripId, String status);

	
}
