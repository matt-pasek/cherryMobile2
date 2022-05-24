package com.company.classes.tariffs;

import java.math.BigDecimal;

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
}
