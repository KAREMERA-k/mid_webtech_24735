package com.auca.library.model;

public class Location {
    private int id;
    private String province;
    private String district;
    private String sector;
    private String cell;
    private String village;

    // Constructors
    public Location() {}

    public Location(int id, String province, String district, String sector, String cell, String village) {
        this.id = id;
        this.province = province;
        this.district = district;
        this.sector = sector;
        this.cell = cell;
        this.village = village;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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
