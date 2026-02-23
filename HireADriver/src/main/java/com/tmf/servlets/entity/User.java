package com.tmf.servlets.entity;

public class User {
	    private String userName;
	    private String role;
	    private String contactNo;
	    private String email;

	    public User(String userName, String role, String contactNo, String email) {
	        this.userName = userName;
	        this.role = role;
	        this.contactNo = contactNo;
	        this.email = email;
	    }

	    public String getUserName() {
	        return userName;
	    }

	    public String getRole() {
	        return role;
	    }

	    public String getContactNo() {
	        return contactNo;
	    }

	    public String getEmail() {
	        return email;
	    }
	}


