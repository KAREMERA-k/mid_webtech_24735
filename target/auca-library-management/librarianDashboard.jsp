<h2>Librarian Dashboard - Manage Books</h2>

<form action="BookController" method="post">
    <input type="hidden" name="action" value="add">
    <input type="text" name="title" placeholder="Book Title" required>
    <input type="text" name="author" placeholder="Author" required>
    <input type="number" name="copies" placeholder="Number of Copies" min="1" required>
    <button type="submit">Add Book</button>
</form>

<form action="BookController" method="post">
    <input type="hidden" name="action" value="delete">
    <input type="number" name="bookId" placeholder="Book ID" required>
    <button type="submit">Delete Book</button>
</form>

<form action="BookController" method="post">
    <input type="hidden" name="action" value="update">
    <input type="number" name="bookId" placeholder="Book ID" required>
    <input type="number" name="copies" placeholder="New Number of Copies" min="1" required>
    <button type="submit">Update Copies</button>
</form>
