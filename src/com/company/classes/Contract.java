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
        int idClient = -1;
        try {
            rs = stmt.executeQuery(
                    "SELECT idClient FROM account WHERE id=" + accountId + ";"
            );
            while(rs.next()) {
                idClient = rs.getInt("idClient");
            }
            if(idClient == -1) {
                System.out.println("Database error!");
            } else {
                int contractCount = -1;
                rs = stmt.executeQuery(
                        "SELECT contractCount FROM client WHERE id=" + idClient + ";"
                );
                while(rs.next()) {
                    contractCount = rs.getInt("contractCount");
                }
                if(contractCount == -1) {
                    System.out.println("Database error!");
                } else {
                    contractCount++;
                    stmt.execute(
                            "UPDATE client SET contractCount =" + contractCount + " WHERE id=" + idClient + ";"
                    );
                }
            }
            stmt.execute(
                    "INSERT INTO contract(msisdn, idAccount, idTariff, callCount, smsCount, mmsCount, transferMbsCount) VALUES (" + this.msisdn + ", " + accountId + ", " + this.tariffId + ", " + this.smsCount + ", " + this.mmsCount + ", " + this.callCount + ", " + this.transferMbsCount + ");"
            );
            disconn();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

}
