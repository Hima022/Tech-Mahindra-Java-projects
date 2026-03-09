package com.tmf.servlets.dao;

import java.util.List;


import com.tmf.servlets.entity.Trip;

public interface DriverDAO {

	List<Trip> getLiveTrips();

	void acceptTrip(int tripId, int driverId, double price);

	void rejectTrip(int tripId, int driverId);

	

}
