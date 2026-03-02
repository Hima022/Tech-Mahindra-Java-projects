package com.tmf.servlets.entity;

import java.sql.Date;

public class User {
	private int userId;
	private String firstName;
	private String lastName;
	private String uname;
	private String email;
	private String phoneNumber;
	private String password;
	private String userType;
	private String gender;
	private Date dob;
	private String address;

	
	public User() {
	}
	public User(int userId, String firstName, String lastName, String uname, String email, String phoneNumber,
			String password, String userType, String gender, Date dob, String address) {
		//super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.uname = uname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.userType = userType;
		this.gender = gender;
		this.dob = dob;
		this.address = address;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}