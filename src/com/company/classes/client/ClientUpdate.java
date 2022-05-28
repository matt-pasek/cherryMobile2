package com.company.classes.client;

import com.company.classes.DBConnect;



public class ClientUpdate extends DBConnect {
    //CHECK CLIENTS
    public boolean checkIfPrivateClientExist(String pesel){
        conn();
        int countThisPesel = 1;
        try {
            rs = stmt.executeQuery(
                    "SELECT COUNT(PESEL) as pesel FROM individualClient WHERE PESEL='"+pesel+"';"
            );
            while (rs.next()) {
                countThisPesel = rs.getInt("pesel");
            }
            disconn();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return countThisPesel != 0;
    }

    public boolean checkIfBusinessClientExist(String NIP){
        conn();
        int countThisNip = 1;
        try {
           rs = stmt.executeQuery(
                    "SELECT COUNT(nip) as nip FROM businessClient WHERE nip='"+NIP+"';"
            );
            while (rs.next()) {
                countThisNip = rs.getInt("nip");
            }
            disconn();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return countThisNip != 0;
    }

    //ADD CLIENTS
    public BusinessClient CreateBusinessClient(String nip, String regon, String companyName, String email){
        //sprawdzanie nipu
        //sprawdzanie czy nie ma już takiej firmy
        if(!checkIfBusinessClientExist(nip)) {
            if(nip.length() == 13) {
                char first = nip.charAt(3);
                char secound = nip.charAt(7);
                char third = nip.charAt(10);
                if(first=='-' && secound=='-' && third=='-') {
                    System.out.println("NIP validated successfully.");
                    BusinessClient client = new BusinessClient(nip, regon, companyName, email);
                    client.uploadClient();
                    return client;
                }else {
                        System.out.println("Error nip xxx-xxx-xx-xx");
                        return null;
                    }
            }else {
                System.out.println("Error to short nip");
                return null;
            }
        } else {
            System.out.println("Client with this NIP already exists.");
            return new BusinessClient(nip, regon, companyName, email);
        }
    }


    public IndividualClient CreatePrivateClient(String Name, String Surname, String PESELstr, String email){
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
                    IndividualClient client = new IndividualClient(Name, Surname, PESELstr, email);
                    client.uploadClient();
                    return client;
                } else {
                    System.out.println("Error PESEL");
                    return null;
                }
            } else {
                System.out.println("Error to short PESEL");
                return null;
            }
        } else {
            System.out.println("Client with this PESEL already exists.");
            return new IndividualClient(Name, Surname, PESELstr, email);
        }
    }

    //DELETE CLIENTS

    public void DeleteBusinessClient(String nip){
        int pointer = 0;
        int accountId = -1;
        String regon = "";
        String companyName = "";
        String email = "";

        if(checkIfBusinessClientExist(nip)) {
            System.out.println("The business client has been removed");
            conn();
            try {
                rs = stmt.executeQuery(
                        "SELECT * FROM businessClient WHERE nip='"+ nip + "';"
                );
                while(rs.next()) {
                    pointer = rs.getInt("pointer");
                    regon = rs.getString("regon");
                    companyName = rs.getString("companyName");
                    stmt.execute(
                            "INSERT INTO oldBusinessClient(id, email, nip, regon, companyName) VALUES ("+ pointer +", '" + email + "', '" + nip +"', '" + regon +"', '"+ companyName +"');"
                    );
                }

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

                rs = stmt.executeQuery(
                        "SELECT id FROM account WHERE idClient=" + pointer + ";"
                );

                while(rs.next()) {
                    accountId = rs.getInt("id");
                    stmt.execute(
                            "DELETE FROM contract WHERE idAccount=" + accountId + ";"
                    );
                }

                stmt.execute(
                        "DELETE FROM account WHERE idClient=" + pointer + ";"
                );
                disconn();
            } catch (Exception e) {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }

        }else{
            System.out.println("The client does not exist");
        }
    }

    public void DeletePrivateClient(String pesel){
        int pointer = -1;
        int accountId = -1;
        String fName = "";
        String lName = "";
        String email = "";

        conn();
        if(checkIfPrivateClientExist(pesel)) {
            System.out.println("Found client with given PESEL");
            try {
                rs = stmt.executeQuery(
                        "SELECT * FROM individualClient WHERE pesel='"+ pesel + "';"
                );
                while(rs.next()) {
                     pointer = rs.getInt("pointer");
                     fName = rs.getString("fName");
                     lName = rs.getString("lName");
                    stmt.execute(
                            "INSERT INTO oldIndividualClient(id, email, fName, lName, pesel) VALUES ("+ pointer +", '" + email + "', '" + fName +"', '" + lName +"', '"+ pesel +"');"
                    );
                }

                rs = stmt.executeQuery(
                        "SELECT pointer FROM individualClient WHERE pesel='" + pesel +"';"
                );

                while(rs.next()) {
                    pointer = rs.getInt("pointer");
                    stmt.execute("DELETE FROM client WHERE id=" + pointer +";");
                }

                stmt.execute(
                        "DELETE FROM individualClient WHERE pesel='" + pesel +"';"
                );

                rs = stmt.executeQuery(
                        "SELECT id FROM account WHERE idClient=" + pointer + ";"
                );

                while(rs.next()) {
                    accountId = rs.getInt("id");
                    stmt.execute(
                            "DELETE FROM contract WHERE idAccount=" + accountId + ";"
                    );
                }

                stmt.execute(
                    "DELETE FROM account WHERE idClient=" + pointer + ";"
                );
                disconn();
            } catch (Exception e) {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }

        }else{
            System.out.println("The client does not exist");
        }
    }
}
