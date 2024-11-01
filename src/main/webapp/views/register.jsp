<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - AUCA Library Management</title>
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
    <div class="form-container">
        <h2>Register</h2>
        <form action="UserController" method="post">
            <input type="hidden" name="action" value="register">

            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <label for="role">Role:</label>
            <select id="role" name="role" required>
                <option value="Student">Student</option>
                <option value="Teacher">Teacher</option>
                <option value="Librarian">Librarian</option>
            </select>

            <label for="membership">Membership Type:</label>
            <select id="membership" name="membership" required>
                <option value="Gold">Gold - 50 Rwf/day, 5 books</option>
                <option value="Silver">Silver - 30 Rwf/day, 3 books</option>
                <option value="Striver">Striver - 10 Rwf/day, 2 books</option>
            </select>

            <button type="submit">Register</button>
        </form>
    </div>
</body>
</html>
