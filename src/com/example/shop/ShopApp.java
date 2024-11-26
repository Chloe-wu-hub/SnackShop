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
                addProduct(stmt, "Digestive Biscuits", new BigDecimal("1.00"), 120, "United Kingdom", new BigDecimal("70"), "Whole Wheat", "Biscuits");
                addProduct(stmt, "Tunnock's Caramel Wafer", new BigDecimal("1.20"), 150, "Scotland", new BigDecimal("55"), "Caramel", "Wafers");
                addProduct(stmt, "Macaron", new BigDecimal("3.00"), 80, "France", new BigDecimal("15"), "Various", "Pastry");
                addProduct(stmt, "Haw Flakes", new BigDecimal("0.50"), 300, "China", new BigDecimal("30"), "Sweet & Sour", "Fruit Candy");
                addProduct(stmt, "Baklava", new BigDecimal("2.50"), 100, "Turkey", new BigDecimal("35"), "Honey & Nuts", "Pastry");
                addProduct(stmt, "Bounty Bar", new BigDecimal("1.50"), 200, "UK", new BigDecimal("57"), "Coconut", "Chocolate Bar");
                addProduct(stmt, "Mochi", new BigDecimal("2.00"), 90, "Japan", new BigDecimal("45"), "Red Bean", "Rice Cake");
                addProduct(stmt, "Lotus Biscoff", new BigDecimal("0.70"), 150, "Belgium", new BigDecimal("25"), "Cinnamon", "Biscuits");
                addProduct(stmt, "Ritter Sport", new BigDecimal("2.20"), 110, "Germany", new BigDecimal("100"), "Hazelnut", "Chocolate");
                addProduct(stmt, "Alfajores", new BigDecimal("2.00"), 130, "Argentina", new BigDecimal("60"), "Dulce de Leche", "Cookies");
                addProduct(stmt, "Jelly Babies", new BigDecimal("1.00"), 170, "United Kingdom", new BigDecimal("80"), "Fruit", "Gummies");
                addProduct(stmt, "Salted Egg Yolk Chips", new BigDecimal("3.50"), 90, "Singapore", new BigDecimal("100"), "Salted Egg Yolk", "Chips");
                addProduct(stmt, "Speculoos Cookie Butter", new BigDecimal("3.00"), 140, "Belgium", new BigDecimal("200"), "Spiced", "Spread");
                addProduct(stmt, "Churros", new BigDecimal("1.80"), 160, "Spain", new BigDecimal("50"), "Cinnamon & Sugar", "Pastry");

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
