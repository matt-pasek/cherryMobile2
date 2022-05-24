package com.company.classes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Account {
    ArrayList<Contract> contracts = new ArrayList<>();
    int billingDate;

    public Account() {
        LocalDate date = java.time.LocalDate.now();
        int day = date.getDayOfMonth();
        int[] billingDays = {1, 4, 7, 11, 14, 17, 21, 24, 27};
        boolean isSet = false;
        for(int i : billingDays){
            if(date.getDayOfMonth()<=i){
                this.billingDate = date.getDayOfMonth();
                isSet = true;
                break;
            }
        }
        if(!isSet){
            if(date.getMonthValue()==2){
                this.billingDate = 1;
            } else {
                this.billingDate = date.lengthOfMonth();
            }
        }
    }
}
