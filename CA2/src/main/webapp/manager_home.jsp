<%@ page import="models.MizDooni" %>
<%@ page import="models.Reader" %>
<%@ page import="objects.Restaurant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en"><head>
    <meta charset="UTF-8">
    <title>Manager Home</title>
</head>
<body>
<%
    String username = (String)request.getSession().getAttribute("username");
%>
<h1>Welcome <%=username%> <a href="logout.jsp" style="color: red">Log Out</a></h1>

<%
    MizDooni mizDooni = new MizDooni();
    Restaurant restaurant = mizDooni.findRestaurantByManager(username);
    if (restaurant == null) {
        request.setAttribute("error", "you don't have any restaurants");
        response.sendRedirect("/error");
    }

    String tableHtml = mizDooni.createHTMLForTables(restaurant);
%>

<h2>Your Restaurant Information:</h2>
<ul>
    <li id="id">Id: <%=restaurant.id%></li>
    <li id="name">Name: <%=restaurant.name%></li>
    <li id="type">Type: <%=restaurant.type%></li>
    <li id="time">Time: <%=restaurant.startTime%> - <%=restaurant.endTime%></li>
    <li id="description">Description: <%=restaurant.description%></li>
    <li id="address">Address: <%=restaurant.street%>, <%=restaurant.city%>, <%=restaurant.country%></li>
    <li id="tables">Tables:</li>
    <ul>
        <%=tableHtml%>
    </ul>
</ul>

<table border="1" cellpadding="10">
    <tr>
        <td>

            <h3>Add Table:</h3>
            <form method="post" action="">
                <label>Table Number:</label>
                <input name="table_number" type="number" min="0"/>
                <br>
                <label>Seats Number:</label>
                <input name="seats_number" type="number" min="1"/>
                <br>
                <button type="submit">Add</button>
            </form>

        </td>
    </tr>
</table>


</body></html>
