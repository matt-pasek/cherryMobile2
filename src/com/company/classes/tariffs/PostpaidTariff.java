package com.company.classes.tariffs;

import java.math.BigDecimal;

public class PostpaidTariff extends Tariff{
    BigDecimal price;
    int callAmount;
    int smsAmount;
    int mmsAmount;
    int transferMbsAmount;
    BigDecimal extraCallPrice;
    BigDecimal extraSmsPrice;
    BigDecimal extraMmsPrice;
    BigDecimal extraTransferMbsPrice;

    public PostpaidTariff(BigDecimal price, int callAmount, int smsAmount, int mmsAmount, int transferMbsAmount, BigDecimal extraCallPrice, BigDecimal extraSmsPrice, BigDecimal extraMmsPrice, BigDecimal extraTransferMbsPrice) {
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

    public void uploadTariff() {
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
                        "INSERT INTO postpaidTariff(pointer, price, callAmount, smsAmount, mmsAmount, transferMbsAmount, extraCallPrice, extraSmsPrice, extraMmsPrice, extraTransferPrice) VALUES (" + id + "," + this.price +"," + this.callAmount + ", " + this.smsAmount + "," + this.mmsAmount + "," + this.transferMbsAmount +"," + this.extraCallPrice +"," + this.extraSmsPrice +"," + this.extraMmsPrice +"," + this.extraTransferMbsPrice +");"
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
