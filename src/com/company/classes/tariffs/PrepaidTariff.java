package com.company.classes.tariffs;

import java.math.BigDecimal;

public class PrepaidTariff extends Tariff{
    BigDecimal callPrice;
    BigDecimal smsPrice;
    BigDecimal mmsPrice;
    BigDecimal transferMbsPrice;

    public PrepaidTariff(String tariffName, BigDecimal callPrice, BigDecimal smsPrice, BigDecimal mmsPrice, BigDecimal transferMbsPrice) {
        this.tariffName = tariffName;
        this.callPrice = callPrice;
        this.smsPrice = smsPrice;
        this.mmsPrice = mmsPrice;
        this.transferMbsPrice = transferMbsPrice;
    }

    public void createTariff() {
        conn();
        int id = -1;
        int tariffs = -1;
        try {
            rs = stmt.executeQuery(
                    "SELECT COUNT(tariffName) as tariffs FROM tariff WHERE tariffName = '" + this.tariffName + "';"
            );
            while(rs.next()) {
                tariffs = rs.getInt("tariffs");
            }

            if (tariffs == -1) {
                stmt.execute(
                        "INSERT INTO tariff(tariffName) VALUES ('"+ this.tariffName + "');"
                );
                rs = stmt.executeQuery(
                        "SELECT id FROM tariff WHERE tariffName = '" + this.tariffName + "';"
                );
                while(rs.next()) {
                    id = rs.getInt("id");
                }
                stmt.execute(
                        "INSERT INTO prepaidTariff(pointer, smsPrice, mmsPrice, transferMbsPrice) VALUES (" + id + "," + this.smsPrice +"," + this.mmsPrice + ", " + this.transferMbsPrice + ");"
                );
            } else {
                System.out.println("Tariff with this name already exists.");
            }
            disconn();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public BigDecimal getCallPrice() {
        return callPrice;
    }

    public BigDecimal getSmsPrice() {
        return smsPrice;
    }

    public BigDecimal getMmsPrice() {
        return mmsPrice;
    }

    public BigDecimal getTransferMbsPrice() {
        return transferMbsPrice;
    }
}
