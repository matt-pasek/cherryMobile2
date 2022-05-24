package com.company.classes;

import com.company.classes.tariffs.Tariff;

public class Contract {
    int msisdn;

    int tariffId;
    int callCount;
    int smsCount;
    int mmsCount;
    int transferMbsCount;

    // when add contract get contract amount check and increment for both

    public Contract(int msisdn, int tariffId) {
        this.msisdn = msisdn;
        this.tariffId = tariffId;
        this.callCount = 0;
        this.smsCount = 0;
        this.mmsCount = 0;
        this.transferMbsCount = 0;
    }

    public void contractUpload() {

    }

}
