package com.example.psi_arb;

import java.math.BigDecimal;

public class ExchangeRateStorage {

    private static BigDecimal bid;

    public ExchangeRateStorage() {
    }

    public static BigDecimal getBid() {
        return bid;
    }

    public static void setBid(BigDecimal bid) {
        ExchangeRateStorage.bid = bid;
    }

    public static BigDecimal getAsk() {
        return ask;
    }

    public static void setAsk(BigDecimal ask) {
        ExchangeRateStorage.ask = ask;
    }

    private static BigDecimal ask;
}
