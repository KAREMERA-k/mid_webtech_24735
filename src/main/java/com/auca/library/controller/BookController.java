package com.auca.library.controller;

import com.auca.library.model.Book;
import com.auca.library.service.BookService;
import com.auca.library.service.FineService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class BookController extends HttpServlet {
    private BookService bookService;
    private FineService fineService;

    @Override
    public void init() {
        bookService = new BookService();
        fineService = new FineService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch the list of available books and set it as a request attribute
        List<Book> availableBooks = bookService.getAvailableBooks();
        request.setAttribute("availableBooks", availableBooks);

        // Forward the request to the student dashboard JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("studentDashboard.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("borrow".equals(action)) {
            borrowBook(request, response);
        } else if ("return".equals(action)) {
            returnBook(request, response);
        } else if ("assign".equals(action)) {
            assignBookToShelf(request, response);
        }
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        int shelfId = Integer.parseInt(request.getParameter("shelfId"));

        boolean isAdded = bookService.addBook(title, author, isbn, shelfId);

        if (isAdded) {
            response.sendRedirect("librarianDashboard.jsp?status=add_success");
        } else {
            response.sendRedirect("librarianDashboard.jsp?status=add_failed");
        }
    }

    private void borrowBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        boolean isBorrowed = bookService.borrowBook(username, bookId);

        if (isBorrowed) {
            response.sendRedirect("studentDashboard.jsp?status=borrow_success");
        } else {
            response.sendRedirect("borrow.jsp?status=borrow_limit_exceeded");
        }
    }

    private void returnBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        boolean isReturned = bookService.returnBook(username, bookId);

        if (isReturned) {
            response.sendRedirect("studentDashboard.jsp?status=return_success");
        } else {
            response.sendRedirect("studentDashboard.jsp?status=return_failed");
        }
    }

    private void assignBookToShelf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int shelfId = Integer.parseInt(request.getParameter("shelfId"));

        boolean isAssigned = bookService.assignBookToShelf(bookId, shelfId);

        if (isAssigned) {
            response.sendRedirect("librarianDashboard.jsp?status=assignment_success");
        } else {
            response.sendRedirect("assign_books.jsp?status=assignment_failed");
        }
    }
}
