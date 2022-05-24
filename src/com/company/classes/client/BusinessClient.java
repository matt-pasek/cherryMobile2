package com.company.classes.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BusinessClient extends Client{
    String nip;
    String regon;
    String companyName;

    int emailCount = 1;

    public BusinessClient(String nip, String regon, String companyName, String email){
        this.nip = nip;
        this.regon = regon;
        this.companyName = companyName;
        this.email = email;

        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT COUNT(email) as email FROM client WHERE email='"+email+"';"
            );
            while (rs.next()) {
                emailCount = rs.getInt("email");
            }
            if (emailCount > 0) {
                System.out.println("Client with this email already exists or there was an internal error.");
            } else {
                stmt.execute(
                        "INSERT INTO client(email)  VALUES ('"+email+"');"
                );
                rs = stmt.executeQuery(
                        "SELECT id FROM client WHERE email='"+email+"';"
                );
                while(rs.next()) {
                    int id = rs.getInt("id");
                    stmt.execute(
                            "INSERT INTO businessClient(pointer, nip, regon, companyName)  VALUES ('"+id+"', '"+nip+"', '"+regon+"', '"+companyName+"');"
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
