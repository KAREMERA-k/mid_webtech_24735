package com.auca.library.service;

import com.auca.library.model.Book;
import com.auca.library.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class BookService {

    // Existing methods...

    public List<Book> getAvailableBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE availability = 1";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setShelfId(rs.getInt("shelf_id"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public boolean borrowBook(String username, int bookId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String userIdQuery = "SELECT id, membership_type FROM users WHERE username = ?";
            PreparedStatement userStmt = conn.prepareStatement(userIdQuery);
            userStmt.setString(1, username);
            ResultSet userResult = userStmt.executeQuery();

            if (userResult.next()) {
                int userId = userResult.getInt("id");
                String membershipType = userResult.getString("membership_type");
                int maxBooks = membershipType.equals("Gold") ? 5 : membershipType.equals("Silver") ? 3 : 2;

                String borrowCountQuery = "SELECT COUNT(*) FROM transactions WHERE user_id = ? AND return_date IS NULL";
                PreparedStatement countStmt = conn.prepareStatement(borrowCountQuery);
                countStmt.setInt(1, userId);
                ResultSet countResult = countStmt.executeQuery();
                countResult.next();
                int borrowedBooks = countResult.getInt(1);

                if (borrowedBooks >= maxBooks) {
                    return false;  // User has reached their borrow limit
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

    public boolean returnBook(String username, int bookId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String userIdQuery = "SELECT id FROM users WHERE username = ?";
            PreparedStatement userStmt = conn.prepareStatement(userIdQuery);
            userStmt.setString(1, username);
            ResultSet userResult = userStmt.executeQuery();

            if (userResult.next()) {
                int userId = userResult.getInt("id");

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

    public boolean addBook(String title, String author, String isbn, int shelfId) {
        String sql = "INSERT INTO books (title, author, isbn, shelf_id, availability) VALUES (?, ?, ?, ?, 1)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, isbn);
            stmt.setInt(4, shelfId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
