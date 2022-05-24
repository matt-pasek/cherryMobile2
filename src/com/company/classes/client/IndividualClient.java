package com.company.classes.client;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class IndividualClient extends Client{
    String fName;
    String lName;
    String pesel;

    public IndividualClient(String fName, String lName, String pesel, String email){
        this.fName = fName;
        this.lName = lName;
        this.pesel = pesel;
        this.email = email;

        int emailCount = 1;

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
                        "SELECT id FROM client WHERE email='"+ email + "';"
                );
                while(rs.next()) {
                    int id = rs.getInt("id");
                    System.out.println(pesel);
                    stmt.execute(
                            "INSERT INTO individualClient(pointer, fName, lName, pesel)  VALUES ('"+id+"', '"+fName+"', '"+lName+"', '"+pesel+"');"
                    );
                    System.out.println("New individual client has been created");
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}