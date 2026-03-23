<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<html>
<head>
<title>Profile</title>

<style>
body {
	margin: 0;
	font-family: 'Segoe UI';
	background: #f5f7fa;
}

/* NAVBAR */
.navbar {
	background: linear-gradient(135deg, #4facfe, #00f2fe);
	padding: 15px 25px;
	display: flex;
	justify-content: space-between;
	color: white;
}

.container {
	display: flex;
	gap: 30px;
	padding: 30px;
}

/* LEFT */
.left {
	background: white;
	padding: 20px;
	border-radius: 10px;
	width: 250px;
	text-align: center;
}

.left img {
	width: 100px;
	height: 100px;
	border-radius: 50%;
}

/* RIGHT */
.right {
	background: white;
	padding: 20px;
	border-radius: 10px;
	width: 400px;
}

input {
	width: 100%;
	padding: 10px;
	margin: 10px 0;
}

button {
	background: #007bff;
	color: white;
	padding: 10px;
	border: none;
	width: 100%;
}

.success {
	color: green;
	font-weight: bold;
}
</style>

</head>

<body>

	<div class="navbar">
		<div>Hire-A-Driver</div>
		<a href="Customer_homeServlet" style="color: white;">← Back</a>
	</div>

	<div class="container">

		<!-- LEFT PROFILE -->
		<div class="left">

			<img
				src="images/${empty user.profileImage ? 'default.png' : user.profileImage}">

			<h3>${user.name}</h3>
			<p>${user.email}</p>
			<p>${user.phone}</p>

		</div>

		<!-- RIGHT FORM -->
		<div class="right">

			<h3>Update Profile</h3>

			<!-- SUCCESS -->
			<c:if test="${param.success == 'updated'}">
				<p class="success">Profile Updated Successfully ✅</p>
			</c:if>

			<form action="Customer_homeServlet" method="post"
				enctype="multipart/form-data">

				<input type="hidden" name="action" value="updateProfile"> <input
					type="text" name="name" value="${user.name}" placeholder="Name">

				<input type="email" name="email" value="${user.email}"> <input
					type="text" name="phone" value="${user.phone}"> <input
					type="number" name="age" value="${user.age}"> <input
					type="password" name="password" placeholder="New Password">

				<input type="file" name="profileImage">

				<button>Update Profile</button>

			</form>

		</div>

	</div>

</body>
</html>