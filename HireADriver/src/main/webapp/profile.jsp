<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tmf.servlets.entity.User"%>

<%
User user = (User) request.getAttribute("user");
%>

<html>
<head>

<title>Profile</title>

<style>

body{
font-family:Arial;
background:#f5f5f5;
padding:40px;
}

.box{
background:white;
padding:30px;
width:350px;
border-radius:15px;
}

input{
width:100%;
padding:8px;
margin:8px 0;
}

</style>

</head>

<body>

<div class="box">

<h2>My Profile</h2>

<form action="Customer_homeServlet" method="post" enctype="multipart/form-data">

<input type="hidden" name="action" value="updateProfile">

Name:
<input type="text" name="name" value="<%=user.getName()%>">

Email:
<input type="email" name="email" value="<%=user.getEmail()%>">

Phone:
<input type="text" name="phone" value="<%=user.getPhone()%>">

Age:
<input type="number" name="age" value="<%=user.getAge()%>">

Profile Image:
<input type="file" name="profileImage">

<button>Update Profile</button>

</form>

</div>

</body>
</html>