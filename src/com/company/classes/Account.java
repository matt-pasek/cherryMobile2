package com.company.classes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;

public class Account extends DBConnect {
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
            if(day<=i){
                this.billingDate = day;
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

    public Contract createContract(BigDecimal msisdn, String tariffName) {
        int tariffId = -1;
        int contractCount = -1;
        int accountId = -1;
        boolean clientIndividual = false;
        conn();
        try {
            rs = stmt.executeQuery(
                    "SELECT contractCount FROM client WHERE id =" + this.clientId + ";"
            );

            while(rs.next()) {
                contractCount = rs.getInt("contractCount");
            }

            rs = stmt.executeQuery(
                    "SELECT COUNT(pointer) as pointerCount FROM individualClient WHERE pointer =" + this.clientId + ";"
            );

            while(rs.next()) {
                clientIndividual = rs.getInt("pointerCount") != 0;
            }

            if (contractCount == -1) {
                System.out.println("Somethings wrong with database");
            } else if(clientIndividual && contractCount > 10){
                System.out.println("This client has 10 contracts already. New contract won't be.");
            } else {
                System.out.println("Client verified successfully");
                rs = stmt.executeQuery(
                        "SELECT id FROM tariff WHERE tariffName = '" + tariffName + "';"
                );

                while (rs.next()) {
                    tariffId = rs.getInt("id");
                }

                rs = stmt.executeQuery(
                        "SELECT id FROM account WHERE idClient =" + this.clientId + " AND accountName = '" + this.name + "';"
                );

                while(rs.next()) {
                    accountId = rs.getInt("id");
                }

                if(tariffId == -1) {
                    System.out.println("There is not such a tariff, or there was an internal database error, new contract has not been added");
                } else if(accountId == -1){
                    System.out.println("There was an error with getting account id :)");
                } else {
                    System.out.println("Contract has been created");
                    Contract contract = new Contract(msisdn, tariffId);
                    contract.uploadContract(accountId);
                    disconn();
                    return contract;
                }
                disconn();
                return null;
            }
            disconn();
            return null;
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void uploadAccount() {
        conn();
        try {
            stmt.execute(
                    "INSERT INTO account(idClient, billingDate, accountName) VALUES ('"+ this.clientId +"','"+ this.billingDate +"','"+ this.name +"');"
            );
            System.out.println("New account has been created");
            disconn();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public void destroyAccount() {
        conn();
        try {
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
