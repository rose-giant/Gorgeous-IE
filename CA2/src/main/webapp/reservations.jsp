<%@ page import="models.MizDooni" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="objects.Reservation" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reservations</title>
</head>
<body>

<%
    MizDooni mizDooni = new MizDooni();
    String username = mizDooni.getActiveUser();
    ArrayList<Reservation> reservations = mizDooni.getUserReservations(username);
    String reservationsHtml = mizDooni.createHtmlForUserReservations(reservations);
%>

<p id="username">username: <%=username%> <a href="/">Home</a> <a href="logout.jsp" style="color: red">Log Out</a></p>
<h1>Your Reservations:</h1>
<br><br>
<br><br>
<table style="width:100%; text-align:center;" border="1">
    <tr>
        <th>Reservation Id</th>
        <th>Resturant Name</th>
        <th>Table Number</th>
        <th>Date & Time</th>
        <th>Canceling</th>
    </tr>


    <%=reservationsHtml%>


    <%
        String number = request.getParameter("action");
        mizDooni.cancelReservation(number);
    %>

</table>
</body>
</html>