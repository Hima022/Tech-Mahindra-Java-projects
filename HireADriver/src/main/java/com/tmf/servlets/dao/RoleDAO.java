package com.tmf.servlets.dao;
import com.tmf.servlets.entity.Driver;
public interface RoleDAO {
	void createCustomer(int userId) throws Exception;

    void createDriver(int userId, String licenseNumber) throws Exception;

    int getCustomerId(int userId) throws Exception;

    int getDriverId(int userId) throws Exception;

    Driver getDriverByUserId(int userId) throws Exception; 

    void uploadDocument(int userId, String path) throws Exception;
}