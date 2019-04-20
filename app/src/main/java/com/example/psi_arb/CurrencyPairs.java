package com.example.psi_arb;

import java.math.BigDecimal;

public class CurrencyPairs {


    public class ExchangeRateStorage {

        private String cPair;
        private BigDecimal bid;
        private BigDecimal ask;

        ExchangeRateStorage() {
        }

        public void setcPair(String cPair) {

            this.cPair = cPair;

        }

        public String getcPair() {

            return cPair;

        }

        public BigDecimal getBid() {

            return bid;

        }

        public BigDecimal getAsk() {

            return ask;

        }

        public void setBid(double bid) {


            this.bid = BigDecimal.valueOf(bid);

        }

        public void setAsk(double ask) {

            this.ask = BigDecimal.valueOf(ask);
            ;

        }


    }
}