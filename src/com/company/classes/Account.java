package com.company.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Account {
    ArrayList<Contract> contracts = new ArrayList<>();
    int clientId;
    int billingDate;

    public Account(int clientId) {
        LocalDate date = java.time.LocalDate.now();
        int day = date.getDayOfMonth();
        int[] billingDays = {1, 4, 7, 11, 14, 17, 21, 24, 27};
        boolean isSet = false;
        for(int i : billingDays){
            if(date.getDayOfMonth()<=i){
                this.billingDate = date.getDayOfMonth();
                isSet = true;
                break;
            }
        }
        if(!isSet){
            if(date.getMonthValue()==2){
                this.billingDate = 1;
            } else {
                this.billingDate = date.lengthOfMonth();
            }
        }
        this.clientId = clientId;
    }
    public void uploadAccount() {
        int id = -1;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(
                    "INSERT INTO account(idClient, billingDate) VALUES ('"+ this.clientId +"','"+ this.billingDate +"');"
            );
            System.out.println("New account has been created");

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
}
