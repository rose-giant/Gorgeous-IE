<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tables</title>
</head>
<body>
<p id="username">username: ali <a href="/">Home</a> <a href="logout.jsp" style="color: red">Log Out</a></p>
<br><br>
<form action="" method="POST">
    <label>Search:</label>
    <input type="text" name="search" value="">
    <button type="submit" name="action" value="search_by_type">Search By Type</button>
    <button type="submit" name="action" value="search_by_name">Search By Name</button>
    <button type="submit" name="action" value="search_by_city">Search By City</button>
    <button type="submit" name="action" value="clear">Clear Search</button>
</form>
<br><br>
<form action="" method="POST">
    <label>Sort By:</label>
    <button type="submit" name="action" value="sort_by_rate">Overall Score</button>
</form>
<br><br>
<table style="width:100%; text-align:center;" border="1">
    <tr>
        <th>Number</th>
        <th>seat number</th>
    </tr>
</table>
</body>
</html>