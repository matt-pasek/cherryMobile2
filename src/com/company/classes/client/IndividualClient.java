package com.company.classes.client;

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
        this.contractCount = 0;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public void uploadClient() {
        int emailCount = 1;
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
                        "INSERT INTO client(email, contractCount) VALUES ('"+this.email+"', '"+this.contractCount+"');"
                );
                rs = stmt.executeQuery(
                        "SELECT id FROM client WHERE email='"+this.email+"';"
                );
                while(rs.next()) {
                    int id = rs.getInt("id");
                    stmt.execute(
                            "INSERT INTO individualClient(pointer, fName, lName, pesel)  VALUES ('"+id+"', '"+this.fName+"', '"+this.lName+"', '"+this.pesel+"');"
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