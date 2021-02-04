<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04-Feb-21
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Create Form</title>
</head>
<body>
<h2>
    <a href="/users">List All user</a>

</h2>
<form method="post">
    <table border="1" cellpadding="5">
        <caption>
            <h2>Add New User</h2>
        </caption>
        <tr>
            <th>User Name:</th>
            <td>
                <input type="text" name="name" id="name" size="45"/>
            </td>
        </tr>
        <tr>
            <th>User Email:</th>
            <td>
                <input type="text" name="email" id="email" size="45"/>
            </td>
        </tr>
        <tr>
            <th>Country:</th>
            <td>
                <input type="text" name="country" id="country" size="15"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Save"/>
            </td>
        </tr>
    </table>
</form>

</body>
</html>
