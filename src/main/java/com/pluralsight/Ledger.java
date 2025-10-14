package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

//date|time|description|vendor|amount
public class Ledger {
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    public void saveTransaction(String description, String vendor, double amount){
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String transactionLine = String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount);

        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {
            bufWriter.write(transactionLine);
            bufWriter.newLine();
            System.out.println("Deposited Successfully\n");
        } catch (IOException e) {
            System.out.println("An Unexpected Error Has Occurred " + e.getMessage());
        }
    }


    public Ledger(String date, String time, String description, String vendor, double amount) {
        this.date = "";
        this.time = "";
        this.description = "";
        this.vendor = "";
        this.amount = 0;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}
