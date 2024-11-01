<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Assign Book - AUCA Library Management</title>
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
    <div class="form-container">
        <h2>Assign Book to Shelf</h2>
        <form action="BookController" method="post">
            <input type="hidden" name="action" value="assign">

            <label for="bookId">Book ID:</label>
            <input type="text" id="bookId" name="bookId" required>

            <label for="shelfId">Shelf ID:</label>
            <input type="text" id="shelfId" name="shelfId" required>

            <button type="submit">Assign Book</button>
        </form>
    </div>
</body>
</html>
