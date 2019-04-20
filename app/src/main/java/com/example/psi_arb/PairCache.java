package com.example.psi_arb;

public class PairCache {

    public static String getBid() {
        return bid;
    }

    public static void setBid(String bid) {
        PairCache.bid = bid;
    }

    public static String getAsk() {
        return ask;
    }

    public static void setAsk(String ask) {
        PairCache.ask = ask;
    }

    public static String getPair() {
        return pair;
    }

    public static void setPair(String pair) {
        PairCache.pair = pair;
    }

    private static String pair;
    private static String bid;
    private static String ask;


    public PairCache(){}


}
