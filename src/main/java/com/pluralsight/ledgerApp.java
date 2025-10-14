package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ledgerApp {
    public static void main(String[] args) {
        Ledger ledger = new Ledger("","","","",0);
        Scanner input = new Scanner(System.in);
        System.out.println("=====Welcome To The Ledger App=====");
        while (true) {
            ledgerHome(input,ledger);
        }

    }
    public static void ledgerHome(Scanner input,Ledger ledger) {
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
                Deposit(input,ledger);
                break;
            case "P":
                Payment(input,ledger);
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

        System.out.print("Enter Name From: ");
        String vendor = input.nextLine();

        System.out.print("Enter Deposit Amount: ");
        double amount = input.nextDouble();
        input.nextLine();

        ledger.saveTransaction(description,vendor,amount);

    }
    public static void Payment(Scanner input,Ledger ledger) {
        System.out.print("Enter Payment Description: ");
        String description = input.nextLine();

        System.out.print("Enter Name For: ");
        String name = input.nextLine();

        System.out.print("Enter Payment Amount: ");
        double amount = input.nextDouble();
        input.nextLine();

        amount = -Math.abs(amount);

        ledger.saveTransaction(description,name,amount);

    }
    public static void ledgerDisplay(Scanner input) {
        System.out.println("""
                Please Make A Selection Below
                A: Display All
                D: Display Deposits
                P: Display Payments
                R: Reports
                H: Return to Home
                """);
        String selection = input.nextLine().trim().toUpperCase();
        switch (selection){
            case "A":
         //       displayAll
            break;
            case "D":
          //      displayDeposits
            break;
            case "P":
          //      displayPayments
            break;
            case "R":
           //     reports
            break;

        }

    }











}