package com.company.classes;


import java.math.BigDecimal;

public class Contract extends DBConnect {
    BigDecimal msisdn;
    int tariffId;
    int callCount;
    int smsCount;
    int mmsCount;
    int transferMbsCount;

    // when add contract get contract amount check and increment for both

    public Contract(BigDecimal msisdn, int tariffId) {
        this.msisdn = msisdn;
        this.tariffId = tariffId;
        this.callCount = 0;
        this.smsCount = 0;
        this.mmsCount = 0;
        this.transferMbsCount = 0;
    }

    public void uploadContract(int accountId) {
        conn();
        try {
            stmt.execute(
                    "INSERT INTO contract(msisdn, idAccount, idTariff, callCount, smsCount, mmsCount, transferMbsCount) VALUES (" + this.msisdn + ", " + accountId + ", " + this.tariffId + ", " + this.smsCount + ", " + this.mmsCount + ", " + this.callCount + ", " + this.transferMbsCount + ");"
            );
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

}
