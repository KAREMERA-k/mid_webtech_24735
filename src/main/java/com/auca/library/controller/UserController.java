package com.auca.library.controller;

import com.auca.library.service.AuthService;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UserController extends HttpServlet {
    private AuthService authService;

    @Override
    public void init() {
        authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            logoutUser(request, response);
        } else {
            // Handle other GET actions if needed
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            registerUser(request, response);
        } else if ("login".equals(action)) {
            loginUser(request, response);
        } else if ("logout".equals(action)) {
            logoutUser(request, response);
        }
    }

    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();  // Invalidate the session to log out the user
        response.sendRedirect("login.jsp?status=logged_out");  // Redirect to login page after logout
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String membershipType = request.getParameter("membership");

        boolean isRegistered = authService.registerUser(username, password, role, membershipType);

        if (isRegistered) {
            response.sendRedirect("login.jsp?status=registered");
        } else {
            response.sendRedirect("register.jsp?status=failed");
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isAuthenticated = authService.authenticate(username, password);

        if (isAuthenticated) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            String role = authService.getUserRole(username);
            session.setAttribute("role", role);

            if ("Student".equals(role)) {
                response.sendRedirect("studentDashboard.jsp");
            } else if ("Librarian".equals(role)) {
                response.sendRedirect("librarianDashboard.jsp");
            } else {
                response.sendRedirect("login.jsp?status=role_not_found");
            }
        } else {
            response.sendRedirect("login.jsp?status=failed");
        }
    }

}
