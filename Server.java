package com.mycompany.salesprofitcalculator.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is running on port 5000...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Client connected.");

                    // Read input from client
                    String salesRepId = in.readLine(); // First input is the SalesRep ID
                    int laptopsSold = Integer.parseInt(in.readLine());
                    System.out.println("Received SalesRep ID: " + salesRepId + ", Laptops Sold: " + laptopsSold);

                    // Calculate sales profit
                    double laptopPrice = 90.0; // Example price per laptop
                    double salesProfit = laptopsSold * laptopPrice;

                    // Determine charge rate from database
                    double chargeRate = getChargeRate(salesProfit);

                    // Calculate commission value
                    double commissionValue = salesProfit * (chargeRate / 100);

                    // Send response to client
                    out.println("Sales Profit: " + salesProfit);
                    out.println("Commission Rate: " + chargeRate + "%");
                    out.println("Commission Value: " + commissionValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double getChargeRate(double salesProfit) {
        String chargeCode;

        // Determine the charge code based on sales profit
        if (salesProfit > 20000) {
            chargeCode = "C1"; // 10%
        } else if (salesProfit > 10000) {
            chargeCode = "C2"; // 5%
        } else {
            chargeCode = "C3"; // 0%
        }

        System.out.println("Determined Charge Code: " + chargeCode);

        // Fetch the charge rate from the database
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT CHARGERATE FROM CHARGERATES WHERE CHARGECODE = ?")) {
            stmt.setString(1, chargeCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double chargeRate = rs.getDouble("CHARGERATE");
                System.out.println("Fetched Charge Rate from DB: " + chargeRate);
                return chargeRate;
            } else {
                System.out.println("No matching Charge Rate found in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0; // Default charge rate if not found
    }
}
