package com.example.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.math.RoundingMode;
import java.util.Collections;


public class ShopApp {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                conn.setAutoCommit(true); // 确保自动提交
                String query = "INSERT INTO products (name, price, quantity, country_of_origin, weight_g, flavor, category) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);

                // Adding data from the provided table (20 unique products)
                addProduct(stmt, "Pocky", new BigDecimal("1.50"), 100, "Japan", new BigDecimal("50"), "Chocolate", "Biscuit Sticks");
                addProduct(stmt, "Stroopwafel", new BigDecimal("2.00"), 200, "Netherlands", new BigDecimal("60"), "Caramel", "Waffle Cookies");
                addProduct(stmt, "Kinder Bueno", new BigDecimal("1.80"), 150, "Italy", new BigDecimal("43"), "Hazelnut", "Chocolate Bar");
                addProduct(stmt, "Tim Tam", new BigDecimal("2.20"), 300, "Australia", new BigDecimal("45"), "Chocolate", "Biscuits");
                addProduct(stmt, "Reese's Peanut Butter Cups", new BigDecimal("1.00"), 250, "USA", new BigDecimal("40"), "Peanut Butter", "Chocolate");
                addProduct(stmt, "Choco Pie", new BigDecimal("1.30"), 180, "South Korea", new BigDecimal("35"), "Marshmallow", "Cake");
                addProduct(stmt, "Digestive Biscuits", new BigDecimal("1.00"), 120, "United Kingdom", new BigDecimal("70"), "Whole Wheat", "Biscuits");
                addProduct(stmt, "Tunnock's Caramel Wafer", new BigDecimal("1.20"), 140, "Scotland", new BigDecimal("55"), "Caramel", "Wafers");
                addProduct(stmt, "Macaron", new BigDecimal("3.00"), 80, "France", new BigDecimal("15"), "Various", "Pastry");
                addProduct(stmt, "Haw Flakes", new BigDecimal("0.50"), 90, "China", new BigDecimal("30"), "Sweet & Sour", "Fruit Candy");
                addProduct(stmt, "Baklava", new BigDecimal("2.50"), 70, "Turkey", new BigDecimal("35"), "Honey & Nuts", "Pastry");
                addProduct(stmt, "Bounty Bar", new BigDecimal("1.50"), 110, "UK", new BigDecimal("57"), "Coconut", "Chocolate Bar");
                addProduct(stmt, "Mochi", new BigDecimal("2.00"), 95, "Japan", new BigDecimal("45"), "Red Bean", "Rice Cake");
                addProduct(stmt, "Lotus Biscoff", new BigDecimal("0.70"), 130, "Belgium", new BigDecimal("25"), "Cinnamon", "Biscuits");
                addProduct(stmt, "Ritter Sport", new BigDecimal("2.20"), 160, "Germany", new BigDecimal("100"), "Hazelnut", "Chocolate");
                addProduct(stmt, "Alfajores", new BigDecimal("2.00"), 85, "Argentina", new BigDecimal("60"), "Dulce de Leche", "Cookies");
                addProduct(stmt, "Jelly Babies", new BigDecimal("1.00"), 150, "United Kingdom", new BigDecimal("80"), "Fruit", "Gummies");
                addProduct(stmt, "Salted Egg Yolk Chips", new BigDecimal("3.50"), 50, "Singapore", new BigDecimal("100"), "Salted Egg Yolk", "Chips");
                addProduct(stmt, "Speculoos Cookie Butter", new BigDecimal("3.00"), 75, "Belgium", new BigDecimal("200"), "Spiced", "Spread");
                addProduct(stmt, "Churros", new BigDecimal("1.80"), 60, "Spain", new BigDecimal("50"), "Cinnamon & Sugar", "Pastry");

                // Execute batch insert
                stmt.executeBatch();
                System.out.println("Products added successfully.");
                // Example usage: Update product with ID 1 to have a new quantity of 5
                updateProduct(conn, 1, 5);
                // Example usage: Delete product with ID 2
                deleteProduct(conn, 2);
                // Example usage: Get product by ID
                getProductById(conn, 1);
                
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 // 确保 addProduct 方法独立于 main 方法，并且放在类的同一级别
    private static void addProduct(PreparedStatement stmt, String name, BigDecimal price, int quantity, String countryOfOrigin, BigDecimal weight, String flavor, String category) throws SQLException {
        System.out.println("Attempting to add product: " + name);  // 打印正在添加的商品名称
        stmt.setString(1, name);
        stmt.setBigDecimal(2, price);
        stmt.setInt(3, quantity);
        stmt.setString(4, countryOfOrigin);
        stmt.setBigDecimal(5, weight);
        stmt.setString(6, flavor);
        stmt.setString(7, category);
        stmt.addBatch();
        System.out.println("Product added to batch: " + name);  // 打印已加入批量操作
    }

