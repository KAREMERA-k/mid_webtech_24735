package com.auca.library.service;

import org.mindrot.jbcrypt.BCrypt;
import com.auca.library.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {

    public boolean registerUser(String username, String password, String role, String membershipType) {
        // Check if the username already exists
        if (isUsernameTaken(username)) {
            System.out.println("Registration failed: Username already taken.");
            return false;
        }

        // Hash the password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("Attempting to register user: " + username);

        // Attempt to insert the new user
        String sql = "INSERT INTO users (username, password, role, membership_type) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, role);
            stmt.setString(4, membershipType);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Registration successful: Rows affected = " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Check if the username already exists in the database
    private boolean isUsernameTaken(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking username: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Authenticate user with password verification
    public boolean authenticate(String username, String password) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT password FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");
                return BCrypt.checkpw(password, storedHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get user's role by username
    public String getUserRole(String username) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT role FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
