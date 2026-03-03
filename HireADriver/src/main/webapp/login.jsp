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
    <table>
        <tr>
            <th colspan="2" align="center">
                <h2>Login Page</h2>
            </th>
        </tr>

        <tr>
            <td>User name:</td>
            <td><input name="uname" type="text" required></td>
        </tr>

        <tr>
            <td>Password:</td>
            <td><input name="password" type="password" required></td>
        </tr>

        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Login">
            </td>
        </tr>

        <% if (request.getAttribute("error") != null) { %>
        <tr>
            <td colspan="2" align="center" style="color:red;">
                <%= request.getAttribute("error") %>
            </td>
        </tr>
        <% } %>

    </table>
    </form>

</div>

</body>
</html>