<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ page import="java.util.List"%>
<%@ page import="com.tmf.servlets.entity.*"%>

<%@ taglib prefix="hd" uri="http://hireadrive/tags"%>

<%
User user = (User) request.getAttribute("user");
List<Trip> trips = (List<Trip>) request.getAttribute("trips");
String action = request.getParameter("action");
%>

<html>

<head>

<title>Customer Dashboard</title>

<style>
body {
	background: black;
	color: white;
	font-family: Arial;
	padding: 20px;
}

.topbar {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.cards {
	display: flex;
	flex-wrap: wrap;
	gap: 20px;
	margin-top: 20px;
}

.card {
	background: white;
	color: black;
	padding: 15px;
	border-radius: 10px;
	width: 220px;
}

button {
	padding: 8px 15px;
	background: #007BFF;
	color: white;
	border: none;
	margin-top: 10px;
	cursor: pointer;
}
</style>

</head>

<body>

	<div class="topbar">

		<div>
			<h2>Hire-A-Driver | Customer Dashboard</h2>
			<p>
				Welcome <b><%=user.getName()%></b>
			</p>
		</div>

		<div>
			<a href="logoutServlet">
				<button>Logout</button>
			</a>
		</div>

	</div>

	<hr>

	<h3>Your Trips</h3>

	<div class="cards">

		<div class="card">
			<a href="Customer_homeServlet?action=addTrip">+ Add Trip</a>
		</div>

		<%
		if (trips != null) {
			for (Trip t : trips) {
		%>

		<div class="card">

			<b><%=t.getSource()%> → <%=t.getDestination()%></b>

			<p>
				Date :
				<%=t.getStartDate()%></p>

			<p>
				Price : ₹<%=t.getPrice()%></p>

			<!-- BOOK DRIVER BUTTON -->

			<form action="CreateBookingServlet" method="post">

				<input type="hidden" name="tripId" value="<%=t.getTripId()%>">
				<input type="hidden" name="driverId" value="1">

				<button>Book Driver</button>

			</form>
		</div>

		<%
		}
		}
		%>

	</div>


	<%
	if ("addTrip".equals(action)) {
	%>

	<h3>Create Trip</h3>

	<form action="Customer_homeServlet" method="post">

		<input type="hidden" name="action" value="createTrip"> Source
		<input type="text" name="source"> Destination <input
			type="text" name="destination"> Start Date <input type="date"
			name="start_date"> End Date <input type="date"
			name="end_date"> Duration <input type="number"
			name="duration"> Price <input type="number" name="price">

		<button>Create Trip</button>

	</form>

	<%
	}
	%>


	<h3>Previous Bookings</h3>

	<table>
		

		<hd:bookingTable bookings="${bookingsList}" />

	</table>

</body>

</html>