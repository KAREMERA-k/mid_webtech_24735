<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - AUCA Library Management</title>
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
    <div class="login-container">
        <h2>Login</h2>
        <form action="UserController" method="post">
            <input type="hidden" name="action" value="login">

            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <button type="submit">Login</button>
        </form>
        <% if (request.getParameter("status") != null) { %>
            <p class="error">
                <c:choose>
                    <c:when test="${param.status == 'failed'}">Login failed. Please try again.</c:when>
                    <c:when test="${param.status == 'unauthorized'}">Unauthorized access. Please log in first.</c:when>
                </c:choose>
            </p>
        <% } %>
    </div>
</body>
</html>
