package com.company.classes.client;

import com.company.classes.Account;
import java.util.ArrayList;

public class Client {
    String email;
    ArrayList<Account> accounts = new ArrayList<>();

    public void addAccount(){
        accounts.add(new Account());
    }
}
