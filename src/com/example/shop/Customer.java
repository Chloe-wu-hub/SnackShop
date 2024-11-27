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
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Debugging: Print out the values being inserted
            System.out.println("Inserting customer: " + this.customerID);

            stmt.setString(1, this.customerID);
            stmt.setString(2, this.name);
            stmt.setString(3, this.address);
            stmt.setString(4, this.phone);
            stmt.setString(5, this.email);
            stmt.setString(6, String.join("<br>", this.orderHistory));  // 将orderHistory转换为String
            stmt.setString(7, String.join("<br>", this.paymentInfo));  // 将paymentInfo转换为String

            int rowsAffected = stmt.executeUpdate();

            // Debugging: Confirm the number of rows affected
            if (rowsAffected > 0) {
                System.out.println("Customer " + this.customerID + " inserted successfully.");
            } else {
                System.out.println("Failed to insert customer: " + this.customerID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 插入多个客户数据
    public static void insertMultipleCustomers() {
        Customer customer1 = new Customer("1234578", "Pierre Dupont", "5 Rue de la Paix, 75002 Paris, France", "+33 1 44 55 66 77", "pierre.dupont@email.com");
        customer1.orderHistory.add("订单ID: 1001 (Delivered, 150.00 EUR, 2024-12-01)");
        customer1.orderHistory.add("订单ID: 1002 (In Progress, 230.50 EUR, 2024-12-05)");
        customer1.paymentInfo.add("支付ID: 2001 (Credit Card, Completed, 150.00 EUR)");
        customer1.paymentInfo.add("支付ID: 2002 (PayPal, Pending, 230.50 EUR)");
        customer1.saveToDatabase();

        Customer customer2 = new Customer("1234579", "Marie Lefevre", "15 Avenue des Champs-Élysées, 75008 Paris, France", "+33 1 45 67 89 01", "marie.lefevre@email.com");
        customer2.orderHistory.add("订单ID: 1003 (Delivered, 320.00 EUR, 2024-12-03)");
        customer2.paymentInfo.add("支付ID: 2003 (Bank Transfer, Completed, 320.00 EUR)");
        customer2.saveToDatabase();

        Customer customer3 = new Customer("1234580", "Jean-Marie Durand", "32 Boulevard Montmartre, 75009 Paris, France", "+33 1 47 56 78 99", "jean.durand@email.com");
        customer3.orderHistory.add("订单ID: 1004 (Delivered, 180.00 EUR, 2024-12-02)");
        customer3.paymentInfo.add("支付ID: 2024 (Credit Card, Completed, 180.00 EUR)");
        customer3.saveToDatabase();

        Customer customer4 = new Customer("1234581", "Sophie Martin", "10 Rue de Rivoli, 75001 Paris, France", "+33 1 48 59 60 10", "sophie.martin@email.com");
        customer4.orderHistory.add("订单ID: 1005 (Delivered, 99.99 EUR, 2024-12-04)");
        customer4.paymentInfo.add("支付ID: 2024 (Credit Card, Completed, 99.99 EUR)");
        customer4.saveToDatabase();

        Customer customer5 = new Customer("1234582", "Alain Bernard", "20 Rue des Rosiers, 75004 Paris, France", "+33 1 49 51 52 53", "alain.bernard@email.com");
        customer5.orderHistory.add("订单ID: 1006 (In Progress, 210.00 EUR, 2024-12-06)");
        customer5.paymentInfo.add("支付ID: 2024 (PayPal, Pending, 210.00 EUR)");
        customer5.saveToDatabase();

        Customer customer6 = new Customer("1234583", "Claire Lemoine", "18 Rue du Faubourg Saint-Antoine, 75011 Paris, France", "+33 1 50 52 53 54", "claire.lemoine@email.com");
        customer6.orderHistory.add("订单ID: 1007 (Delivered, 220.50 EUR, 2024-12-07)");
        customer6.paymentInfo.add("支付ID: 2024 (PayPal, Completed, 220.50 EUR)");
        customer6.saveToDatabase();

        Customer customer7 = new Customer("1234584", "Jacques Dubois", "7 Place de la République, 75003 Paris, France", "+33 1 52 53 54 55", "jacques.dubois@email.com");
        customer7.orderHistory.add("订单ID: 1008 (Delivered, 180.00 EUR, 2024-12-08)");
        customer7.paymentInfo.add("支付ID: 2024 (Bank Transfer, Completed, 180.00 EUR)");
        customer7.saveToDatabase();

        Customer customer8 = new Customer("1234585", "Emilie Boucher", "4 Rue des Martyrs, 75009 Paris, France", "+33 1 58 60 61 62", "emilie.boucher@email.com");
        customer8.orderHistory.add("订单ID: 1009 (In Progress, 145.00 EUR, 2024-12-09)");
        customer8.paymentInfo.add("支付ID: 2024 (PayPal, Pending, 145.00 EUR)");
        customer8.saveToDatabase();

        Customer customer9 = new Customer("1234586", "Nicolas Pires", "22 Rue Saint-Denis, 75001 Paris, France", "+33 1 63 64 65 66", "nicolas.pires@email.com");
        customer9.orderHistory.add("订单ID: 1010 (Delivered, 250.00 EUR, 2024-12-10)");
        customer9.paymentInfo.add("支付ID: 2024 (Credit Card, Completed, 250.00 EUR)");
        customer9.saveToDatabase();

        Customer customer10 = new Customer("1234587", "Isabelle Lefranc", "13 Rue de la Gare, 75008 Paris, France", "+33 1 72 73 74 75", "isabelle.lefranc@email.com");
        customer10.orderHistory.add("订单ID: 1011 (Delivered, 175.50 EUR, 2024-12-11)");
        customer10.paymentInfo.add("支付ID: 2024 (PayPal, Completed, 175.50 EUR)");
        customer10.saveToDatabase();
    }
}
