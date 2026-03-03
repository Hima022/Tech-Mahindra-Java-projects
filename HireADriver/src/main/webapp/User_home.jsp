<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.tmf.servlets.entity.User" %>
<%@ page import="com.tmf.servlets.entity.Booking" %>

<%
User user = (User) request.getAttribute("user");
List<Booking> bookings =
        (List<Booking>) request.getAttribute("bookings");
String action =
        (String) request.getAttribute("action");
%>

<html>
<head>
<title>User Dashboard</title>
</head>
<body>

<h2>Welcome <%= user.getFirstName() %></h2>

<!-- 🔹 Profile Section -->
<h3>My Profile</h3>
<p><b>Name:</b> <%= user.getFirstName() %> <%= user.getLastName() %></p>
<p><b>Email:</b> <%= user.getEmail() %></p>
<p><b>Phone:</b> <%= user.getPhoneNumber() %></p>

<a href="User_homeServlet?action=edit">Update Profile</a>
<br><br>

<!-- 🔹 Edit Profile -->
<% if ("edit".equals(action)) { %>

<h3>Edit Profile</h3>
<form method="post" action="User_homeServlet">

First Name:<br>
<input type="text" name="first_name"
       value="<%= user.getFirstName() %>" required><br><br>

Last Name:<br>
<input type="text" name="last_name"
       value="<%= user.getLastName() %>" required><br><br>

Phone:<br>
<input type="text" name="phone_number"
       value="<%= user.getPhoneNumber() %>" required><br><br>

<button type="submit">Update</button>
</form>

<% } %>

<hr>

<!-- 🔹 Booking History -->
<h3>Previous Bookings</h3>

<% if (bookings == null || bookings.isEmpty()) { %>
    <p>No bookings found.</p>
<% } else { %>

<table border="1" cellpadding="10">
<tr>
    <th>Booking ID</th>
    <th>Source</th>
    <th>Destination</th>
    <th>Status</th>
</tr>

<% for (Booking b : bookings) { %>
<tr>
    <td><%= b.getBookingId() %></td>
    <td><%= b.getSource() %></td>
    <td><%= b.getDestination() %></td>
    <td><%= b.getStatus() %></td>
</tr>
<% } %>

</table>

<% } %>

<br><br>
<button onclick="location.href='logoutServlet'">
    Logout
</button>

</body>
</html>