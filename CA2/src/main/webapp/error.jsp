<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String username = (String)request.getSession().getAttribute("username");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>400 Error</title>
</head>
<body style="text-align:center">
<%
    String errorMessage = (String) request.getSession().getAttribute("error");
%>
<h1><%=errorMessage%></h1>
</body>
</html>
