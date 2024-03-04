<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en"><head>
    <meta charset="UTF-8">
    <title>Client Home</title>
</head>
<body>
<%
    String username = (String)request.getSession().getAttribute("username");
%>
<h1>Welcome <%=username%> <a href="/" style="color: red">Log Out</a></h1>

<ul type="square">
    <li>
        <a href="/restaurants">Restaurants</a>
    </li>
    <li>
        <a href="/reservations">Reservations</a>
    </li>
</ul>


</body></html>
