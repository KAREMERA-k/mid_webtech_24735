package com.auca.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.io.File;

public class DatabaseUtil {
    // Set the absolute path to the SQLite database file

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load SQLite JDBC Driver: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static final String DB_PATH = "C:/Users/Admin/auca_library.db"; // Update this with your full path
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    public static Connection getConnection() throws SQLException {
        // Debugging: Print database URL
        System.out.println("Attempting connection to database at: " + DB_PATH);
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            // Print confirmation of connection
            System.out.println("Connected to SQLite database at: " + DB_PATH);

            // Create tables with individual error handling and debugging output
            try {
                stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
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
                        ");");
                System.out.println("Users table created.");
            } catch (SQLException e) {
                System.out.println("Error creating users table: " + e.getMessage());
            }

            try {
                stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                        "id INTEGER PRIMARY KEY," +
                        "title TEXT NOT NULL," +
                        "author TEXT," +
                        "isbn TEXT UNIQUE," +
                        "shelf_id INTEGER," +
                        "availability INTEGER DEFAULT 1" +
                        ");");
                System.out.println("Books table created.");
            } catch (SQLException e) {
                System.out.println("Error creating books table: " + e.getMessage());
            }

            // Repeat for other tables with debugging output for each
            try {
                stmt.execute("CREATE TABLE IF NOT EXISTS shelves (" +
                        "id INTEGER PRIMARY KEY," +
                        "room_id INTEGER," +
                        "description TEXT" +
                        ");");
                System.out.println("Shelves table created.");
            } catch (SQLException e) {
                System.out.println("Error creating shelves table: " + e.getMessage());
            }

            try {
                stmt.execute("CREATE TABLE IF NOT EXISTS rooms (" +
                        "id INTEGER PRIMARY KEY," +
                        "location TEXT," +
                        "capacity INTEGER" +
                        ");");
                System.out.println("Rooms table created.");
            } catch (SQLException e) {
                System.out.println("Error creating rooms table: " + e.getMessage());
            }

            try {
                stmt.execute("CREATE TABLE IF NOT EXISTS locations (" +
                        "id INTEGER PRIMARY KEY," +
                        "province TEXT," +
                        "district TEXT," +
                        "sector TEXT," +
                        "cell TEXT," +
                        "village TEXT" +
                        ");");
                System.out.println("Locations table created.");
            } catch (SQLException e) {
                System.out.println("Error creating locations table: " + e.getMessage());
            }

            try {
                stmt.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                        "id INTEGER PRIMARY KEY," +
                        "user_id INTEGER NOT NULL," +
                        "book_id INTEGER NOT NULL," +
                        "borrow_date TEXT DEFAULT CURRENT_TIMESTAMP," +
                        "return_date TEXT," +
                        "fine REAL DEFAULT 0.00," +
                        "FOREIGN KEY (user_id) REFERENCES users(id)," +
                        "FOREIGN KEY (book_id) REFERENCES books(id)" +
                        ");");
                System.out.println("Transactions table created.");
            } catch (SQLException e) {
                System.out.println("Error creating transactions table: " + e.getMessage());
            }

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}
