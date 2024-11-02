<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Locations List</title>
</head>
<body>
    <h2>Locations List</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Province</th>
            <th>District</th>
            <th>Sector</th>
            <th>Cell</th>
            <th>Village</th>
        </tr>
        <c:forEach var="location" items="${locations}">
            <tr>
                <td>${location.id}</td>
                <td>${location.province}</td>
                <td>${location.district}</td>
                <td>${location.sector}</td>
                <td>${location.cell}</td>
                <td>${location.village}</td>
            </tr>
        </c:forEach>
    </table>
    <a href="dashboard.jsp">Back to Dashboard</a>
</body>
</html>
