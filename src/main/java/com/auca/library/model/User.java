package com.auca.library.model;

public class User {
    private int id;
    private String username;
    private String password; // Store hashed password here
    private String role; // Librarian, Student, Teacher, etc.
    private String membershipType; // Gold, Silver, Striver
    private String phoneNumber;
    private String province;
    private String district;
    private String sector;
    private String cell;
    private String village;

    // Constructors
    public User() {}

    public User(int id, String username, String password, String role, String membershipType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.membershipType = membershipType;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }

    public String getCell() { return cell; }
    public void setCell(String cell) { this.cell = cell; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }
}

