<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String username = request.getParameter("username");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>400 Error</title>
</head>
<body style="text-align:center">
<h1>400<br>No user with username <%=username%> exists</h1>
</body>
</html>
