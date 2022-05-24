package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
    static void setup() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        System.out.println("Configuring database");
        String url = "jdbc:sqlite:db.sqlite";
        Connection con = DriverManager.getConnection(url);
        Statement stmt = con.createStatement();
        stmt.execute("create table if not exists businessClient\n" +
                "(\n" +
                "pointer     integer not null constraint businessClient_pk primary key,\n" +
                "nip         text,\n" +
                "regon       text,\n" +
                "companyName text\n" +
                ");\n" +
                "\n" +
                "create unique index businessClient_pointer_uindex\n" +
                "    on businessClient (pointer);\n" +
                "\n"
        );

        stmt.execute("create table if not exists client\n" +
                "(\n" +
                "id    integer not null constraint client_pk primary key autoincrement,\n" +
                "email text\n" +
                ");\n" +
                "\n" +
                "create unique index client_id_uindex\n" +
                "    on client (id);\n" +
                "\n"
        );

        stmt.execute("create table if not exists individualClient\n" +
                "(\n" +
                "pointer integer not null constraint individualClient_pk primary key,\n" +
                "fName   text,\n" +
                "lName   text,\n" +
                "pesel   text\n" +
                ");\n" +
                "\n" +
                "create unique index individualClient_pointer_uindex\n" +
                "    on individualClient (pointer);\n" +
                "\n"
        );
        stmt.execute("create table if not exists account\n" +
                "(\n" +
                "    id          integer not null\n" +
                "        constraint account_pk\n" +
                "            primary key autoincrement,\n" +
                "    idClient    integer not null,\n" +
                "    billingDate int\n" +
                ");\n" +
                "\n" +
                "create unique index account_id_uindex\n" +
                "    on account (id);\n" +
                "\n");
        stmt.execute("create table if not exists contract\n" +
                "(\n" +
                "    msisdn           integer not null,\n" +
                "    idAccount        integer not null,\n" +
                "    idTariff         integer not null,\n" +
                "    callCount        integer,\n" +
                "    smsCount         integer,\n" +
                "    mmsCount         integer,\n" +
                "    transferMbsCount integer\n" +
                ");\n" +
                "\n" +
                "create unique index contract_msisdn_uindex\n" +
                "    on contract (msisdn);\n" +
                "\n");
        stmt.execute("create table if not exists postpaidTariff\n" +
                "(\n" +
                "    pointer          integer not null\n" +
                "        constraint postpaidTariff_pk\n" +
                "            primary key,\n" +
                "    smsPrice         real,\n" +
                "    mmsPrice         real,\n" +
                "    transferMbsPrice real\n" +
                ");\n" +
                "\n" +
                "create unique index postpaidTariff_pointer_uindex\n" +
                "    on postpaidTariff (pointer);\n" +
                "\n");
        stmt.execute("create table if not exists prepaidTariff\n" +
                "(\n" +
                "    pointer            integer not null\n" +
                "        constraint prepaidTariff_pk\n" +
                "            primary key,\n" +
                "    price              real,\n" +
                "    callAmount         integer,\n" +
                "    smsAmount          integer,\n" +
                "    mmsAmount          integer,\n" +
                "    transferMbsAmount  integer,\n" +
                "    extraCallPrice     real,\n" +
                "    extraSmsPrice      real,\n" +
                "    extraMmsPrice      real,\n" +
                "    extraTransferPrice real\n" +
                ");\n" +
                "\n" +
                "create unique index prepaidTariff_pointer_uindex\n" +
                "    on prepaidTariff (pointer);\n" +
                "\n");
        stmt.execute("create table if not exists tariff\n" +
                "(\n" +
                "    id integer not null\n" +
                "        constraint tariff_pk\n" +
                "            primary key autoincrement\n" +
                ");\n" +
                "\n" +
                "create unique index tariff_id_uindex\n" +
                "    on tariff (id);\n" +
                "\n");
    }

}
