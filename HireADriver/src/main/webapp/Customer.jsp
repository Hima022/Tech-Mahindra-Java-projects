<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ page import="java.util.List"%>
<%@ page import="com.tmf.servlets.entity.*"%>

<%
User user = (User) request.getAttribute("user");
List<Trip> trips = (List<Trip>) request.getAttribute("trips");
List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
String action = request.getParameter("action");
%>

<html>

<head>

<title>Customer Dashboard</title>

<style>

body{
background:black;
color:white;
font-family:Arial;
padding:20px;
}

.topbar{
display:flex;
align-items:center;
gap:190px;
button:right-corner;
}

.profile{
display:flex;
align-items:center;
gap:10px;
cursor:pointer;
}

.profile img{
width:40px;
height:40px;
border-radius:50%;
}

.search{
padding:10px;
border-radius:20px;
border:none;
}

.cards{
display:flex;
gap:20px;
flex-wrap:wrap;
margin-top:20px;
}

.card{
background:white;
color:black;
padding:15px;
border-radius:15px;
width:200px;
}

/* ADD TRIP BUTTON */

.addCard{
display:flex;
justify-content:center;
box-size:10px;
align-items:center;
font-size:5px;
cursor:pointer;
}

</style>

</head>

<body>

<!-- TOP BAR -->

<div class="topbar">

<div class="profile"
onclick="location.href='Customer_homeServlet?action=profile'">

<img src="<%=request.getContextPath()%>/images/photo.jpg">

<b>Welcome, <%=user.getName()%></b>

</div>

<input class="search" placeholder="source">
<input class="search" placeholder="destination">

<button onclick="location.href='logoutServlet'">
Logout
</button>

</div>

<hr>

<h3>Trips</h3>

<div class="cards">

<!-- + ADD TRIP BUTTON -->

<div class="card addCard">

<a href="Customer_homeServlet?action=addTrip"
style="text-decoration:none;font-size:40px;color:black;">
+
</a>

</div>


<%
if(trips!=null){
for(Trip t:trips){
%>

<div class="card">

<p><b><%=t.getSource()%> → <%=t.getDestination()%></b></p>

<p>Date: <%= t.getStartDate() != null ? t.getStartDate() : "Not Set" %></p>

</div>

<%
}
}
%>

</div>


<!-- CREATE TRIP FORM -->

<%
if("addTrip".equals(action)){
%>

<h3>Create Trip</h3>

<form action="Customer_homeServlet" method="post">

<input type="hidden" name="action" value="createTrip">

Source
<input type="text" name="source" required>

Destination
<input type="text" name="destination" required>
Start Date
<input type="date" name="start_date" required>

End Date
<input type="date" name="end_date" required>


Duration
<input type="number" name="duration">

Price
<input type="number" name="price">

<button>Create Trip</button>

</form>

<%
}
%>



<h3>Previous Bookings</h3>

<div class="cards">

<%
if(bookings!=null){
for(Booking b:bookings){
%>

<div class="card">

<p><b>Booking <%=b.getBookingId()%></b></p>

<p><%=b.getSource()%> → <%=b.getDestination()%></p>

<p>Status: <%=b.getStatus()%></p>

</div>

<%
}
}
%>

</div>

</body>

</html>