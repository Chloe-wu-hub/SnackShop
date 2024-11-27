package com.example.shop;

import java.sql.*;
import java.util.ArrayList;

public class Customer {
    private String customerID;
    private String name;
    private String address;
    private String phone;
    private String email;
    private ArrayList<String> orderHistory;
    private ArrayList<String> paymentInfo;

    // Constructor
    public Customer(String customerID, String name, String address, String phone, String email) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.orderHistory = new ArrayList<>();
        this.paymentInfo = new ArrayList<>();
    }

    // 保存客户信息到数据库
    public void saveToDatabase() {
        String query = "INSERT INTO customers (customer_id, name, address, phone, email, order_history, payment_info) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        // 数据验证
        if (this.customerID == null || this.customerID.isEmpty()) {
            System.out.println("Customer ID cannot be null or empty.");
            return;
        }
        if (!this.email.contains("@")) {
            System.out.println("Invalid email format.");
            return;
        }
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Database connection established.");
                conn.setAutoCommit(false);  // 禁用自动提交

                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, this.customerID);
                    stmt.setString(2, this.name);
                    stmt.setString(3, this.address);
                    stmt.setString(4, this.phone);
                    stmt.setString(5, this.email);
                    stmt.setString(6, String.join(", ", this.orderHistory));  // 用逗号分隔
                    stmt.setString(7, String.join(", ", this.paymentInfo));  // 用逗号分隔

                    int rowsAffected = stmt.executeUpdate();
                    conn.commit();

                    if (rowsAffected > 0) {
                        System.out.println("Customer " + this.customerID + " inserted successfully.");
                    } else {
                        System.out.println("Failed to insert customer: " + this.customerID);
                    }
                } catch (SQLException e) {
                    conn.rollback();  // 回滚事务
                    System.out.println("Error inserting customer with ID: " + this.customerID);
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error with database connection.");
            e.printStackTrace();
        }
    }

    // 插入多个客户数据
    public static void insertMultipleCustomers() {
        // 使用您提供的客户数据
        String[][] customerData = {
            {"1234578", "Pierre Dupont", "5 Rue de la Paix, 75002 Paris, France", "+33 1 44 55 66 77", "pierre.dupont@email.com", "1001: Delivered, 1002: In Progress", "150.00 EUR, 230.50 EUR"},
            {"1234579", "Marie Lefevre", "15 Avenue des Champs-Élysées, 75008 Paris, France", "+33 1 45 67 89 01", "marie.lefevre@email.com", "1003: Delivered", "320.00 EUR"},
            {"1234580", "Jean-Marie Durand", "32 Boulevard Montmartre, 75009 Paris, France", "+33 1 47 56 78 99", "jean.durand@email.com", "1004: Delivered", "180.00 EUR"},
            {"1234581", "Sophie Martin", "10 Rue de Rivoli, 75001 Paris, France", "+33 1 48 59 60 10", "sophie.martin@email.com", "1005: Delivered", "99.99 EUR"},
            {"1234582", "Alain Bernard", "20 Rue des Rosiers, 75004 Paris, France", "+33 1 49 51 52 53", "alain.bernard@email.com", "1006: In Progress", "210.00 EUR"},
            {"1234583", "Claire Lemoine", "18 Rue du Faubourg Saint-Antoine, 75011 Paris, France", "+33 1 50 52 53 54", "claire.lemoine@email.com", "1007: Delivered", "220.50 EUR"},
            {"1234584", "Jacques Dubois", "7 Place de la République, 75003 Paris, France", "+33 1 52 53 54 55", "jacques.dubois@email.com", "1008: Delivered", "180.00 EUR"},
            {"1234585", "Emilie Boucher", "4 Rue des Martyrs, 75009 Paris, France", "+33 1 58 60 61 62", "emilie.boucher@email.com", "1009: In Progress", "145.00 EUR"},
            {"1234586", "Nicolas Pires", "22 Rue Saint-Denis, 75001 Paris, France", "+33 1 63 64 65 66", "nicolas.pires@email.com", "1010: Delivered", "250.00 EUR"},
            {"1234587", "Isabelle Lefranc", "13 Rue de la Gare, 75008 Paris, France", "+33 1 72 73 74 75", "isabelle.lefranc@email.com", "1011: Delivered", "175.50 EUR"}
        };

        // 遍历每个客户信息并插入数据库
        for (String[] customerInfo : customerData) {
            String customerID = customerInfo[0];
            String name = customerInfo[1];
            String address = customerInfo[2];
            String phone = customerInfo[3];
            String email = customerInfo[4];
            String orderHistory = customerInfo[5];
            String paymentInfo = customerInfo[6];

            Customer customer = new Customer(customerID, name, address, phone, email);
            customer.orderHistory.add(orderHistory);  // 设置订单历史
            customer.paymentInfo.add(paymentInfo);    // 设置支付信息

            customer.saveToDatabase();  // 保存客户信息到数据库
        }
    }

    // 添加 main 方法来调用插入多个客户数据的功能
    public static void main(String[] args) {
        // 插入具体的客户数据
        insertMultipleCustomers();
    }
 // 更新客户信息到数据库
    public void updateCustomerInformation(String name, String address, String phone, String email, ArrayList<String> orderHistory, ArrayList<String> paymentInfo) {
        String query = "UPDATE customers SET name = ?, address = ?, phone = ?, email = ?, order_history = ?, payment_info = ? WHERE customer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Database connection established.");
                conn.setAutoCommit(false);  // 禁用自动提交

                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, name);
                    stmt.setString(2, address);
                    stmt.setString(3, phone);
                    stmt.setString(4, email);
                    stmt.setString(5, String.join(", ", orderHistory));  // 用逗号分隔订单历史
                    stmt.setString(6, String.join(", ", paymentInfo));  // 用逗号分隔支付信息
                    stmt.setString(7, this.customerID);  // 使用当前对象的 customerID

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        conn.commit();  // 提交事务
                        System.out.println("Customer " + this.customerID + " updated successfully.");
                    } else {
                        conn.rollback();  // 回滚事务
                        System.out.println("No customer found with ID: " + this.customerID);
                    }
                } catch (SQLException e) {
                    conn.rollback();  // 回滚事务
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 // 删除客户信息
    public void deleteFromDatabase() {
        String query = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Database connection established.");
                conn.setAutoCommit(false);  // 禁用自动提交

                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, this.customerID);  // 使用当前对象的 customerID

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        conn.commit();  // 提交事务
                        System.out.println("Customer " + this.customerID + " deleted successfully.");
                    } else {
                        conn.rollback();  // 回滚事务
                        System.out.println("No customer found with ID: " + this.customerID);
                    }
                } catch (SQLException e) {
                    conn.rollback();  // 回滚事务
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
