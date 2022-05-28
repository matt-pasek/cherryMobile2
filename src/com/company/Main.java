package com.company;

import com.company.classes.Account;
import com.company.classes.client.Client;
import com.company.classes.client.ClientUpdate;
import com.company.classes.tariffs.PrepaidTariff;
import com.company.classes.tariffs.Tariff;

import java.math.BigDecimal;
import java.sql.*;

public class Main {

    public static void main(String[] args){
        try {
            SQL.setup();
            ClientUpdate clientUpdate = new ClientUpdate();
            PrepaidTariff tariff = new PrepaidTariff("NicZaDuzo", BigDecimal.valueOf(2.99), BigDecimal.valueOf(10.99), BigDecimal.valueOf(20.99), BigDecimal.valueOf(2.99));
            tariff.createTariff();

            //Client client2 = clientUpdate.CreateBusinessClient("894-307-81-49", "Poland", "SproSoft", "szypki@gmail.com");
            //clientUpdate.DeleteBusinessClient("894-307-81-49");

            Client client1 = clientUpdate.CreatePrivateClient("Anna", "Lis", "59121124832", "sagata@o2.pl");
            //assert client2 != null;
            //assert client1 != null;

            //Account acc2 = client1.createAccount("dziecience");

            //Account acc11 = client1.createAccount("modlitwa");

            //Account acc21 = client2.createAccount("mieszkanie");
            /*
            acc2.createContract(BigDecimal.valueOf(123456789), "SzystkoZaZero");
            acc2.createContract(BigDecimal.valueOf(567182746), "SzystkoZaZero");
            acc2.createContract(BigDecimal.valueOf(972612341), "SzystkoZaZero");
            acc11.createContract(BigDecimal.valueOf(980213472), "SzystkoZaZero");
            acc11.createContract(BigDecimal.valueOf(859326123), "SzystkoZaZero");
            */
            clientUpdate.DeletePrivateClient("59121124832");


            /*
            stmt.execute("""
                INSERT OR IGNORE INTO tariff(tariffName) VALUES ('SzystkoZaZero')
                """
            );
            stmt.execute("""
                INSERT OR IGNORE INTO prepaidTariff(pointer, smsPrice, mmsPrice, transferMbsPrice) VALUES (1, 2.99, 10.99, 20.99)
                """
            );
            */
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}