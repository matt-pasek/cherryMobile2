package com.company.classes.client;

import com.company.classes.Account;
import com.company.classes.DBConnect;

import java.util.ArrayList;

public abstract class Client extends DBConnect {
    String email;
    int contractCount;
    ArrayList<Account> accounts = new ArrayList<Account>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getContractCount() {
        return contractCount;
    }

    public void setContractCount(int contractCount) {
        this.contractCount = contractCount;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public Account createAccount(String name){
        conn();
        int id = -1;
        try {
            rs = stmt.executeQuery(
                    "SELECT id FROM client WHERE email='"+this.email+"';"
            );

            while (rs.next()) {
                id = rs.getInt("id");
            }

            if (id == -1) {
                System.err.println("Account has not been created.");
                disconn();
                return null;
            } else {
                Account acc = new Account(id, name);
                acc.uploadAccount();
                this.accounts.add(acc);
                disconn();
                return acc;
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    public abstract void uploadClient();
}
