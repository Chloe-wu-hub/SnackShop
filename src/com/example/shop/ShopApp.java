package com.example.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;

public class ShopApp {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                String query = "INSERT INTO products (name, price, country_of_origin, weight_g, flavor, category) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);

                // Adding data from the provided table
                addProduct(stmt, "Pocky", new BigDecimal("1.50"), "Japan", new BigDecimal("50"), "Chocolate", "Biscuit Sticks");
                addProduct(stmt, "Stroopwafel", new BigDecimal("2.00"), "Netherlands", new BigDecimal("60"), "Caramel", "Waffle Cookies");
                addProduct(stmt, "Kinder Bueno", new BigDecimal("1.80"), "Italy", new BigDecimal("43"), "Hazelnut", "Chocolate Bar");
                addProduct(stmt, "Tim Tam", new BigDecimal("2.20"), "Australia", new BigDecimal("45"), "Chocolate", "Biscuits");
                addProduct(stmt, "Reese's Peanut Butter Cups", new BigDecimal("1.00"), "USA", new BigDecimal("40"), "Peanut Butter", "Chocolate");
                addProduct(stmt, "Choco Pie", new BigDecimal("1.30"), "South Korea", new BigDecimal("35"), "Marshmallow", "Cake");
                addProduct(stmt, "Digestive", new BigDecimal("1.00"), "United Kingdom", new BigDecimal("70"), "Plain", "Biscuits");

                // Execute batch insert
                stmt.executeBatch();
                System.out.println("Products added successfully.");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addProduct(PreparedStatement stmt, String name, BigDecimal price, String countryOfOrigin, BigDecimal weight, String flavor, String category) throws SQLException {
        stmt.setString(1, name);
        stmt.setBigDecimal(2, price);
        stmt.setString(3, countryOfOrigin);
        stmt.setBigDecimal(4, weight);
        stmt.setString(5, flavor);
        stmt.setString(6, category);
        stmt.addBatch();
    }
}
