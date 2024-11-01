package com.auca.library.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private int shelfId;
    private boolean availability;

    // Constructors
    public Book() {}

    public Book(int id, String title, String author, String isbn, int shelfId, boolean availability) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.shelfId = shelfId;
        this.availability = availability;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getShelfId() { return shelfId; }
    public void setShelfId(int shelfId) { this.shelfId = shelfId; }

    public boolean isAvailability() { return availability; }
    public void setAvailability(boolean availability) { this.availability = availability; }
}
