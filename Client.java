package com.mycompany.salesprofitcalculator.client;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        new Thread(new ClientRunnable()).start();
    }
}

class ClientRunnable implements Runnable {
    @Override
    public void run() {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.print("Enter Sales Representative ID: ");
            String salesRepID = reader.readLine();

            int laptopsSold;
            while (true) {
                System.out.print("Enter Number of Laptops Sold: ");
                String input = reader.readLine();
                try {
                    laptopsSold = Integer.parseInt(input); // Attempt to parse the input
                    break; // Exit the loop if parsing is successful
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            // Send data to the server
            out.println(salesRepID);
            out.println(laptopsSold);

            // Receive and display response
            System.out.println("Server Response:");
            System.out.println("Sales Profit: " + in.readLine());
            System.out.println("Commission Rate: " + in.readLine());
            System.out.println("Commission Value: " + in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
