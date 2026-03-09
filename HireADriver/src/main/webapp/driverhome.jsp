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
	align-items: center;
	gap: 20px;
}

.profile {
	display: flex;
	align-items: center;
	gap: 10px;
	cursor: pointer;
}

.profile img {
	width: 40px;
	height: 40px;
	border-radius: 50%;
}

.search {
	padding: 8px;
	border-radius: 20px;
	border: none;
}

.cards {
	display: flex;
	gap: 20px;
	flex-wrap: wrap;
	margin-top: 20px;
}

.card {
	background: #ddd;
	color: black;
	padding: 15px;
	border-radius: 20px;
	width: 200px;
}
</style>

</head>

<body>

	<!-- TOP BAR -->

	<div class="topbar">

		<div class="profile" onclick="location.href='Driver_homeServlet?action=profile'">

			<img src="<%=request.getContextPath()%>/images/driver_pic.jpg">
			<b>Welcome, <%=driver.getName()%></b>
		
		</div>

		<input class="search" placeholder="source"> 
		<input class="search" placeholder="destination">

		<button onclick="location.href='logoutServlet'">Logout</button>

	</div>

	<hr>

	<h3>Live Trips</h3>

	<div class="cards">

		<%
		if (trips != null) {
			for (Trip t : trips) {
		%>

		<div class="card">

			<b>Trip <%=t.getTripId()%></b>

			<p>
				Source:
				<%=t.getSource()%></p>

			<p>
				Destination:
				<%=t.getDestination()%></p>

			<p>
				Date:
				<%=t.getStartDate()%></p>

			<form action="Driver_homeServlet" method="post">

				<input type="hidden" name="action" value="acceptTrip"> <input
					type="hidden" name="tripId" value="<%=t.getTripId()%>">

				Price: <input type="number" name="price">

				<button>Accept</button>

			</form>

			<form action="Driver_homeServlet" method="post">

				<input type="hidden" name="action" value="rejectTrip"> <input
					type="hidden" name="tripId" value="<%=t.getTripId()%>">

				<button>Reject</button>

			</form>

		</div>

		<%
		}
		}
		%>

	</div>


	<h3>Previous Bookings</h3>

	<div class="cards">

		<%
		if (bookings != null) {
			for (Booking b : bookings) {
		%>

		<div class="card">

			<b>Booking <%=b.getBookingId()%></b>

			<p>
				Source:
				<%=b.getSource()%></p>

			<p>
				Destination:
				<%=b.getDestination()%></p>

			<p>
				Customer:
				<%=b.getCustomerName()%></p>

			<p>
				Date:
				<%=b.getStartDate()%></p>

		</div>

		<%
		}
		}
		%>

	</div>

</body>
</html>