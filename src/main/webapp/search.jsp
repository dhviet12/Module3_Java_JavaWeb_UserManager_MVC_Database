<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05-Feb-21
  Time: 9:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Search form</title>
</head>
<body>
<h3>
    <a href="/users">Back to All user List</a>
</h3>

<h1>Enter country of User you want to find</h1>
<form method="post">
    <input type="text" name="country" id="country" placeholder="enter country ">
    <input type="submit" value="search">
</form>

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
    </tr>
</c:forEach>
</table>

</body>
</html>
