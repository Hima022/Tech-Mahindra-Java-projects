<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<link rel="stylesheet" href="RegistrationStyle.css">
</head>

<body>

	<h1>Registration For Hire A Driver</h1>

	<form action="RegistrationServlet" method="post">

		Name: <input type="text" name="name" required>
		
		Phone: <input type="text" name="phone" required> 
		
		Email: <input type="email" name="email" required> 
		
		Password: <input type="password" name="password" required> 
		
		Age: <input type="number" name="age" required> 
		
		Gender: <select name="gender">
			<option value="MALE">Male</option>
			<option value="FEMALE">Female</option>
			<option value="OTHER">Other</option>
		</select> 
		
		User Type: <select name="user_type">
			<option value="CUSTOMER">Customer</option>
			<option value="DRIVER">Driver</option>
		</select>

		<button type="submit">Register</button>

	</form>

	<%
	if (request.getAttribute("error") != null) {
	%>
	<p style="color: red;"><%=request.getAttribute("error")%></p>
	<%
	}
	%>

	<%
	if (request.getAttribute("success") != null) {
	%>
	<p style="color: green;"><%=request.getAttribute("success")%></p>
	<%
	}
	%>

	<br>
	<a href="login.jsp">Already have an account? Login</a>

</body>
</html>