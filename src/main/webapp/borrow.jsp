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
