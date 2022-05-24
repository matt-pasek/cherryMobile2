package com.company.classes.client;

import com.company.classes.client.BusinessClient;
import com.company.classes.client.IndividualClient;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClientUpdate {
    //CHECK CLIENTS
    public static boolean checkIfPrivateClientExist(BigDecimal PESEL){
        int countThisPesel = 1;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT COUNT(PESEL) as pesel FROM individualClient WHERE PESEL="+PESEL+";"
            );
            while (rs.next()) {
                countThisPesel = rs.getInt("pesel");
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        if (countThisPesel == 0){
            return false;
        }else{
            return true;
        }
    }

    public static boolean checkIfBusinessClientExist(String NIP){
        int countThisNip = 1;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT COUNT(nip) as nip FROM businessClient WHERE nip='"+NIP+"';"
            );
            while (rs.next()) {
                countThisNip = rs.getInt("nip");
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        if (countThisNip == 0){
            return false;
        }else{
            return true;
        }
    }

    //ADD CLIENTS
    public static BusinessClient CreateBusinessClient(String nip, String regon, String companyName){
        //sprawdzanie nipu
        //sprawdzanie czy nie ma już takiej firmy
        if(checkIfBusinessClientExist(nip) == false) {
            if(nip.length() == 13) {
                Character first = nip.charAt(3);
                Character secound = nip.charAt(7);
                Character third = nip.charAt(10);
                if(first=='-' && secound=='-' && third=='-') {
                    System.out.println("A new business customer has been created");
                    return new BusinessClient(nip, regon, companyName);
                }else {
                        System.out.println("Error nip xxx-xxx-xx-xx");
                        return null;
                    }
            }else {
                System.out.println("Error to short nip");
                return null;
            }
        }else{
            System.out.println("Client with that NIP is already exist");
            return null;
        }
    }


    public static IndividualClient CreatePrivateClient(String Name, String Surname, String PESELstr, String email){
        BigDecimal PESELint = new BigDecimal(PESELstr);
        if(checkIfPrivateClientExist(PESELint) == false) {
            if(PESELstr.length() == 11) {
                //sprawdzanie PESELU
                int first = Integer.parseInt(String.valueOf(PESELstr.charAt(0)));
                int secound = Integer.parseInt(String.valueOf(PESELstr.charAt(1)));
                int third = Integer.parseInt(String.valueOf(PESELstr.charAt(2)));
                int fourth = Integer.parseInt(String.valueOf(PESELstr.charAt(3)));
                int fifth = Integer.parseInt(String.valueOf(PESELstr.charAt(4)));
                int sixth = Integer.parseInt(String.valueOf(PESELstr.charAt(5)));
                int seventh = Integer.parseInt(String.valueOf(PESELstr.charAt(6)));
                int eighth = Integer.parseInt(String.valueOf(PESELstr.charAt(7)));
                int ninth = Integer.parseInt(String.valueOf(PESELstr.charAt(8)));
                int tenth = Integer.parseInt(String.valueOf(PESELstr.charAt(9)));
                int result = 10 - Integer.parseInt(String.valueOf(PESELstr.charAt(10)));
                if ((first * 1 + secound * 3 + third * 7 + fourth * 9 + fifth * 1 + sixth * 3 + seventh * 7 + eighth * 9 + ninth * 1 + tenth * 3) % 10 == result) {
                    System.out.println("PESEL validated successfully.");
                    return new IndividualClient(Name, Surname, PESELstr, email);
                } else {
                    System.out.println("Error PESEL");
                    return null;
                }
            }else{
                System.out.println("Error to short PESEL");
                return null;
            }
        }else{
            System.out.println("Client with that PESEL is already exist");
            return null;
        }
    }

    //DELETE CLIENTS

    public static void DeleteBusinessClient(String nip){
        if(checkIfBusinessClientExist(nip) == true) {
            System.out.println("The buisnes client has been removedd");
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:db.sqlite";
                Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                stmt.execute(
                        "DELETE FROM businessClient WHERE nip='"+ nip +"';"
                );

            } catch (Exception e) {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }

        }else{
            System.out.println("The client does not exist");
        }
    }

    public static void DeletePrivateClient(String PESELstr){
        BigDecimal PESELint = new BigDecimal(PESELstr);
        if(checkIfPrivateClientExist(PESELint) == true) {
            System.out.println("Found client with given PESEL");
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:db.sqlite";
                Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT pointer FROM individualClient WHERE pesel='" + PESELstr +"';"
                );
                while(rs.next()) {
                    int pointer = rs.getInt("pointer");
                    stmt.execute("DELETE FROM client WHERE id=" + pointer +";");
                }

                stmt.execute(
                        "DELETE FROM individualClient WHERE pesel='" + PESELstr +"';"
                );

            } catch (Exception e) {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }

        }else{
            System.out.println("The client does not exist");
        }
    }
}
