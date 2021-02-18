<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04-Feb-21
  Time: 2:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<h1>All User list</h1>

<table border="1", width="400", cellspacing="2", cellpadding="2">
    <tr>
        <td>Name</td>
        <td>Email</td>
        <td>Country</td>
    </tr>
    <c:forEach items="${list}" var="user">
        <tr>
            <td>${user.getName()}</td>
            <td>${user.getEmail()}</td>
            <td>${user.getCountry()}</td>
            <td>
                <a href="/users?action=edit&id=${user.id}">Edit</a>
                <a href="/users?action=delete&id=${user.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<h2>
    <a href="/users?action=create">Add New User</a>
</h2>
<h2>
    <a href="/users?action=search">Search User</a>
</h2>

</body>
</html>
