package com.auca.library.controller;

import com.auca.library.service.BookService;
import com.auca.library.service.FineService;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class BookController extends HttpServlet {
    private BookService bookService;
    private FineService fineService;

    @Override
    public void init() {
        bookService = new BookService();
        fineService = new FineService();
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

    private void borrowBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        boolean isBorrowed = bookService.borrowBook(userId, bookId);

        if (isBorrowed) {
            response.sendRedirect("dashboard.jsp?status=borrow_success");
        } else {
            response.sendRedirect("borrow.jsp?status=borrow_limit_exceeded");
        }
    }

    private void returnBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        boolean isReturned = bookService.returnBook(userId, bookId);

        if (isReturned) {
            response.sendRedirect("dashboard.jsp?status=return_success");
        } else {
            response.sendRedirect("dashboard.jsp?status=return_failed");
        }
    }

    private void assignBookToShelf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int shelfId = Integer.parseInt(request.getParameter("shelfId"));

        boolean isAssigned = bookService.assignBookToShelf(bookId, shelfId);

        if (isAssigned) {
            response.sendRedirect("dashboard.jsp?status=assignment_success");
        } else {
            response.sendRedirect("assign_books.jsp?status=assignment_failed");
        }
    }
}
