package com.auca.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:sqlite:auca_library.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            // Create tables
            String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY," +
                    "username TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL," +
                    "role TEXT NOT NULL," +
                    "membership_type TEXT DEFAULT 'Striver'," +
                    "phone_number TEXT," +
                    "province TEXT," +
                    "district TEXT," +
                    "sector TEXT," +
                    "cell TEXT," +
                    "village TEXT," +
                    "created_at TEXT DEFAULT CURRENT_TIMESTAMP" +
                    ");";
            stmt.execute(usersTable);

            String booksTable = "CREATE TABLE IF NOT EXISTS books (" +
                    "id INTEGER PRIMARY KEY," +
                    "title TEXT NOT NULL," +
                    "author TEXT," +
                    "isbn TEXT UNIQUE," +
                    "shelf_id INTEGER," +
                    "availability INTEGER DEFAULT 1" +
                    ");";
            stmt.execute(booksTable);

            String shelvesTable = "CREATE TABLE IF NOT EXISTS shelves (" +
                    "id INTEGER PRIMARY KEY," +
                    "room_id INTEGER," +
                    "description TEXT" +
                    ");";
            stmt.execute(shelvesTable);

            String roomsTable = "CREATE TABLE IF NOT EXISTS rooms (" +
                    "id INTEGER PRIMARY KEY," +
                    "location TEXT," +
                    "capacity INTEGER" +
                    ");";
            stmt.execute(roomsTable);

            String locationsTable = "CREATE TABLE IF NOT EXISTS locations (" +
                    "id INTEGER PRIMARY KEY," +
                    "province TEXT," +
                    "district TEXT," +
                    "sector TEXT," +
                    "cell TEXT," +
                    "village TEXT" +
                    ");";
            stmt.execute(locationsTable);

            String transactionsTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                    "id INTEGER PRIMARY KEY," +
                    "user_id INTEGER NOT NULL," +
                    "book_id INTEGER NOT NULL," +
                    "borrow_date TEXT DEFAULT CURRENT_TIMESTAMP," +
                    "return_date TEXT," +
                    "fine REAL DEFAULT 0.00," +
                    "FOREIGN KEY (user_id) REFERENCES users(id)," +
                    "FOREIGN KEY (book_id) REFERENCES books(id)" +
                    ");";
            stmt.execute(transactionsTable);

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
