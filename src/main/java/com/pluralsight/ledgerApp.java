package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ledgerApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("=====Welcome To The Ledger App=====");
        while (true) {
            displayLedger(input);
        }

    }
    public static void displayLedger(Scanner input) {
        System.out.print("""
                Please Enter A Selection Below
                D: Add Deposit
                P: Make a Payment(Debit)
                L: Ledger
                X: Exit
                """);
        String selection = input.nextLine().trim().toUpperCase();
        switch (selection) {
            case "D":
            Deposit(input);
            break;
            case "P":
            Payment(input);
            break;
            case "L":
            Ledger(input);
            break;
            case "X":
            input.close();
            System.exit(0);
            break;
        }


    }
    public static void Deposit(Scanner input) {
        System.out.print("Enter Deposit Description: ");
        String description = input.nextLine();

        System.out.print("Enter Name From: ");
        String name = input.nextLine();

        System.out.print("Enter Deposit Amount: ");
        double amount = input.nextDouble();
        input.nextLine();

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String transactionLine = String.format("%s|%s|%s|%s|%.2f", date, time, description, name, amount);

        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {
            bufWriter.write(transactionLine);
            bufWriter.newLine();
            System.out.println("Deposited Successfully\n");
        } catch (IOException e) {
            System.out.println("An Unexpected Error Has Occurred " + e.getMessage());
        }

    }
    public static void Payment(Scanner input) {
        System.out.print("Enter Payment Description: ");
        String description = input.nextLine();

        System.out.print("Enter Name For: ");
        String name = input.nextLine();

        System.out.print("Enter Payment Amount: ");
        double amount = input.nextDouble();
        input.nextLine();

        amount = -Math.abs(amount);

        double balance = 0.0;

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String transactionLine = String.format("%s|%s|%s|%s|%.2f",date,time,description,name,amount);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {
            writer.write(transactionLine);
            writer.newLine();
            System.out.println("Payment Was Successful.");
            System.out.printf("-%.2f%n", balance + amount);
        } catch (IOException e) {
            System.out.println("An Unexpected Error Has Occurred " + e.getMessage());
        }
    }
    public static void Ledger(Scanner input) {


    }











}
