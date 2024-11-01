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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            registerUser(request, response);
        } else if ("login".equals(action)) {
            loginUser(request, response);
        } else if ("logout".equals(action)) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("login.jsp?status=logged_out");
        }
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
            session.setAttribute("role", authService.getUserRole(username));  // Get role from AuthService
            response.sendRedirect("dashboard.jsp");
        } else {
            response.sendRedirect("login.jsp?status=failed");
        }
    }
}
