package com.example.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;


public class ShopApp {
    public static void main(String[] args) {
    	try (Connection conn = DatabaseConnection.getConnection()) {
    	    if (conn != null) {
    	        conn.setAutoCommit(false);  // 禁用自动提交
    	        String query = "INSERT INTO products (name, price, quantity, country_of_origin, weight_g, flavor, category, imagepath) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    	        PreparedStatement stmt = conn.prepareStatement(query);

    	        // Adding data from the provided table
                // Adding data from the provided table (20 unique products)
                addProduct(stmt, "Pocky", new BigDecimal("1.50"), 100, "Japan", new BigDecimal("50"), "Chocolate", "Biscuit","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Stroopwafel", new BigDecimal("2.00"), 200, "Netherlands", new BigDecimal("60"), "Caramel", "Cookies","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Kinder Bueno", new BigDecimal("1.80"), 150, "Italy", new BigDecimal("43"), "Hazelnut", "Chocolate","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Tim Tam", new BigDecimal("2.20"), 300, "Australia", new BigDecimal("45"), "Chocolate", "Biscuits","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Reese's Peanut Butter Cups", new BigDecimal("1.00"), 250, "USA", new BigDecimal("40"), "Peanut Butter", "Chocolate","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Choco Pie", new BigDecimal("1.30"), 180, "South Korea", new BigDecimal("35"), "Marshmallow", "Cake","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Digestive Biscuits", new BigDecimal("1.00"), 120, "United Kingdom", new BigDecimal("70"), "Whole Wheat", "Biscuits","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Tunnock's Caramel Wafer", new BigDecimal("1.20"), 140, "Scotland", new BigDecimal("55"), "Caramel", "Wafers","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Macaron", new BigDecimal("3.00"), 80, "France", new BigDecimal("15"), "Various", "Pastry","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Haw Flakes", new BigDecimal("0.50"), 90, "China", new BigDecimal("30"), "Sweet & Sour", "Fruit Candy","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Baklava", new BigDecimal("2.50"), 70, "Turkey", new BigDecimal("35"), "Honey & Nuts", "Pastry","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Bounty Bar", new BigDecimal("1.50"), 110, "UK", new BigDecimal("57"), "Coconut", "Chocolate","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Mochi", new BigDecimal("2.00"), 95, "Japan", new BigDecimal("45"), "Red Bean", "Cake","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Lotus Biscoff", new BigDecimal("0.70"), 130, "Belgium", new BigDecimal("25"), "Cinnamon", "Biscuits","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Ritter Sport", new BigDecimal("2.20"), 160, "Germany", new BigDecimal("100"), "Hazelnut", "Chocolate","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Alfajores", new BigDecimal("2.00"), 85, "Argentina", new BigDecimal("60"), "Dulce de Leche", "Cookies","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Jelly Babies", new BigDecimal("1.00"), 150, "United Kingdom", new BigDecimal("80"), "Fruit", "Gummies","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Salted Egg Yolk Chips", new BigDecimal("3.50"), 50, "Singapore", new BigDecimal("100"), "Salted Egg Yolk", "Chips","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Speculoos Cookie Butter", new BigDecimal("3.00"), 75, "Belgium", new BigDecimal("200"), "Spiced", "Spread","/Users/wumingge/Desktop/Product images");
                addProduct(stmt, "Churros", new BigDecimal("1.80"), 60, "Spain", new BigDecimal("50"), "Cinnamon & Sugar", "Pastry","/Users/wumingge/Desktop/Product images");

                // Execute batch insert
                stmt.executeBatch();
                conn.commit();  // 显式提交事务
                System.out.println("Products added successfully.");
                // Example usage: Update product with ID 1 to have a new quantity of 5
                updateProduct(conn, 1, 5);
             // 删除删除操作的调用，或者替换为其他产品ID
             // deleteProduct(conn, 2);  // 不需要删除ID为2的产品
                // 删除后刷新并显示所有产品
                showProducts(conn);  // 显示所有产品，验证 ID 为 2 的产品是否被删除
                // Example usage: Get product by ID
                getProductById(conn, 1);
                
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
        	System.out.println("Error during delete operation: " + e.getMessage());
            System.out.println("Transaction rolled back.");
        }
    }
 // 确保 addProduct 方法独立于 main 方法，并且放在类的同一级别
    private static void addProduct(PreparedStatement stmt, String name, BigDecimal price, int quantity, String countryOfOrigin, BigDecimal weight, String flavor, String category, String imagepath) throws SQLException {
        System.out.println("Attempting to add product: " + name);  // 打印正在添加的商品名称
        stmt.setString(1, name);
        stmt.setBigDecimal(2, price);
        stmt.setInt(3, quantity);
        stmt.setString(4, countryOfOrigin);
        stmt.setBigDecimal(5, weight);
        stmt.setString(6, flavor);
        stmt.setString(7, category);
		stmt.setString(8, imagepath);
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
 // 显示所有产品的方法
    private static void showProducts(Connection conn) throws SQLException {
    	// 查询产品，限制 id 在 1 到 20 之间
        String query = "SELECT * FROM products WHERE id BETWEEN 1 AND 20";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            boolean hasProducts = false;  // 增加检查是否有产品
            while (rs.next()) {
                hasProducts = true;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                System.out.println("Product ID: " + id + ", Name: " + name + ", Price: " + price);
            }
            if (!hasProducts) {
                System.out.println("No products found.");
            }
        }
    }

    private static void deleteProduct(Connection conn, int productId) throws SQLException {
        if (conn == null || conn.isClosed()) {
            System.out.println("Connection is invalid or closed.");
            return;
        }

     // 确认产品是否存在
        String checkExistQuery = "SELECT id FROM products WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkExistQuery)) {
            checkStmt.setInt(1, productId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Product with ID " + productId + " not found.");
                    return;  // 如果没有找到产品，则退出
                }
            }
        }

