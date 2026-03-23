<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="LoginStyle.css">
</head>
<body>

<div class="container">

<form action="LoginServlet" method="post">

<h2>Login</h2>

Email:
<input type="email" name="email" required>

Password:
<input type="password" name="password" required>

<input type="submit" value="Login">

</form>

</div>

</body>
</html>