package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
        List<Ledger> transactions = Ledger.parseTransactions();
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
                displayAll(transactions);
                break;
            case "D":
                displayDeposit(transactions);
                break;
            case "P":
                displayPayments(transactions);
                break;
            case "R":
                reports(input);
                break;
            case "H":
        }
    }

    public static void displayAll(List<Ledger> transactions) {
        for (Ledger ledger : transactions) {
            System.out.println(Ledger.transactionFormater(ledger));
        }
        System.out.println();
    }

    public static void displayDeposit(List<Ledger> transactions) {
        for (Ledger ledger : transactions) {
            if (ledger.getAmount() > 0) {
                System.out.println(Ledger.transactionFormater(ledger));
            }
        }
        System.out.println();
    }

    public static void displayPayments(List<Ledger> transactions) {
        for (Ledger ledger : transactions) {
            if (ledger.getAmount() < 0) {
                System.out.println(Ledger.transactionFormater(ledger));
            }
        }
        System.out.println();
    }

    public static void reports(Scanner input) {
        List<Ledger> transactions = Ledger.parseTransactions();
        System.out.println("""
                Reports: Please Make A Selection Below
                1: Month To Date
                2: Previous Month
                3: Year To Date
                4: Previous Year
                5: Search By Vendor
                0: Back
                """);
        String selection = input.nextLine().trim().toUpperCase();
        switch (selection) {
            case "1":
                monthToDate(transactions);
                break;
            case "2":
                previousMonth(transactions);
                break;
            case "3":
                yearToDate(transactions);
                break;
            case "4":
                previousYear(transactions);
                break;
            case "5":
                searchVendor(input, transactions);
                break;
            case "0":
                ledgerDisplay(input);
                break;
        }
    }

    public static void monthToDate(List<Ledger> transactions) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Ledger ledger : transactions) {
            try {
                LocalDate transactionDate = LocalDate.parse(ledger.getDate(), formatter);

                if (transactionDate.getYear() == today.getYear() &&
                        transactionDate.getMonth() == today.getMonth()) {
                    System.out.println(Ledger.transactionFormater(ledger));
                }
            } catch (Exception e) {
                System.out.println("An Unexpected Error Has Occurred " + e.getMessage());
            }
        }
        System.out.println();
    }

    public static void previousMonth(List<Ledger> transactions) {
        LocalDate today = LocalDate.now();
        YearMonth previousMonth = YearMonth.from(today.minusMonths(1));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = previousMonth.atDay(1);
        LocalDate endDate = previousMonth.atEndOfMonth();

        for (Ledger ledger : transactions) {
            try {
                LocalDate transactionDate = LocalDate.parse(ledger.getDate(), formatter);

                if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                    System.out.println(Ledger.transactionFormater(ledger));
                }
            } catch (Exception e) {
                System.out.println("An Unexpected Error Has Occurred " + e.getMessage());
            }
        }
        System.out.println();
    }

    public static void yearToDate(List<Ledger> transactions) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Ledger ledger : transactions) {
            try {
                LocalDate transactionDate = LocalDate.parse(ledger.getDate(), formatter);

                if (transactionDate.getYear() == today.getYear()) {
                    System.out.println(Ledger.transactionFormater(ledger));
                }
            } catch (Exception e) {
                System.out.println("An Unexpected Error Has Occurred " + e.getMessage());
            }
        }
        System.out.println();
    }

    public static void previousYear(List<Ledger> transactions) {
        LocalDate today = LocalDate.now();
        LocalDate startYear = LocalDate.of(today.getYear() - 1, 1, 1);
        LocalDate endYear = LocalDate.of(today.getYear() - 1, 12, 31);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Ledger ledger : transactions) {
            try {
                LocalDate transactionDate = LocalDate.parse(ledger.getDate(), formatter);

                if ((transactionDate.isEqual(startYear) || transactionDate.isAfter(startYear)) &&
                        (transactionDate.isEqual(endYear) || transactionDate.isBefore(endYear))) {
                    System.out.println(Ledger.transactionFormater(ledger));
                }
            } catch (Exception e) {
                System.out.println("An Unexpected Error Has Occurred " + e.getMessage());
            }
        }
        System.out.println();
    }

    public static void searchVendor(Scanner input, List<Ledger> transactions) {
        System.out.println("Please Enter Vendor: ");
        String vendorName = input.nextLine().trim().toUpperCase();
        for (Ledger ledger : transactions) {
            if (ledger.getVendor().toUpperCase().equals(vendorName)) {
                System.out.println(Ledger.transactionFormater(ledger));
            }
        }
        System.out.println();
    }

}