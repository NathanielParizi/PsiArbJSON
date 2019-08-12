package com.example.psi_arb;

import java.math.BigDecimal;

public class ExchangeRateStorage {

    private static BigDecimal bid;
    private static BigDecimal ask;

    public ExchangeRateStorage() {
    }

    public BigDecimal getBid() {
        return this.bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return this.ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }


}
