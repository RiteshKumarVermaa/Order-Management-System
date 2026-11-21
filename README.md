# Order Management System

A Java-based Order Management System developed using JDBC and MySQL to manage customer orders efficiently.

## Features

- Insert new customer orders
- Display orders by status
- Search orders with quantity greater than 2
- Update order quantity
- Update order status
- Calculate total order value
- Delete cancelled orders

## Technologies Used

- Java
- JDBC
- MySQL
- Maven

## Project Structure

```text
ordermanagement/
├── src/
│   ├── main/
│   │   └── java/com/order/
│   │       ├── App.java
│   │       ├── DBConnection.java
│   │       ├── Order.java
│   │       └── OrderDAO.java
│   └── test/
├── pom.xml
└── README.md
```

## Database

Database Name: `ecommerce_db`

Table Name: `Orders`

## How to Run

1. Install Java and MySQL.
2. Create the `ecommerce_db` database.
3. Configure your MySQL credentials in `DBConnection.java`.
4. Open the project in VS Code.
5. Run the application using Maven.

## Author

Ritesh Kumar Verma