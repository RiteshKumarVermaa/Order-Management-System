
package com.order;

import java.sql.*;

public class OrderDAO {

    // Display Order Details
    private void displayOrder(ResultSet rs) throws SQLException {
        System.out.println(
            "ID: " + rs.getInt("order_id") +
            " | Customer: " + rs.getString("customer_name") +
            " | Product: " + rs.getString("product_name") +
            " | Quantity: " + rs.getInt("quantity") +
            " | Price: " + rs.getDouble("unit_price") +
            " | Status: " + rs.getString("status")
        );
    }

    // (a) Insert Order
    public void insertOrder(Order order) throws SQLException {

        String sql = "INSERT INTO Orders " +
                "(order_id, customer_name, product_name, quantity, unit_price, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, order.getOrderId());
            ps.setString(2, order.getCustomerName());
            ps.setString(3, order.getProductName());
            ps.setInt(4, order.getQuantity());
            ps.setDouble(5, order.getUnitPrice());
            ps.setString(6, order.getStatus());

            ps.executeUpdate();
            System.out.println("Order Inserted Successfully!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Duplicate Order ID! Order already exists.");
        }
    }

    // (b) Search by Status, Quantity > 2, Price DESC
    public void searchOrders(String status) throws SQLException {

        String sql = "SELECT * FROM Orders " +
                "WHERE status = ? AND quantity > 2 " +
                "ORDER BY unit_price DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    displayOrder(rs);
                    found = true;
                }

                if (!found) {
                    System.out.println("No Matching Orders Found!");
                }
            }
        }
    }

    // (c) Increase Order Quantity
    public void updateQuantity(int orderId, int additionalQuantity)
            throws SQLException {

        if (additionalQuantity <= 0) {
            System.out.println("Additional quantity must be positive!");
            return;
        }

        String sql = "UPDATE Orders SET quantity = quantity + ? " +
                "WHERE order_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, additionalQuantity);
            ps.setInt(2, orderId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Order Quantity Updated Successfully!");
            } else {
                System.out.println("Order Not Found!");
            }
        }
    }

    // (d) Update Status Only If Valid
    public void updateStatus(int orderId, String newStatus)
            throws SQLException {

        if (!newStatus.equalsIgnoreCase("Pending") &&
            !newStatus.equalsIgnoreCase("Shipped") &&
            !newStatus.equalsIgnoreCase("Delivered") &&
            !newStatus.equalsIgnoreCase("Cancelled")) {

            System.out.println("Invalid Status!");
            return;
        }

        String sql = "UPDATE Orders SET status = ? WHERE order_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, orderId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Order Status Updated Successfully!");
            } else {
                System.out.println("Order Not Found!");
            }
        }
    }

    // (e) Calculate Total Order Value
    public void calculateTotalValue() throws SQLException {

        String sql = "SELECT SUM(quantity * unit_price) AS total FROM Orders";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                System.out.println(
                    "Total Order Value: " + rs.getDouble("total")
                );
            }
        }
    }

    // (e) Delete Only Cancelled Orders
    public void deleteCancelledOrder(int orderId) throws SQLException {

        String sql = "DELETE FROM Orders " +
                "WHERE order_id = ? AND status = 'Cancelled'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Cancelled Order Deleted Successfully!");
            } else {
                System.out.println(
                    "Order Not Found or Status is Not Cancelled!"
                );
            }
        }
    }
}
