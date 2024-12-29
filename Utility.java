package com.mycompany.salesprofitcalculator.utility;

import com.mycompany.salesprofitcalculator.server.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class Utility {
    public void addEmployee(String id, String name, double salary) {
        String query = "INSERT INTO EMPLOYEES (EMPLOYEEID, EMPLOYEENAME, SALARY) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setDouble(3, salary);
            stmt.executeUpdate();
            System.out.println("Employee added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchEmployee(String id) {
        String query = "SELECT * FROM EMPLOYEES WHERE EMPLOYEEID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Employee ID: " + rs.getString("EMPLOYEEID"));
                System.out.println("Employee Name: " + rs.getString("EMPLOYEENAME"));
                System.out.println("Salary: " + rs.getDouble("SALARY"));
            } else {
                System.out.println("Employee not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployeeSalary(String id, double newSalary) {
        String query = "UPDATE EMPLOYEES SET SALARY = ? WHERE EMPLOYEEID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, newSalary);
            stmt.setString(2, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee salary updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Utility utility = new Utility();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Employee");
            System.out.println("2. Search Employee");
            System.out.println("3. Update Employee Salary");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Employee ID: ");
                    String id = scanner.next();
                    System.out.print("Enter Employee Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Salary: ");
                    double salary = scanner.nextDouble();
                    utility.addEmployee(id, name, salary);
                }
                case 2 -> {
                    System.out.print("Enter Employee ID to search: ");
                    String id = scanner.next();
                    utility.searchEmployee(id);
                }
                case 3 -> {
                    System.out.print("Enter Employee ID to update salary: ");
                    String id = scanner.next();
                    System.out.print("Enter new Salary: ");
                    double salary = scanner.nextDouble();
                    utility.updateEmployeeSalary(id, salary);
                }
                case 4 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
