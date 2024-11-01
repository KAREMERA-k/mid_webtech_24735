package com.auca.library.service;

import com.auca.library.model.Location;
import com.auca.library.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationService {

    public boolean createLocation(Location location) {
        String sql = "INSERT INTO locations (province, district, sector, cell, village) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, location.getProvince());
            stmt.setString(2, location.getDistrict());
            stmt.setString(3, location.getSector());
            stmt.setString(4, location.getCell());
            stmt.setString(5, location.getVillage());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM locations";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Location location = new Location(
                        rs.getInt("id"),
                        rs.getString("province"),
                        rs.getString("district"),
                        rs.getString("sector"),
                        rs.getString("cell"),
                        rs.getString("village")
                );
                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }
}

