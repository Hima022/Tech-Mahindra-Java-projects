<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.tmf.servlets.entity.Booking"%>

<html>
<head>
<title>Admin Dashboard</title>
</head>

<body>

<h2>Admin Dashboard</h2>

<h3>All Bookings</h3>

<table border="1">

<tr>
<th>Booking ID</th>
<th>Trip ID</th>
<th>Driver ID</th>
<th>Status</th>
</tr>

<%
List<Booking> bookings = (List<Booking>)request.getAttribute("bookings");

if(bookings != null){
for(Booking b : bookings){
%>

<tr>

<td><%=b.getBookingId()%></td>
<td><%=b.getTripId()%></td>
<td><%=b.getDriverId()%></td>
<td><%=b.getStatus()%></td>

</tr>

<%
}
}
%>

</table>

</body>
</html>