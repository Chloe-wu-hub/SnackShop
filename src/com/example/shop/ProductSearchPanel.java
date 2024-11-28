package com.example.shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ProductSearchPanel extends JPanel {
    private JTextField productIdField;
    private JButton searchButton;
    private JLabel resultLabel;

    public ProductSearchPanel() {
        setLayout(new FlowLayout());

        // 创建UI组件
        JLabel productIdLabel = new JLabel("Enter Product ID: ");
        productIdField = new JTextField(10);
        searchButton = new JButton("Search");
        resultLabel = new JLabel("Product Information will appear here.");

        // 添加组件到面板
        add(productIdLabel);
        add(productIdField);
        add(searchButton);
        add(resultLabel);

        // 添加搜索按钮的事件处理
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Id = productIdField.getText().trim();  // 去掉前后空格
                if (!Id.isEmpty() && Id.matches("\\d+")) {  // 判断是否为有效的数字ID
                    searchProductById(Id);
                } else {
                    resultLabel.setText("Please enter a valid numeric Product ID.");
                }
            }
        });
    }

 // 查询商品信息的方法
    private void searchProductById(String productId) {
        String query = "SELECT * FROM products WHERE id = ?";  // 修改字段名为 id

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, productId);  // 设置查询条件
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // 从结果集获取商品信息
                String id = rs.getString("id");  // 获取商品ID
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                
                // 这里是你想要显示的字段
                StringBuilder resultText = new StringBuilder("<html>");
                resultText.append("<b>ID:</b> ").append(id).append("<br>");
                resultText.append("<b>Name:</b> ").append(name).append("<br>");
                resultText.append("<b>Price:</b> ").append(price).append(" EUR<br>");
                resultText.append("<b>Quantity:</b> ").append(quantity).append("<br>");
                resultText.append("</html>");
                
                // 显示商品信息
                resultLabel.setText(resultText.toString());
            } else {
                resultLabel.setText("No product found with ID: " + productId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("Error while searching for the product.");
        }
    }

    // 主函数，创建界面并显示
    public static void main(String[] args) {
        JFrame frame = new JFrame("Product Search");
        ProductSearchPanel panel = new ProductSearchPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.add(panel);
        frame.setVisible(true);
    }
}
