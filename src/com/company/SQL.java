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
        stmt.execute("""
                create table if not exists businessClient
                (
                pointer     integer not null constraint businessClient_pk primary key,
                nip         text,
                regon       text,
                companyName text,
                FOREIGN KEY (pointer) REFERENCES client(id)
                );

                create unique index businessClient_pointer_uindex
                    on businessClient (pointer);

                """
        );

        stmt.execute("""
                create table if not exists oldBusinessClient
                (
                id          integer not null constraint oldBusinessClient_pk primary key,
                email       text,
                nip         text,
                regon       text,
                companyName text
                );

                create unique index businessClient_pointer_uindex
                    on businessClient (pointer);

                """
        );

        stmt.execute("""
                create table if not exists client
                (
                id    integer not null constraint client_pk primary key autoincrement,
                email text,
                contractCount integer
                );

                create unique index client_id_uindex
                    on client (id);

                """
        );

        stmt.execute("""
                create table if not exists individualClient
                (
                pointer integer not null constraint individualClient_pk primary key,
                fName   text,
                lName   text,
                pesel   text,
                FOREIGN KEY (pointer) REFERENCES client(id)
                
                );

                create unique index individualClient_pointer_uindex
                    on individualClient (pointer);

                """
        );

        stmt.execute("""
                create table if not exists oldIndividualClient
                (
                id      integer not null constraint individualClient_pk primary key,
                email   text,
                fName   text,
                lName   text,
                pesel   text
                );

                create unique index individualClient_pointer_uindex
                    on individualClient (pointer);

                """
        );

        stmt.execute("""
                create table if not exists account
                (
                    id          integer not null
                        constraint account_pk
                            primary key autoincrement,
                    accountName text,
                    idClient    integer not null,
                    billingDate int,
                    FOREIGN KEY (idClient) REFERENCES client(id)
                );

                create unique index account_id_uindex
                    on account (id);

                """);
        stmt.execute("""
                create table if not exists contract
                (
                    msisdn           integer not null
                        constraint account_pk
                            primary key,
                    idAccount        integer not null,
                    idTariff         integer not null,
                    callCount        integer,
                    smsCount         integer,
                    mmsCount         integer,
                    transferMbsCount integer,
                    FOREIGN KEY (idAccount) REFERENCES account(id),
                    FOREIGN KEY (idTariff) REFERENCES tariff(id)
                );

                create unique index contract_msisdn_uindex
                    on contract (msisdn);

                """);
        stmt.execute("""
                create table if not exists postpaidTariff
                (
                    pointer          integer not null
                        constraint postpaidTariff_pk
                            primary key,
                    smsPrice         real,
                    mmsPrice         real,
                    transferMbsPrice real,
                    FOREIGN KEY (pointer) REFERENCES tariff(id)
                );

                create unique index postpaidTariff_pointer_uindex
                    on postpaidTariff (pointer);

                """);
        stmt.execute("""
                create table if not exists prepaidTariff
                (
                    pointer            integer not null
                        constraint prepaidTariff_pk
                            primary key,
                    price              real,
                    callAmount         integer,
                    smsAmount          integer,
                    mmsAmount          integer,
                    transferMbsAmount  integer,
                    extraCallPrice     real,
                    extraSmsPrice      real,
                    extraMmsPrice      real,
                    extraTransferPrice real,
                    FOREIGN KEY (pointer) REFERENCES tariff(id)
                );

                create unique index prepaidTariff_pointer_uindex
                    on prepaidTariff (pointer);

                """);
        stmt.execute("""
                create table if not exists tariff
                (
                    id integer not null
                        constraint tariff_pk
                            primary key autoincrement,\040
                    tariffName text
                );

                create unique index tariff_id_uindex
                    on tariff (id);

                """);
    }

}
