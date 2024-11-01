<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - AUCA Library Management</title>
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
    <div class="dashboard">
        <h2>Welcome, ${sessionScope.username}</h2>

        <c:choose>
            <c:when test="${sessionScope.role == 'Librarian'}">
                <h3>Librarian Actions</h3>
                <a href="manage_books.jsp">Manage Books</a>
                <a href="approve_members.jsp">Approve Memberships</a>
                <a href="assign_books.jsp">Assign Books to Shelves</a>
            </c:when>

            <c:when test="${sessionScope.role == 'Student' || sessionScope.role == 'Teacher'}">
                <h3>Member Actions</h3>
                <a href="borrow.jsp">Borrow a Book</a>
                <a href="return.jsp">Return a Book</a>
            </c:when>

            <c:otherwise>
                <p>Access Denied. Please contact an administrator.</p>
            </c:otherwise>
        </c:choose>

        <a href="UserController?action=logout">Logout</a>
    </div>
</body>
</html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Borrow a Book - AUCA Library Management</title>
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
    <div class="form-container">
        <h2>Borrow a Book</h2>
        <form action="BookController" method="post">
            <input type="hidden" name="action" value="borrow">

            <label for="userId">User ID:</label>
            <input type="text" id="userId" name="userId" required>

            <label for="bookId">Book ID:</label>
            <input type="text" id="bookId" name="bookId" required>

            <button type="submit">Borrow</button>
        </form>
    </div>
</body>
</html>
