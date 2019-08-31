package com.example.psi_arb;

//
//*****************************************************************
// This class is responsible for normalizing currency pair data.
// Cryptocurrency Exchanges customize their own acronyms of
// specific coins and in order to calculate arbitrage from all
// pairs, we need these acronyms to match. This class will do such
// operations.
//*****************************************************************

public class NormalizePairs {

    private static String pair;
    private static String[] binancePair = new String[1000];

    public static String getPair() {
        return pair;
    }

    public static void setPair(String pair) {
        NormalizePairs.pair = pair;
    }

    private static String base;
    private static String quote;

    public NormalizePairs() {
    }

    public static String getBase() {
        return base;
    }

    public static void setBase(String base) {
        NormalizePairs.base = base;
    }

    public static String getQuote() {
        return quote;
    }

    public static void setQuote(String quote) {
        NormalizePairs.quote = quote;
    }


}
