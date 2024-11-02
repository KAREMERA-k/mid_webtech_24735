<%@ page import="java.util.List" %>
<%@ page import="com.auca.library.model.Book" %>

<%
    // Ensure availableBooks is retrieved from the request scope
    List<Book> availableBooks = (List<Book>) request.getAttribute("availableBooks");
%>

<% if (availableBooks != null && !availableBooks.isEmpty()) { %>
    <table border="1">
        <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Shelf ID</th>
            <th>Actions</th>
        </tr>
        <% for (Book book : availableBooks) { %>
            <tr>
                <td><%= book.getTitle() %></td>
                <td><%= book.getAuthor() %></td>
                <td><%= book.getShelfId() %></td>
                <td>
                    <form action="borrow.jsp" method="get" style="display:inline;">
                        <input type="hidden" name="bookId" value="<%= book.getId() %>">
                        <button type="submit">Borrow</button>
                    </form>
                    <form action="return.jsp" method="get" style="display:inline;">
                        <input type="hidden" name="bookId" value="<%= book.getId() %>">
                        <button type="submit">Return</button>
                    </form>
                </td>
            </tr>
        <% } %>
    </table>
<% } else { %>
    <p>No available books at the moment.</p>
<% } %>
