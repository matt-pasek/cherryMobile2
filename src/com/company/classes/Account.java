package com.company.classes;

import com.company.classes.tariffs.Tariff;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Account {
    ArrayList<Contract> contracts = new ArrayList<>();
    int clientId;
    int billingDate;
    String name;

    public Account(int clientId, String name) {
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
        this.name = name;
    }

    public Contract createContract(int msisdn, String tariffName) {
        int tariffId = -1;
        int contractCount = -1;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT id FROM tariff WHERE tariffName = '" + tariffName + "';"
            );
            while (rs.next()) {
                tariffId = rs.getInt("id");
            }

            rs = stmt.executeQuery(
                    "SELECT COUNT(msisdn) as msisdn FROM contract WHERE idAccount =" + this.clientId + ";"
            );

            while(rs.next()){
                contractCount = rs.getInt("msisdn");
            }

            rs = stmt.executeQuery(
                    "SELECT COUNT(contractCount) FROM client"
            );
            if(tariffId == -1) {
                System.out.println("New tariff has been created");
                Contract contract = new Contract(msisdn, tariffId);
                //contract.uploadContract();
                return contract;
            } else {
                System.out.println("Tariff has been found, not adding new");
                Contract contract = new Contract(msisdn, tariffId);
                return contract;
            }
            
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        return null;
    }

    public void uploadAccount() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(
                    "INSERT INTO account(idClient, billingDate, accountName) VALUES ('"+ this.clientId +"','"+ this.billingDate +"','"+ this.name +"');"
            );
            System.out.println("New account has been created");

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public void destroyAccount() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            boolean isDeleted = stmt.execute(
                    "DELETE FROM account WHERE accountName = '" + this.name + "' AND idClient =" + this.clientId + ";"
            );
            String message = (isDeleted) ? "Account has not been deleted, why? We dont know." : "Account has been deleted";
            System.out.println(message);
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
}
