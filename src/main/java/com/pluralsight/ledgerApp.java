package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ledgerApp {
    public static void main(String[] args) {
        Ledger ledger = new Ledger("", "", "", "", 0);
        Scanner input = new Scanner(System.in);
        System.out.println("=====Welcome To The Ledger App=====");
        while (true) {
            ledgerHome(input, ledger);
        }

    }

    public static void ledgerHome(Scanner input, Ledger ledger) {
        System.out.print("""
                Main: Please Enter A Selection Below
                D: Add Deposit
                P: Make a Payment(Debit)
                L: Ledger
                X: Exit
                """);
        String selection = input.nextLine().trim().toUpperCase();
        switch (selection) {
            case "D":
                Deposit(input, ledger);
                break;
            case "P":
                Payment(input, ledger);
                break;
            case "L":
                ledgerDisplay(input);
                break;
            case "X":
                input.close();
                System.exit(0);
                break;
        }
    }

    public static void Deposit(Scanner input, Ledger ledger) {
        System.out.print("Enter Deposit Description: ");
        String description = input.nextLine();

        System.out.print("Enter Vendor From: ");
        String vendor = input.nextLine();

        System.out.print("Enter Deposit Amount: ");
        double amount = input.nextDouble();
        input.nextLine();

        ledger.saveTransaction(description, vendor, amount);

    }

    public static void Payment(Scanner input, Ledger ledger) {
        System.out.print("Enter Payment Description: ");
        String description = input.nextLine();

        System.out.print("Enter Vendor For: ");
        String vendor = input.nextLine();

        System.out.print("Enter Payment Amount: ");
        double amount = input.nextDouble();
        input.nextLine();

        amount = -Math.abs(amount);

        ledger.saveTransaction(description, vendor, amount);

    }

    public static void ledgerDisplay(Scanner input) {
        System.out.println("""
                Ledger: Please Make A Selection Below
                A: Display All
                D: Display Deposits
                P: Display Payments
                R: Reports
                H: Return to Home
                """);
        String selection = input.nextLine().trim().toUpperCase();
        switch (selection) {
            case "A":
                displayAll();
                break;
            case "D":
                displayDeposit();
                break;
            case "P":
                displayPayments();
                break;
            case "R":
                reports(input);
                break;
            case "H":
        }

    }

    public static void displayAll() {
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
            String input;

            while ((input = bufReader.readLine()) != null) {
                System.out.println(input);
            }
        } catch (IOException e) {
            System.out.println("An Unexpected Error Has Occurred " + e.getMessage());
        }
        System.out.println();
    }

    public static void displayDeposit() {
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
            String line;
            bufReader.readLine();

            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                double amount = Double.parseDouble(parts[4].trim());
                if (amount > 0) {
                    System.out.println(line);
                }

            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading deposits: " + e.getMessage());
        }
        System.out.println();
    }

    public static void displayPayments() {
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
            String line;
            bufReader.readLine();

            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                double amount = Double.parseDouble(parts[4].trim());
                if (amount < 0) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading payments: " + e.getMessage());
        }
        System.out.println();
    }

    public static void reports(Scanner input) {
        System.out.println("""
                Reports: Please Make A Selection Below
                1: Month To Date
                2: Previous Month
                3: Year To Date
                4: Search by Vendor
                5: Return to Home
                0: Back
                """);
        String selection = input.nextLine().trim().toUpperCase();
        switch (selection) {
            case "1":
                //MonthToDate
                break;
            case "2":
                //Previous Month
                break;
            case "3":
                //Year to Date
                break;
            case "4":
                //Previous Year
                break;
            case "5":
                //Search by vendor
                break;
            case "0":
                ledgerDisplay(input);
                break;
        }


    }
    public static void monthToDate(Ledger ledger) {
        List<Ledger> transactions = Ledger.parseTransactions();



    }





}