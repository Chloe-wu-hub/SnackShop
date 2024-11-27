package com.example.shop;

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

    // Getters and setters
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getOrderHistory() {
        return orderHistory;
    }

    public void addOrder(String order) {
        this.orderHistory.add(order);
    }

    public ArrayList<String> getPaymentInfo() {
        return paymentInfo;
    }

    public void addPaymentInfo(String payment) {
        this.paymentInfo.add(payment);
    }

    // Add, modify, and delete customer information
    public void updateCustomerInfo(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public void deleteCustomerInfo() {
        // Clear the customer data
        this.customerID = null;
        this.name = null;
        this.address = null;
        this.phone = null;
        this.email = null;
        this.orderHistory.clear();
        this.paymentInfo.clear();
    }

    // Print customer details
    public void printCustomerInfo() {
        System.out.println("Customer ID: " + customerID);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Order History: " + orderHistory);
        System.out.println("Payment Information: " + paymentInfo);
    }
}