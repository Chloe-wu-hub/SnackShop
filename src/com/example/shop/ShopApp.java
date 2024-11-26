package com.example.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;

public class ShopApp {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                String query = "INSERT INTO products (name, price, quantity, country_of_origin, weight_g, flavor, category) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);

                // Adding data from the provided table
                addProduct(stmt, "Pocky", new BigDecimal("1.50"), 100, "Japan", new BigDecimal("50"), "Chocolate", "Biscuit Sticks");
                addProduct(stmt, "Stroopwafel", new BigDecimal("2.00"), 200, "Netherlands", new BigDecimal("60"), "Caramel", "Waffle Cookies");
                addProduct(stmt, "Kinder Bueno", new BigDecimal("1.80"), 150, "Italy", new BigDecimal("43"), "Hazelnut", "Chocolate Bar");
                addProduct(stmt, "Tim Tam", new BigDecimal("2.20"), 300, "Australia", new BigDecimal("45"), "Chocolate", "Biscuits");
                addProduct(stmt, "Reese's Peanut Butter Cups", new BigDecimal("1.00"), 250, "USA", new BigDecimal("40"), "Peanut Butter", "Chocolate");
                addProduct(stmt, "Choco Pie", new BigDecimal("1.30"), 180, "South Korea", new BigDecimal("35"), "Marshmallow", "Cake");
                addProduct(stmt, "Digestive", new BigDecimal("1.00"), 120, "United Kingdom", new BigDecimal("70"), "Plain", "Biscuits");
                addProduct(stmt, "Oreos", new BigDecimal("1.50"), 500, "USA", new BigDecimal("154"), "Vanilla", "Cookies");
                addProduct(stmt, "Ferrero Rocher", new BigDecimal("3.00"), 80, "Italy", new BigDecimal("38"), "Hazelnut", "Chocolate");
                addProduct(stmt, "Haribo Gummy Bears", new BigDecimal("0.99"), 600, "Germany", new BigDecimal("100"), "Fruit", "Gummy Candy");
                addProduct(stmt, "Toblerone", new BigDecimal("2.50"), 120, "Switzerland", new BigDecimal("100"), "Honey and Almond Nougat", "Chocolate");
                addProduct(stmt, "Lindt Excellence", new BigDecimal("3.50"), 90, "Switzerland", new BigDecimal("100"), "Dark Chocolate", "Chocolate Bar");
                addProduct(stmt, "Milka", new BigDecimal("1.70"), 200, "Germany", new BigDecimal("100"), "Milk Chocolate", "Chocolate Bar");
                addProduct(stmt, "Skittles", new BigDecimal("1.20"), 400, "USA", new BigDecimal("50"), "Fruit", "Candy");
                addProduct(stmt, "Mars Bar", new BigDecimal("1.50"), 350, "United Kingdom", new BigDecimal("51"), "Caramel", "Chocolate Bar");
                addProduct(stmt, "Twix", new BigDecimal("1.80"), 220, "USA", new BigDecimal("50"), "Caramel", "Chocolate Bar");
                addProduct(stmt, "Cadbury Dairy Milk", new BigDecimal("2.00"), 300, "United Kingdom", new BigDecimal("95"), "Milk Chocolate", "Chocolate Bar");
                addProduct(stmt, "Pepero", new BigDecimal("1.40"), 250, "South Korea", new BigDecimal("47"), "Chocolate", "Biscuit Sticks");
                addProduct(stmt, "Matcha KitKat", new BigDecimal("2.50"), 100, "Japan", new BigDecimal("45"), "Matcha", "Chocolate Bar");

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

    private static void addProduct(PreparedStatement stmt, String name, BigDecimal price, int quantity, String countryOfOrigin, BigDecimal weight, String flavor, String category) throws SQLException {
        stmt.setString(1, name);
        stmt.setBigDecimal(2, price);
        stmt.setInt(3, quantity);
        stmt.setString(4, countryOfOrigin);
        stmt.setBigDecimal(5, weight);
        stmt.setString(6, flavor);
        stmt.setString(7, category);
        stmt.addBatch();
    }
}