        // 如果产品存在，执行删除操作
        String deleteQuery = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, productId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product ID " + productId + " deleted successfully.");
                conn.commit();  // 删除后显式提交事务
                System.out.println("Transaction committed.");
            } else {
                System.out.println("No rows were deleted. Product with ID " + productId + " might not exist.");
            }
        } catch (SQLException e) {
            System.out.println("Error during delete operation: " + e.getMessage());
            conn.rollback();  // 如果出现异常，回滚事务
            System.out.println("Transaction rolled back.");
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
    public static void searchProductsByKeywords(String keywords, String operator) {
        // 检查是否有输入关键词
        if (keywords == null || keywords.trim().isEmpty()) {
            System.out.println("Please provide valid keywords.");
            return;
        }

        String[] keywordArray = keywords.trim().split("\\s+"); // 按空格拆分为数组，忽略多余的空格
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM products WHERE ");

        // 默认逻辑运算符是OR，如果传入的是AND，则使用AND
        if (operator == null || operator.equalsIgnoreCase("OR")) {
            // 使用OR运算符
            String conditions = String.join(" OR ", Collections.nCopies(keywordArray.length, "(name LIKE ? OR description LIKE ?)"));
            queryBuilder.append(conditions);
        } else if (operator.equalsIgnoreCase("AND")) {
            // 使用AND运算符
            String conditions = String.join(" AND ", Collections.nCopies(keywordArray.length, "(name LIKE ? AND description LIKE ?)"));
            queryBuilder.append(conditions);
        } else {
            System.out.println("Unsupported operator. Please use 'AND' or 'OR'.");
            return;
        }

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
    public static void createOrder(Connection conn, int customerId, Map<Integer, Integer> productQuantities) {
        try {
            // 计算订单的总价格
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                // 获取产品的单价
                String productQuery = "SELECT price FROM products WHERE id = ?";
                try (PreparedStatement productStmt = conn.prepareStatement(productQuery)) {
                    productStmt.setInt(1, productId);
                    try (ResultSet rs = productStmt.executeQuery()) {
                        if (rs.next()) {
                            BigDecimal price = rs.getBigDecimal("price");
                            totalPrice = totalPrice.add(price.multiply(new BigDecimal(quantity)));
                        }
                    }
                }
            }

            // 插入订单
            String insertOrderQuery = "INSERT INTO orders (customer_id, total_price, status) VALUES (?, ?, 'PENDING')";
            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, customerId);
                orderStmt.setBigDecimal(2, totalPrice);
                orderStmt.executeUpdate();

                // 获取生成的订单 ID
                ResultSet generatedKeys = orderStmt.getGeneratedKeys();
                int orderId = -1;
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                }

                // 插入订单项
                for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
                    String insertOrderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
                    try (PreparedStatement itemStmt = conn.prepareStatement(insertOrderItemQuery)) {
                        itemStmt.setInt(1, orderId);
                        itemStmt.setInt(2, entry.getKey());
                        itemStmt.setInt(3, entry.getValue());
                        itemStmt.executeUpdate();
                    }
                }
                // 创建发票
                generateInvoice(conn, orderId);

                // 提交事务
                conn.commit();
                System.out.println("Order created successfully with Order ID: " + orderId);
                System.out.println("Invoice generated successfully for Order ID: " + orderId);
                System.out.println("Order created successfully with Order ID: " + orderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
                System.out.println("Transaction rolled back due to error.");
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
             }
        }
    }
    public static void updateOrderStatus(Connection conn, int orderId, String newStatus) {
        // 确认订单状态是有效的
        if (newStatus == null || newStatus.trim().isEmpty()) {
            System.out.println("Invalid status provided.");
            return;
        }

        // 定义支持的状态
        String[] validStatuses = {"PENDING", "CONFIRMED", "SHIPPED", "DELIVERED"};
        boolean isValidStatus = false;
        for (String status : validStatuses) {
            if (status.equalsIgnoreCase(newStatus)) {
                isValidStatus = true;
                break;
            }
        }

        if (!isValidStatus) {
            System.out.println("Invalid order status: " + newStatus);
            return;
        }

        // 更新订单状态
        String updateQuery = "UPDATE orders SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Order " + orderId + " status updated to " + newStatus);
            } else {
                System.out.println("Order with ID " + orderId + " not found or status is already " + newStatus);
            }
        } catch (SQLException e) {
            System.out.println("Error updating order status: " + e.getMessage());
        }
    }
    public static void generateInvoice(Connection conn, int orderId) {
        try {
            // 获取订单总金额
            String orderQuery = "SELECT total_price FROM orders WHERE id = ?";
            BigDecimal totalAmount = BigDecimal.ZERO;

            try (PreparedStatement orderStmt = conn.prepareStatement(orderQuery)) {
                orderStmt.setInt(1, orderId);
                try (ResultSet rs = orderStmt.executeQuery()) {
                    if (rs.next()) {
                        totalAmount = rs.getBigDecimal("total_price");
                    } else {
                        System.out.println("Order ID " + orderId + " not found.");
                        return;  // 如果找不到订单，结束方法
                    }
                }
            }

            // 生成发票号 (可以使用UUID或自定义规则)
            String invoiceNumber = "INV-" + System.currentTimeMillis(); // 使用时间戳生成简单发票号

            // 将发票信息插入到 invoices 表中
            String insertInvoiceQuery = "INSERT INTO invoices (order_id, invoice_number, total_amount) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertInvoiceQuery)) {
                stmt.setInt(1, orderId);
                stmt.setString(2, invoiceNumber);
                stmt.setBigDecimal(3, totalAmount);
                stmt.executeUpdate();
                System.out.println("Invoice generated successfully for Order ID: " + orderId);
            }
        } catch (SQLException e) {
            System.out.println("Error generating invoice: " + e.getMessage());
        }
    }
    public static void addInvoice(Connection conn, int orderId, BigDecimal totalAmount, String issueDate) throws SQLException {
        String query = "INSERT INTO invoices (order_id, total_amount, issue_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.setBigDecimal(2, totalAmount);
            stmt.setString(3, issueDate);
            stmt.executeUpdate();
            System.out.println("Invoice added successfully for Order ID: " + orderId);
        }
    }
    public static void updateInvoice(Connection conn, int invoiceId, BigDecimal newTotalAmount, String newIssueDate) throws SQLException {
        String query = "UPDATE invoices SET total_amount = ?, issue_date = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBigDecimal(1, newTotalAmount);
            stmt.setString(2, newIssueDate);
            stmt.setInt(3, invoiceId);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Invoice with ID " + invoiceId + " updated successfully.");
            } else {
                System.out.println("No invoice found with ID " + invoiceId);
            }
        }
    }
    public static void deleteInvoice(Connection conn, int invoiceId) throws SQLException {
        String query = "DELETE FROM invoices WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, invoiceId);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Invoice with ID " + invoiceId + " deleted successfully.");
            } else {
                System.out.println("No invoice found with ID " + invoiceId);
            }
        }
    }
    public static void getInvoiceById(Connection conn, int invoiceId) throws SQLException {
        String query = "SELECT * FROM invoices WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, invoiceId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Invoice ID: " + rs.getInt("id"));
                    System.out.println("Order ID: " + rs.getInt("order_id"));
                    System.out.println("Total Amount: " + rs.getBigDecimal("total_amount"));
                    System.out.println("Issue Date: " + rs.getString("issue_date"));
                } else {
                    System.out.println("Invoice with ID " + invoiceId + " not found.");
                }
            }
        }
    }
    
}










