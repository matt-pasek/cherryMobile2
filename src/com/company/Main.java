package com.company;

import com.company.classes.Account;
import com.company.classes.client.ClientUpdate;

import java.nio.channels.AcceptPendingException;
import java.sql.*;

public class Main {

    public static void main(String[] args){
        try {
            SQL.setup();
            //ClientUpdate.CreateBusinessClient("894-307-81-49", "Poland", "SproSoft");

            ClientUpdate.CreatePrivateClient("Anna", "Lis", "06243005875", "sagata@o2.pl");
            //ClientUpdate.CreatePrivateClient("Anna", "Lis", "59121124832", "fagata@o2.pl");
            //ClientUpdate.DeleteBusinessClient("894-307-81-49");
            ClientUpdate.DeletePrivateClient("06243005875");


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}