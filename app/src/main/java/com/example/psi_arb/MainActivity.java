package com.example.psi_arb;

import android.content.Context;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.BreakIterator;
import java.util.ArrayList;

import static com.android.volley.Request.*;

//*******************************************************************************************
//
//        ooooooooo.    .oooooo..o ooooo               .o.       ooooooooo.   oooooooooo.
//       `888   `Y88. d8P'    `Y8 `888'              .888.      `888   `Y88. `888'   `Y8b
//        888   .d88' Y88bo.       888              .8"888.      888   .d88'  888     888
//        888ooo88P'   `"Y8888o.   888             .8' `888.     888ooo88P'   888oooo888'
//        888              `"Y88b  888  8888888   .88ooo8888.    888`88b.     888    `88b
//        888         oo     .d8P  888           .8'     `888.   888  `88b.   888    .88P
//        o888o        8""88888P'  o888o         o88o     o8888o o888o  o888o o888bood8P'
//
//  Multipoint arbitrage expert system developed by Nathaniel-Joel Parizi.
//  This algorithm will demonstrate automated order execution accross cryptocurrency
//  exchanges in order to capture arbitrage profit between cryptocurrencies.
//
//********************************************************************************************
//
//      The arsenal:
//
// ETHUSD // ETHEUR //ETHXBT //BTCUSD  //BTCEUR //XMRXBT //XRPUSD //XRPEUR //XRPXBT //BABUSD //BABEUR
//
//********************************************************************************************

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
//
//    ExchangeRateStorage ETHUSD_poloniex = new ExchangeRateStorage();
//    ExchangeRateStorage ETHBTC_poloniex = new ExchangeRateStorage();
//    ExchangeRateStorage BTCUSD_poloniex = new ExchangeRateStorage();
//    ExchangeRateStorage XRPUSD_poloniex = new ExchangeRateStorage();
//    ExchangeRateStorage XMRUSD_poloniex = new ExchangeRateStorage();
//    ExchangeRateStorage XMRBTC_poloniex = new ExchangeRateStorage();
//    ExchangeRateStorage XRPBTC_poloniex = new ExchangeRateStorage();

    //Bitfinex

    ExchangeRateStorage BTCUSD_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage BTCEUR_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage ETHBTC_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage XRPBTC_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage ETHUSD_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage ETHEUR_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage XMRBTC_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage XMRUSD_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage XRPUSD_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage XRPEUR_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage BABUSD_bitfinex = new ExchangeRateStorage();
    ExchangeRateStorage BABEUR_bitfinex = new ExchangeRateStorage();


    // ================================================  TOTAL PAIRS   45


    static String c1;
    static String c2;
    static String c3;
    static int z = 0;
    private static int pairCounter = 0;

    private static ArrayList<String> pairs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Make an API connection for crypto data:
        // 940baf66-211a-45fc-b813-d70c2f5e78bd

        exRate = (TextView) findViewById(R.id.rate);
        pair = (TextView) findViewById(R.id.pair);
        btn = (Button) findViewById(R.id.btn);

        getCryptoPair();
//        getCryptoPair("ETHEUR");
//        getCryptoPair("ETHBTC");
//        getCryptoPair("BTCUSD");
//        getCryptoPair("BTCEUR");
//        getCryptoPair("XMRBTC");
//        getCryptoPair("XRPUSD");
//        getCryptoPair("XRPEUR");
//        getCryptoPair("XRPBTC");
//        getCryptoPair("BABUSD");
//        getCryptoPair("BABEUR");


        ETHBTC_bitfinex.setBid(BigDecimal.valueOf(1.01));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println(ETHUSD_bitfinex.getBid() + "\n" + ETHEUR_bitfinex.getBid() + "\n" + ETHBTC_bitfinex.getBid() +
                        "\n" + BTCUSD_bitfinex.getBid() + "\n" + BTCEUR_bitfinex.getBid() + "\n" + XMRBTC_bitfinex.getBid() + "\n" + XRPUSD_bitfinex.getBid() + "\n" +
                        XRPEUR_bitfinex.getBid() + "\n" + XRPBTC_bitfinex.getBid() + "\n" + BABUSD_bitfinex.getBid() + "\n" + BABEUR_bitfinex.getBid());
                String comeON = (ETHUSD_bitfinex.getBid() + "\n" + ETHEUR_bitfinex.getBid() + "\n" + ETHBTC_bitfinex.getBid() +
                        "\n" + BTCUSD_bitfinex.getBid() + "\n" + BTCEUR_bitfinex.getBid() + "\n" + XMRBTC_bitfinex.getBid() + "\n" + XRPUSD_bitfinex.getBid() + "\n" +
                        XRPEUR_bitfinex.getBid() + "\n" + XRPBTC_bitfinex.getBid() + "\n" + BABUSD_bitfinex.getBid() + "\n" + BABEUR_bitfinex.getBid()).toString();


                pair.setText(comeON);
            }
        });

    }

    private void getCryptoPair() {

        RequestQueue queue = Volley.newRequestQueue(this);
        final String URL = "https://api.bitfinex.com/v1/pubticker/" + "ETHUSD";
        JsonObjectRequest arrayRequestETHUSD = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    response.getString("bid");   // Test
                    updateBitfinexPair("ETHUSD", response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR at " + "ETHUSD" + " " + error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        queue.add(arrayRequestETHUSD);



    }


    private void updateBitfinexPair(String str, JSONObject response) {

        // This entire method parses the BID/ASK JSON from Bitfinex API

        try {

            Log.d("update method: pair: ", str);

            switch (str) {
                case "ETHUSD":
                    ETHUSD_bitfinex.setBid(getMyBigDeci(response.getString("bid")));
                    ETHUSD_bitfinex.setAsk(getMyBigDeci(response.getString("ask")));
                    Toast.makeText(getApplicationContext(),"WHAT THE HELL",Toast.LENGTH_LONG).show();
//                    Toast.makeText(getApplicationContext(), "BigDeci" + str + " " + " BID:" + ETHUSD_bitfinex.getBid(), Toast.LENGTH_LONG).show();
//                    Toast.makeText(getApplicationContext(), "BigDeci" + str + " " + " ASK:" + ETHUSD_bitfinex.getAsk(), Toast.LENGTH_LONG).show();
                    break;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private BigDecimal getMyBigDeci(String str) {

        BigDecimal b;
        Double.parseDouble(str);
        b = new BigDecimal(str, MathContext.DECIMAL64);

        return b;
    }


/*
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

            BigDecimal bigD;

            // bigD = getMyBigDeci(pairCache);

            Toast.makeText(MainActivity.this, (CharSequence) pairCache, Toast.LENGTH_LONG).show();
            //    ETHBTC_bitfinex.setBid(bigD);
            System.out.println(ETHBTC_bitfinex.getBid());

            // exRate.setText(bigD.toString());
            // pair.setText(pairCache.getPair());

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

    */

}