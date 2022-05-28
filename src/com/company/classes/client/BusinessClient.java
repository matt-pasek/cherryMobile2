package com.company.classes.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BusinessClient extends Client {
    String nip;
    String regon;
    String companyName;

    int emailCount = 1;

    public BusinessClient(String nip, String regon, String companyName, String email){
        this.nip = nip;
        this.regon = regon;
        this.companyName = companyName;
        this.email = email;
        this.contractCount = 0;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(int emailCount) {
        this.emailCount = emailCount;
    }

    @Override
    public void uploadClient(){
        conn();
        try {
            rs = stmt.executeQuery(
                    "SELECT COUNT(email) as email FROM client WHERE email='"+this.email+"';"
            );
            while (rs.next()) {
                emailCount = rs.getInt("email");
            }
            if (emailCount > 0) {
                System.out.println("Client with this email already exists or there was an internal error.");
            } else {
                stmt.execute(
                        "INSERT INTO client(email, contractCount) VALUES ('" + this.email + "','" + this.contractCount + "');"
                );
                rs = stmt.executeQuery(
                        "SELECT id FROM client WHERE email='" + email + "';"
                );
                while (rs.next()) {
                    int id = rs.getInt("id");
                    stmt.execute(
                            "INSERT INTO businessClient(pointer, nip, regon, companyName)  VALUES ('" + id + "', '" + this.nip + "', '" + this.regon + "', '" + this.companyName + "');"
                    );
                    System.out.println("New business client has been created");
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
