package com.example.currencyapp;

import android.util.Log;

public class MyCurrency {

    private String country;
    private double rate;

    public MyCurrency(String country, double rate) {
        this.country = country;
        this.rate = rate;
    }

    public MyCurrency() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String toString(){
        return country;
    }

    public static Double convertCurrency(MyCurrency myCurrencyFrom, MyCurrency myCurrencyTo, Double amount){
        Double output = new Double(0);
        if (!amount.equals(0) || amount.isNaN()) {
            if (myCurrencyFrom.getCountry().contains("DKK")) {
                output = amount / myCurrencyTo.getRate();
            } else {
                Double amountInDkk = myCurrencyFrom.getRate() * amount;
                output = amountInDkk / myCurrencyTo.getRate();
            }
        }
        return output;
    }
}
