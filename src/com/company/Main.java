package com.company;

import com.company.classes.Account;
import com.company.classes.client.Client;
import com.company.classes.client.ClientUpdate;

import java.nio.channels.AcceptPendingException;
import java.sql.*;

public class Main {

    public static void main(String[] args){
        try {
            SQL.setup();
            Client client2 = ClientUpdate.CreateBusinessClient("894-307-81-49", "Poland", "SproSoft", "szypki@gmail.com");
            //ClientUpdate.DeleteBusinessClient("894-307-81-49");

            Client client1 = ClientUpdate.CreatePrivateClient("Anna", "Lis", "59121124832", "sagata@o2.pl");
            //ClientUpdate.DeletePrivateClient("06243005875");
            assert client2 != null;
            assert client1 != null;

            Account acc2 = client1.createAccount("dziecience");

            Account acc11 = client1.createAccount("modlitwa");

            Account acc21 = client2.createAccount("mieszkanie");
            acc21.destroyAccount();
            ClientUpdate.DeletePrivateClient("59121124832");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}