package com.company.classes.tariffs;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PrepaidTariff extends Tariff{
    BigDecimal price;
    int callAmount;
    int smsAmount;
    int mmsAmount;
    int transferMbsAmount;
    BigDecimal extraCallPrice;
    BigDecimal extraSmsPrice;
    BigDecimal extraMmsPrice;
    BigDecimal extraTransferMbsPrice;

    public PrepaidTariff(String tariffName, BigDecimal price, int callAmount, int smsAmount, int mmsAmount, int transferMbsAmount, BigDecimal extraCallPrice, BigDecimal extraSmsPrice, BigDecimal extraMmsPrice, BigDecimal extraTransferMbsPrice) {
        this.tariffName = tariffName;
        this.price = price;
        this.callAmount = callAmount;
        this.smsAmount = smsAmount;
        this.mmsAmount = mmsAmount;
        this.transferMbsAmount = transferMbsAmount;
        this.extraCallPrice = extraCallPrice;
        this.extraSmsPrice = extraSmsPrice;
        this.extraMmsPrice = extraMmsPrice;
        this.extraTransferMbsPrice = extraTransferMbsPrice;
    }

    public void createTariff() {
        int id = -1;
        int tariffs = -1;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT COUNT(tariffName) as tariffs FROM tariff WHERE tarrifName = '" + this.tariffName + "';"
            );
            while(rs.next()) {
                tariffs = rs.getInt("tariffs");
            }

            if (tariffs == -1) {
                stmt.execute(
                        "INSERT INTO tariff(tariffName) VALUES ('"+ this.tariffName + "');"
                );
                rs = stmt.executeQuery(
                        "SELECT id FROM tariff WHERE tarrifName = '" + this.tariffName + "';"
                );
                while(rs.next()) {
                    id = rs.getInt("id");
                }
                stmt.execute(
                        "INSERT INTO prepaidTariff(pointer, price, callAmount, smsAmount, mmsAmount, transferMbsAmount, extraCallPrice, extraSmsPrice, extraMmsPrice, extraTransferPrice) VALUES (" + id + "," + this.price +"," + this.callAmount + ", " + this.smsAmount + "," + this.mmsAmount + "," + this.transferMbsAmount +"," + this.extraCallPrice +"," + this.extraSmsPrice +"," + this.extraMmsPrice +"," + this.extraTransferMbsPrice +");"
                );
            } else {
                System.out.println("Tariff with this name already exists.");
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getCallAmount() {
        return callAmount;
    }

    public int getSmsAmount() {
        return smsAmount;
    }

    public int getMmsAmount() {
        return mmsAmount;
    }

    public int getTransferMbsAmount() {
        return transferMbsAmount;
    }

    public BigDecimal getExtraCallPrice() {
        return extraCallPrice;
    }

    public BigDecimal getExtraSmsPrice() {
        return extraSmsPrice;
    }

    public BigDecimal getExtraMmsPrice() {
        return extraMmsPrice;
    }

    public BigDecimal getExtraTransferMbsPrice() {
        return extraTransferMbsPrice;
    }
}
