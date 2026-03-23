<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.tmf.servlets.entity.*"%>

<%
User driver = (User) request.getAttribute("driver");
List<Trip> trips = (List<Trip>) request.getAttribute("trips");
List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
%>

<html>
<head>
<title>Driver Dashboard</title>

<style>
body {
	margin: 0;
	font-family: 'Segoe UI';
	background: #eef2f7;
}

.navbar {
	background: #4facfe;
	padding: 15px;
	display: flex;
	justify-content: space-between;
	color: white;
}

.cards {
	display: flex;
	flex-wrap: wrap;
	gap: 20px;
	padding: 20px;
}

.card {
	background: white;
	padding: 15px;
	border-radius: 10px;
	width: 240px;
}

button {
	width: 100%;
	padding: 8px;
	border: none;
	margin-top: 5px;
	color: white;
}

.accept {
	background: green;
}

.reject {
	background: red;
}
</style>

</head>

<body>

	<div class="navbar">
		<h2>Driver Dashboard</h2>
		<a href="logoutServlet" style="color: white;">Logout</a>
	</div>

	<div style="padding: 20px;">
		<h3>
			Welcome,
			<%=driver.getName()%></h3>
	</div>

	<h3 style="padding-left: 20px;">Available Trips</h3>

	<div class="cards">

		<%
		if (trips != null) {
			for (Trip t : trips) {
		%>

		<div class="card">

			<b><%=t.getSource()%> → <%=t.getDestination()%></b>

			<p>
				Date:
				<%=t.getStartDate()%></p>
			<p>
				₹
				<%=t.getPrice()%></p>

			<form action="Driver_homeServlet" method="post">
				<input type="hidden" name="action" value="acceptTrip"> <input
					type="hidden" name="tripId" value="<%=t.getTripId()%>">
				<button class="accept">Accept</button>
			</form>

			<form action="Driver_homeServlet" method="post">
				<input type="hidden" name="action" value="rejectTrip"> <input
					type="hidden" name="tripId" value="<%=t.getTripId()%>">
				<button class="reject">Reject</button>
			</form>

		</div>

		<%
		}
		}
		%>

	</div>

	<h3 style="padding-left: 20px;">Your Bookings</h3>

	<div class="cards">

		<%
		if (bookings != null) {
			for (Booking b : bookings) {
		%>

		<div class="card">

			<b>Booking #<%=b.getBookingId()%></b>

			<p><%=b.getSource()%>
				→
				<%=b.getDestination()%></p>

			<p>
				Status:
				<%=b.getStatus()%></p>

			<p>
				₹
				<%=b.getPrice()%></p>

		</div>

		<%
}}
%>

	</div>

</body>
</html>