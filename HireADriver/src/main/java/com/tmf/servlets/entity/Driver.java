package com.tmf.servlets.entity;

public class Driver {


	    private int driverId;
	    private int userId;
	    private String licenseNumber;
	    private String documentPath;

	    public Driver() {}

	    public int getDriverId() {
	        return driverId;
	    }

	    public void setDriverId(int driverId) {
	        this.driverId = driverId;
	    }

	    public int getUserId() {
	        return userId;
	    }

	    public void setUserId(int userId) {
	        this.userId = userId;
	    }

	    public String getLicenseNumber() {
	        return licenseNumber;
	    }

	    public void setLicenseNumber(String licenseNumber) {
	        this.licenseNumber = licenseNumber;
	    }

	    public String getDocumentPath() {
	        return documentPath;
	    }

	    public void setDocumentPath(String documentPath) {
	        this.documentPath = documentPath;
	    }
	}

