<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tmf.servlets.entity.User"%>

<%
User driver = (User) request.getAttribute("driver");
%>

<h2>Driver Profile</h2>

<form action="Driver_homeServlet" method="post" enctype="multipart/form-data">

<input type="hidden" name="action" value="updateProfile">

Name
<input type="text" name="name" value="<%=driver.getName()%>">

Email
<input type="email" name="email" value="<%=driver.getEmail()%>">

Phone
<input type="text" name="phone" value="<%=driver.getPhone()%>">

Experience
<input type="number" name="experience" value="<%=driver.getExperience()%>">

Upload Driving License
<input type="file" name="license">

Upload ID Proof
<input type="file" name="idproof">

<button>Save Changes</button>

</form>