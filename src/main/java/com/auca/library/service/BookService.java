package com.auca.library.service;

import com.auca.library.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookService {

    public boolean borrowBook(int userId, int bookId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String membershipTypeQuery = "SELECT membership_type FROM users WHERE id = ?";
            PreparedStatement memStmt = conn.prepareStatement(membershipTypeQuery);
            memStmt.setInt(1, userId);
            ResultSet memResult = memStmt.executeQuery();

            if (memResult.next()) {
                String membershipType = memResult.getString("membership_type");
                int maxBooks = membershipType.equals("Gold") ? 5 : membershipType.equals("Silver") ? 3 : 2;

                String borrowCountQuery = "SELECT COUNT(*) FROM transactions WHERE user_id = ? AND return_date IS NULL";
                PreparedStatement countStmt = conn.prepareStatement(borrowCountQuery);
                countStmt.setInt(1, userId);
                ResultSet countResult = countStmt.executeQuery();
                countResult.next();
                int borrowedBooks = countResult.getInt(1);

                if (borrowedBooks >= maxBooks) {
                    return false;
                }

                String sql = "INSERT INTO transactions (user_id, book_id, borrow_date) VALUES (?, ?, CURDATE())";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userId);
                stmt.setInt(2, bookId);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean returnBook(int userId, int bookId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT borrow_date, membership_type FROM transactions t JOIN users u ON t.user_id = u.id WHERE t.user_id = ? AND t.book_id = ? AND return_date IS NULL";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LocalDate borrowDate = rs.getDate("borrow_date").toLocalDate();
                String membershipType = rs.getString("membership_type");
                LocalDate returnDate = LocalDate.now();

                FineService fineService = new FineService();
                double fine = fineService.calculateFine(borrowDate, returnDate, membershipType);

                String updateSql = "UPDATE transactions SET return_date = CURDATE(), fine = ? WHERE user_id = ? AND book_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setDouble(1, fine);
                updateStmt.setInt(2, userId);
                updateStmt.setInt(3, bookId);

                return updateStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean assignBookToShelf(int bookId, int shelfId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "UPDATE books SET shelf_id = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shelfId);
            stmt.setInt(2, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
