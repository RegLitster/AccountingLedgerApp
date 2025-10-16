package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


//date|time|description|vendor|amount
public class Ledger {
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    public static String transactionFormater(Ledger ledger) {
       return String.format(ledger.getDate() + " | " + ledger.getTime() + " | " + ledger.getDescription() + " | " + ledger.getVendor() + " | " + ledger.getAmount());

    }



    public void saveTransaction(String description, String vendor, double amount) {
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String transactionLine = String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount);

        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {
            bufWriter.write(transactionLine);
            bufWriter.newLine();
            System.out.println("Summited Successfully\n");
        } catch (IOException e) {
            System.out.println("An Unexpected Error Has Occurred " + e.getMessage());
        }
    }

    public static List<Ledger> parseTransactions() {
        List<Ledger> transactions = new ArrayList<>();
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
            String line;
            bufReader.readLine();

            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 5) continue;

                String date = parts[0].trim();
                String time = parts[1].trim();
                String description = parts[2].trim();
                String vendor = parts[3].trim();
                double amount = Double.parseDouble(parts[4].trim());

                Ledger transaction = new Ledger(date, time, description, vendor, amount);
                transactions.add(transaction);
            }
        } catch (IOException e) {
            System.out.println("An Unexpected Error Has Occurred  " + e.getMessage());
        }

        return transactions;
    }

    public Ledger(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }


}