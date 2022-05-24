package com.company.classes.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BusinessClient extends Client{
    String nip;
    String regon;
    String companyName;

    public BusinessClient(String nip, String regon, String companyName){
        this.nip = nip;
        this.regon = regon;
        this.companyName = companyName;

        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(
                    "INSERT INTO businessClient(nip, regon, companyName) VALUES ('"+nip+"', '"+regon+"', '"+companyName+"')"
            );
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }
}
