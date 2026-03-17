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

.profile {
	display: flex;
	align-items: center;
	gap: 10px;
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
	width: 230px;
}

button {
	padding: 7px 12px;
	margin-top: 8px;
	background: #007BFF;
	color: white;
	border: none;
	cursor: pointer;
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

	<!-- TOP BAR -->

	<div class="topbar">

		<div class="profile">

			<img src="<%=request.getContextPath()%>/images/driver_pic.jpg"
				width="40">

			
			<h2>Hire-A-Driver | Driver Dashboard
				<br>
				Welcome <%=driver.getName()%></h2>

		</div>

		<div>
			<a href="logoutServlet">
				<button>Logout</button>
			</a>
		</div>

	</div>

	<hr>


	<h3>Available Trips (Customer Requests)</h3>

	<div class="cards">

		<%
		if (trips != null) {
			for (Trip t : trips) {
		%>

		<div class="card">

			<b>Trip ID: <%=t.getTripId()%></b>

			<p>
				<b>Source:</b>
				<%=t.getSource()%></p>

			<p>
				<b>Destination:</b>
				<%=t.getDestination()%></p>

			<p>
				<b>Date:</b>
				<%=t.getStartDate()%></p>

			<p>
				<b>Price:</b> ₹<%=t.getPrice()%></p>


			<!-- ACCEPT TRIP -->

			<form action="Driver_homeServlet" method="post">

				<input type="hidden" name="action" value="acceptTrip"> <input
					type="hidden" name="tripId" value="<%=t.getTripId()%>">

				<button class="accept">Accept</button>

			</form>


			<!-- REJECT TRIP -->

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


	<hr>


	<h3>Your Accepted Bookings</h3>

	<div class="cards">

		<%
		if (bookings != null) {
			for (Booking b : bookings) {
		%>

		<div class="card">

			<b>Booking ID: <%=b.getBookingId()%></b>

			<p>
				<b>Source:</b>
				<%=b.getSource()%></p>

			<p>
				<b>Destination:</b>
				<%=b.getDestination()%></p>

			<p>
				<b>Status:</b>
				<%=b.getStatus()%></p>

			<p>
				<b>Price:</b> ₹<%=b.getPrice()%></p>

		</div>

		<%
}
}
%>

	</div>

</body>
</html>