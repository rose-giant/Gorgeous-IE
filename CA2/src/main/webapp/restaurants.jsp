<%@ page import="models.MizDooni" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restaurants</title>
</head>
<body>
<%
    String username = (String) request.getSession().getAttribute("username");
%>
<p id="username">username: <%=username%> <a href="${pageContext.request.contextPath}/client_home">Home</a> <a href="${pageContext.request.contextPath}/logout" style="color: red">Log Out</a></p>
<br><br>
<form action="${pageContext.request.contextPath}/filterRestaurants" method="POST">
    <label>Search:</label>
    <input type="text" name="search" value="">
    <button type="submit" name="action" value="search_by_type">Search By Type</button>
    <button type="submit" name="action" value="search_by_name">Search By Name</button>
    <button type="submit" name="action" value="search_by_city">Search By City</button>
    <button type="submit" name="action" value="clear" onclick="location.reload();" >Clear Search</button>
    <br><br>
    <button type="submit" name="action" value="sort_by_score">sort by score</button>
</form>

<%
    String action = request.getParameter("action");
    String search = request.getParameter("search");
    if (action == null) {
        action = "";
    }
    if (search == null) {
        search = "";
    }

    MizDooni mizDooni = new MizDooni();
    String restaurantsHtml = mizDooni.createHTMLForRestaurantsList(action, search);
%>

<br><br>
<table style="width:100%; text-align:center;" border="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>City</th>
        <th>Type</th>
        <th>Time</th>
        <th>Service Score</th>
        <th>Food Score</th>
        <th>Ambiance Score</th>
        <th>Overall Score</th>
    </tr>

    <%= restaurantsHtml %>
</table>


</body>
</html>
