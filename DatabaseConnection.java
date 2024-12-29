package com.mycompany.salesprofitcalculator.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {

    public static Connection getConnection() {
        try {
            // Database URL for Apache Derby
            return DriverManager.getConnection(
                "jdbc:derby://localhost:1527/commissiondb", // Database URL
                "root",                                     // Username
                "rootdb"                                    // Password
            );
        } catch (SQLException e) {
            // Print the stack trace for debugging
            e.printStackTrace();
            System.out.println("Failed to connect to the database. Please check the connection settings.");
            return null;
        }
    }


    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("Database connected successfully!");
        } else {
            System.out.println("Database connection failed!");
        }
    }
}
