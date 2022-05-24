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
    public static boolean checkIfPrivateClientExist(String pesel){
        int countThisPesel = 1;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT COUNT(PESEL) as pesel FROM individualClient WHERE PESEL='"+pesel+"';"
            );
            while (rs.next()) {
                countThisPesel = rs.getInt("pesel");
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return countThisPesel != 0;
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

        return countThisNip != 0;
    }

    //ADD CLIENTS
    public static BusinessClient CreateBusinessClient(String nip, String regon, String companyName, String email){
        //sprawdzanie nipu
        //sprawdzanie czy nie ma już takiej firmy
        if(!checkIfBusinessClientExist(nip)) {
            if(nip.length() == 13) {
                char first = nip.charAt(3);
                char secound = nip.charAt(7);
                char third = nip.charAt(10);
                if(first=='-' && secound=='-' && third=='-') {
                    System.out.println("NIP validated successfully.");
                    return new BusinessClient(nip, regon, companyName, email);
                }else {
                        System.out.println("Error nip xxx-xxx-xx-xx");
                        return null;
                    }
            }else {
                System.out.println("Error to short nip");
                return null;
            }
        }else{
            return new BusinessClient(nip, regon, companyName, email);
        }
    }


    public static IndividualClient CreatePrivateClient(String Name, String Surname, String PESELstr, String email){
        if(!checkIfPrivateClientExist(PESELstr)) {
            if(PESELstr.length() == 11) {
                //sprawdzanie PESELU
                int first = Integer.parseInt(String.valueOf(PESELstr.charAt(0)));
                int second = Integer.parseInt(String.valueOf(PESELstr.charAt(1)));
                int third = Integer.parseInt(String.valueOf(PESELstr.charAt(2)));
                int fourth = Integer.parseInt(String.valueOf(PESELstr.charAt(3)));
                int fifth = Integer.parseInt(String.valueOf(PESELstr.charAt(4)));
                int sixth = Integer.parseInt(String.valueOf(PESELstr.charAt(5)));
                int seventh = Integer.parseInt(String.valueOf(PESELstr.charAt(6)));
                int eighth = Integer.parseInt(String.valueOf(PESELstr.charAt(7)));
                int ninth = Integer.parseInt(String.valueOf(PESELstr.charAt(8)));
                int tenth = Integer.parseInt(String.valueOf(PESELstr.charAt(9)));
                int result = 10 - Integer.parseInt(String.valueOf(PESELstr.charAt(10)));
                if ((first + second * 3 + third * 7 + fourth * 9 + fifth + sixth * 3 + seventh * 7 + eighth * 9 + ninth + tenth * 3) % 10 == result) {
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
            System.out.println("Client with this PESEL already exists.");
            return new IndividualClient(Name, Surname, PESELstr, email);
        }
    }

    //DELETE CLIENTS

    public static void DeleteBusinessClient(String nip){
        int pointer = 0;
        String regon = "";
        String companyName = "";
        String email = "";

        if(checkIfBusinessClientExist(nip)) {
            System.out.println("The business client has been removed");
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:db.sqlite";
                Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM businessClient WHERE nip='"+ nip + "';"
                );
                while(rs.next()) {
                    pointer = rs.getInt("pointer");
                    regon = rs.getString("regon");
                    companyName = rs.getString("companyName");

                }

                stmt.execute(
                        "INSERT INTO oldBusinessClient(id, email, nip, regon, companyName) VALUES ("+ pointer +", '" + email + "', '" + nip +"', '" + regon +"', '"+ companyName +"');"
                );

                rs = stmt.executeQuery(
                        "SELECT pointer FROM businessClient WHERE nip='" + nip +"';"
                );
                while(rs.next()) {
                    pointer = rs.getInt("pointer");
                    stmt.execute("DELETE FROM client WHERE id=" + pointer +";");
                }

                stmt.execute(
                        "DELETE FROM businessClient WHERE nip='" + nip +"';"
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
        int pointer = 0;
        String fName = "";
        String lName = "";
        String email = "";
        BigDecimal PESELint = new BigDecimal(PESELstr);

        if(checkIfPrivateClientExist(PESELstr)) {
            System.out.println("Found client with given PESEL");
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:db.sqlite";
                Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM individualClient WHERE pesel='"+ PESELstr + "';"
                );
                while(rs.next()) {
                     pointer = rs.getInt("pointer");
                     fName = rs.getString("fName");
                     lName = rs.getString("lName");;
                }

                stmt.execute(
                        "INSERT INTO oldIndividualClient(id, email, fName, lName, pesel) VALUES ("+ pointer +", '" + email + "', '" + fName +"', '" + lName +"', '"+ PESELstr +"');"
                );

                rs = stmt.executeQuery(
                        "SELECT pointer FROM individualClient WHERE pesel='" + PESELstr +"';"
                );
                while(rs.next()) {
                    pointer = rs.getInt("pointer");
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