    private static void updateProduct(Connection conn, int productId, int newQuantity) throws SQLException {
        if (newQuantity < 1 || newQuantity > 10) {
            System.out.println("Quantity must be between 1 and 10.");
            return;
        }

        String selectQuery = "SELECT quantity, price FROM products WHERE id = ?";
        try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
            selectStmt.setInt(1, productId);
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    int currentQuantity = rs.getInt("quantity");
                    BigDecimal currentPrice = rs.getBigDecimal("price");

                    if (currentQuantity == 0) {
                        System.out.println("Current quantity is zero, cannot update price proportionally.");
                        return;
                    }
              
                 // 计算单价
                 BigDecimal unitPrice = currentPrice.divide(new BigDecimal(currentQuantity), 2, RoundingMode.HALF_UP);
                 // 计算新价格
                 BigDecimal newPrice = unitPrice.multiply(new BigDecimal(newQuantity));

                    String updateQuery = "UPDATE products SET quantity = ?, price = ? WHERE id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, newQuantity);
                        updateStmt.setBigDecimal(2, newPrice);
                        updateStmt.setInt(3, productId);
                        updateStmt.executeUpdate();
                        System.out.println("Product ID " + productId + " updated successfully with new quantity: " + newQuantity + " and new price: " + newPrice);
                    }
                } else {
                    System.out.println("Product with ID " + productId + " not found.");
                }
            }
        }
    }
    private static void deleteProduct(Connection conn, int productId) throws SQLException {
        String deleteQuery = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, productId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product ID " + productId + " deleted successfully.");
            } else {
                System.out.println("Product with ID " + productId + " not found.");
            }
        }
    }
    private static void getProductById(Connection conn, int productId) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    BigDecimal price = rs.getBigDecimal("price");
                    int quantity = rs.getInt("quantity");
                    String countryOfOrigin = rs.getString("country_of_origin");
                    BigDecimal weight = rs.getBigDecimal("weight_g");
                    String flavor = rs.getString("flavor");
                    String category = rs.getString("category");

                    System.out.println("Product ID: " + productId);
                    System.out.println("Name: " + name);
                    System.out.println("Price: " + price);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("Country of Origin: " + countryOfOrigin);
                    System.out.println("Weight (g): " + weight);
                    System.out.println("Flavor: " + flavor);
                    System.out.println("Category: " + category);
                } else {
                    System.out.println("Product with ID " + productId + " not found.");
                }
            }
        }
    }
    public static void searchProductsByKeywords(String keywords) {
        // 检查是否有输入关键词
        if (keywords == null || keywords.trim().isEmpty()) {
            System.out.println("Please provide valid keywords.");
            return;
        }

        String[] keywordArray = keywords.trim().split("\\s+"); // 按空格拆分为数组，忽略多余的空格
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM products WHERE ");

        // 动态生成 SQL 查询条件
        String conditions = String.join(" OR ", Collections.nCopies(keywordArray.length, "(name LIKE ? OR description LIKE ?)"));
        queryBuilder.append(conditions);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {

            // 设置每个关键词的参数
            int paramIndex = 1;
            for (String keyword : keywordArray) {
                String wildcardKeyword = "%" + keyword + "%"; // 模糊匹配，添加通配符 %
                stmt.setString(paramIndex++, wildcardKeyword); // 用于 name 字段
                stmt.setString(paramIndex++, wildcardKeyword); // 用于 description 字段
            }

            // 执行查询
            try (ResultSet rs = stmt.executeQuery()) {
                // 输出结果
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Price: $" + rs.getBigDecimal("price"));
                    System.out.println("Quantity: " + rs.getInt("quantity"));
                    System.out.println("Country of Origin: " + rs.getString("country_of_origin"));
                    System.out.println("Weight (g): " + rs.getBigDecimal("weight_g"));
                    System.out.println("Flavor: " + rs.getString("flavor"));
                    System.out.println("Category: " + rs.getString("category"));
                    System.out.println("------------------------------");
                }
                if (!found) {
                    System.out.println("No products found matching the keywords: " + keywords);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error during batch insert.");
        }
    }
}

