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

<label>Uname:</label>
<input type="text" name="uname" required>

<label>First Name:</label>
<input type="text" name="first_name" required>

<label>Last Name:</label>
<input type="text" name="last_name" required>

<label>Email:</label>
<input type="email" name="email" required>

<label>Phone:</label>
<input type="text" name="phone_number" required>

<label>Password:</label>
<input type="password" name="password" required>

<label>Gender:</label>
<select name="gender" required>
  <option value="MALE">Male</option>
  <option value="FEMALE">Female</option>
  <option value="OTHERS">Others</option>
</select>

<label>User Type:</label>
<select name="user_type" required>
  <option value="CUSTOMER">Customer</option>
  <option value="DRIVER">Driver</option>
</select>

<label>License Number (Only for Driver):</label>
<input type="text" name="license_number">

<label>DOB:</label>
<input type="date" name="dob" required>

<br><br>
<button type="submit">Register</button>

</form>

<!--Dynamic Message Section -->

<% if (request.getAttribute("error") != null) { %>
    <p style="color:red;">
        <%= request.getAttribute("error") %>
    </p>
<% } %>

<% if (request.getAttribute("success") != null) { %>
    <p style="color:green;">
        <%= request.getAttribute("success") %>
    </p>
<% } %>

<br>
<a href="login.jsp">Already have an account? Login</a>

</body>
</html>