package com.example.psi_arb;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.BreakIterator;


public class MainActivity extends AppCompatActivity {

    private static TextView exRate;
    private static TextView pair;

    private static final String TAG = "TAG";
    TextView text;
    Button btn;
    private double bid1 = 0;
    private double ask1 = 0;
    private double x = 0;
    public int path = 0;
    StringBuffer optimalPathText = new StringBuffer();

    static double profitTarget = 1;

    public static BigDecimal cd1b = new BigDecimal(0);
    public static BigDecimal cd1a = new BigDecimal(0);
    public static BigDecimal cd2b = new BigDecimal(0);
    public static BigDecimal cd2a = new BigDecimal(0);
    public static BigDecimal cd3b = new BigDecimal(0);
    public static BigDecimal cd3a = new BigDecimal(0);
    double triArbitrage[] = new double[10000000];


    //Poloniex

    ExchangeRateStorage ETHUSD_poloniex = new ExchangeRateStorage();
    ExchangeRateStorage ETHBTC_poloniex = new ExchangeRateStorage();
    ExchangeRateStorage BTCUSD_poloniex = new ExchangeRateStorage();
    ExchangeRateStorage XRPUSD_poloniex = new ExchangeRateStorage();
    ExchangeRateStorage XMRUSD_poloniex = new ExchangeRateStorage();
    ExchangeRateStorage XMRBTC_poloniex = new ExchangeRateStorage();
    ExchangeRateStorage XRPBTC_poloniex = new ExchangeRateStorage();


    //Bitfinex


    ExchangeRateStorage BTCUSD_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage ETHBTC_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage XRPBTC_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage ETHUSD_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage XMRBTC_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage XMRUSD_bitfinex = new ExchangeRateStorage();


    // ================================================  TOTAL PAIRS   45


    static String c1;
    static String c2;
    static String c3;
    static int z = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make an API connection for crypto data:
        // 940baf66-211a-45fc-b813-d70c2f5e78bd

        exRate = (TextView) findViewById(R.id.rate);
        pair = (TextView) findViewById(R.id.pair);
        btn = (Button) findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONparseTask jsonParse = new JSONparseTask(MainActivity.this);
                jsonParse.execute();

            }
        });


    }


    private class JSONparseTask extends AsyncTask<String, Void, PairCache> {

        Context context;

        public JSONparseTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(PairCache pairCache) {
            super.onPostExecute(pairCache);

            BigDecimal bigD = new BigDecimal(0);

            bigD = getMyBigDeci(pairCache);

            ETHBTC_bitfinex.setBid(bigD);
            System.out.println(ETHBTC_bitfinex.getBid());

            exRate.setText(bigD.toString());
            pair.setText(pairCache.getPair());

        }

        private BigDecimal getMyBigDeci(PairCache pairCache) {

            BigDecimal b;

            String k = pairCache.getBid();
            Double.parseDouble(k);
            b = new BigDecimal(k, MathContext.DECIMAL64);

            return b;
        }

        @Override
        protected PairCache doInBackground(String... strings) {

            PairCache cryptoStuff = new PairCache();
            String data = ((new HTTPCrpytoClient()).getData());
            System.out.println("****SUCCESS *****" + data);

            String myPair = "BTC-ETH";


            try {
                cryptoStuff = JSONcryptoParser.getExchangeRates(data, myPair);

             } catch (Throwable t) {
                t.printStackTrace();
            }

            return cryptoStuff;
        }
    }

}