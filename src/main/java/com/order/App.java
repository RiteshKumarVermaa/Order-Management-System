
package com.order;

import java.sql.SQLException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        OrderDAO dao = new OrderDAO();

        try {
            // Test MySQL connection
            try (var con = DBConnection.getConnection()) {
                System.out.println("MySQL Connected Successfully!");
            }

            int choice;

            do {
                System.out.println("\n===== ORDER MANAGEMENT MENU =====");
                System.out.println("1. Insert Order");
                System.out.println("2. Search Orders by Status");
                System.out.println("3. Increase Order Quantity");
                System.out.println("4. Update Order Status");
                System.out.println("5. Total Order Value and Delete");
                System.out.println("6. Exit");
                System.out.print("Enter Choice: ");

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        System.out.print("Enter Order ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Customer Name: ");
                        String customer = sc.nextLine();

                        System.out.print("Enter Product Name: ");
                        String product = sc.nextLine();

                        System.out.print("Enter Quantity: ");
                        int quantity = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Unit Price: ");
                        double price = Double.parseDouble(sc.nextLine());

                        System.out.print("Enter Status: ");
                        String status = sc.nextLine();

                        if (quantity <= 0 || price <= 0) {
                            System.out.println(
                                "Quantity and price must be positive!"
                            );
                            break;
                        }

                        if (!status.equalsIgnoreCase("Pending") &&
                            !status.equalsIgnoreCase("Shipped") &&
                            !status.equalsIgnoreCase("Delivered") &&
                            !status.equalsIgnoreCase("Cancelled")) {
                            System.out.println("Invalid Status!");
                            break;
                        }

                        Order order = new Order(
                            id, customer, product, quantity, price, status
                        );

                        dao.insertOrder(order);
                        break;

                    case 2:
                        System.out.print("Enter Order Status: ");
                        String searchStatus = sc.nextLine();
                        dao.searchOrders(searchStatus);
                        break;

                    case 3:
                        System.out.print("Enter Order ID: ");
                        int updateId = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Additional Quantity: ");
                        int extra = Integer.parseInt(sc.nextLine());

                        dao.updateQuantity(updateId, extra);
                        break;

                    case 4:
                        System.out.print("Enter Order ID: ");
                        int statusId = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter New Status: ");
                        String newStatus = sc.nextLine();

                        dao.updateStatus(statusId, newStatus);
                        break;

                    case 5:
                        dao.calculateTotalValue();

                        System.out.print("Enter Order ID to Delete: ");
                        int deleteId = Integer.parseInt(sc.nextLine());

                        dao.deleteCancelledOrder(deleteId);
                        break;

                    case 6:
                        System.out.println("Thank you! Program Exited.");
                        break;

                    default:
                        System.out.println("Invalid Choice!");
                }

            } while (choice != 6);

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());

        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
