package com.example.psi_arb;

import android.content.Context;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v4.content.res.TypedArrayUtils;
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

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.android.volley.Request.*;
import static com.example.psi_arb.NormalizePairs.normalize;

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
//
//  Multipoint arbitrage expert system developed by Nathaniel-Joel Parizi.
//  This algorithm will demonstrate automated order execution accross crypto-
//  exchanges in order to capture arbitrage profit between digital assets.
//


//*************************************************8 Reminders *************
// Binance has no price data for TRIG coin!!! Don't divide by 0 on that!


public class MainActivity extends AppCompatActivity {

    private static TextView exRate;
    private static TextView pair;

    private static final String TAG = "";
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

    private static String tempPair;

    //***************************************************************
    //===========Instance variables of currency pairs================
    // Each object stores a bid and ask price of type BigDecimal.
    // Naming convention is important here, the triangular arbitrage
    // module matches base currency and quote currency in order to
    // calculate the arbitrage.
    //***************************************************************


    static String c1;
    static String c2;
    static String c3;
    static int z = 0;
    private static int pairCounter = 0;
    private static ArrayList<String> cPair = new ArrayList<>();

    //**************** Instruments with BID and ASK prices

    private static String[] bitfinexPair = new String[1000];
    private static BigDecimal[] bitfinexxBid = new BigDecimal[1000];
    private static BigDecimal[] bitfinexAsk = new BigDecimal[1000];
    private static BigDecimal[] bitfinexVolume = new BigDecimal[1000];

    private static String[] bittrexPair = new String[1000];
    private static BigDecimal[] bittrexBid = new BigDecimal[1000];
    private static BigDecimal[] bittrexAsk = new BigDecimal[1000];
    private static BigDecimal[] bittrexVolume = new BigDecimal[1000];

    private static String[] binancePair = new String[1000];
    private static BigDecimal[] binanceBid = new BigDecimal[1000];
    private static BigDecimal[] binanceAsk = new BigDecimal[1000];
    private static BigDecimal[] binanceVolume = new BigDecimal[1000];

    private static String[] okexPair = new String[1000];
    private static BigDecimal[] okexBid = new BigDecimal[1000];
    private static BigDecimal[] okexAsk = new BigDecimal[1000];
    private static BigDecimal[] okexVolume = new BigDecimal[1000];

    private static String[] krakenPair = new String[1000];
    private static BigDecimal[] krakenBid = new BigDecimal[1000];
    private static BigDecimal[] krakenAsk = new BigDecimal[1000];
    private static BigDecimal[] krakenVolume = new BigDecimal[1000];

    private static String[] poloniexPair = new String[1000];
    private static BigDecimal[] poloniexBid = new BigDecimal[1000];
    private static BigDecimal[] poloniexAsk = new BigDecimal[1000];
    private static BigDecimal[] poloniexVolume = new BigDecimal[1000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make an API connection for crypto data:
        //

        exRate = (TextView) findViewById(R.id.rate);
        pair = (TextView) findViewById(R.id.pair);
        btn = (Button) findViewById(R.id.btn);

        cPair.add(" ");
        pair.setText(cPair.get(0));


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getCryptoPair();


            }
        });

    }

    private void getCryptoPair() {

        RequestQueue queue = Volley.newRequestQueue(this);

        //Bitfinex ************************
        String URL = "https://api-pub.bitfinex.com/v2/tickers?symbols=ALL";
        JsonArrayRequest jsonArr_bitfinex = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {

                Log.d("Response: ", response.toString());


                bitfinexPair = new String[417];
                bitfinexAsk = new BigDecimal[417];
                bitfinexxBid = new BigDecimal[417];


                Log.d("COOLSPOT3", String.valueOf(response.length()));

                for (int i = 0; i < 417; i++) {

                    try {

                        JSONArray innerArr = response.getJSONArray(i);


                        bitfinexxBid[i] = BigDecimal.valueOf(Double.parseDouble(innerArr.getString(1)));
                        bitfinexAsk[i] = BigDecimal.valueOf(Double.parseDouble(innerArr.getString(3)));
                        bitfinexVolume[i] = BigDecimal.valueOf(Double.parseDouble(innerArr.getString(8)));
                        bitfinexPair[i] = innerArr.getString(0).substring(0);

                        //Normalize data
                        String base = bitfinexPair[i].substring(1, 4);
                        String quote = bitfinexPair[i].substring(4, 7);
                        bitfinexPair[i] = base + "_" + quote;


                        Log.d("BITFINEX", bitfinexPair.length + "i:" + i + " " + (bitfinexPair[i]) + " " + bitfinexxBid[i].toString() + " " + bitfinexAsk[i].toString() + " " + bitfinexVolume[i].toString());

                        //    Log.d("Items: ", jArr.getString(i));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR at " + tempPair + " " + error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        queue.add(jsonArr_bitfinex);

        URL = "https://api.bittrex.com/api/v1.1/public/getmarketsummaries";
        JsonObjectRequest bittrexObject = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {


                    JSONArray metadataArr = response.getJSONArray("result");

                    bittrexPair = new String[metadataArr.length()];
                    bittrexAsk = new BigDecimal[metadataArr.length()];
                    bittrexBid = new BigDecimal[metadataArr.length()];

                    for (int i = 0; i < metadataArr.length(); i++) {


                        JSONObject obj = metadataArr.getJSONObject(i);

                        bittrexBid[i] = BigDecimal.valueOf(Double.parseDouble(obj.getString("Bid")));
                        bittrexAsk[i] = BigDecimal.valueOf(Double.parseDouble(obj.getString("Ask")));
                        bittrexVolume[i] = BigDecimal.valueOf(Double.parseDouble(obj.getString("BaseVolume")));
                        bittrexPair[i] = obj.getString("MarketName");

                        //Normalize data
                        bittrexPair[i] = bittrexPair[i].replace("-", "_");

                        Log.d("BittrexSet", bittrexPair.length + " " + bittrexPair[i] + bittrexVolume[i].toString());


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("HELLO Object", response.toString());
                //   JSONObject metadata = response.getJSONObject("result");
                //    Log.d("HELLO Metadata" , metadata.getString("result"));
                //              JSONArray array = response.getJSONArray(result);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(bittrexObject);

        URL = "https://api.binance.com/api/v3/ticker/bookTicker";

        final JsonArrayRequest binanceFeed = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                binancePair = new String[response.length()];
                binanceAsk = new BigDecimal[response.length()];
                binanceBid = new BigDecimal[response.length()];


                try {

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        binancePair[i] = obj.getString("symbol");
                        binanceAsk[i] = BigDecimal.valueOf(Double.parseDouble(obj.getString("askPrice")));
                        binanceBid[i] = BigDecimal.valueOf(Double.parseDouble(obj.getString("bidPrice")));
                        binanceVolume[i] = BigDecimal.valueOf(Double.parseDouble(obj.getString("bidQty")));

                        NormalizePairs normalizer = new NormalizePairs();


                        //Normalize data
                        String str = binancePair[i];
                        normalize(str, i);


                        if (binancePair[i].length() < 6) {
                            //Normalize data
                            String base = binancePair[i].substring(0, 2);
                            String quote = binancePair[i].substring(2, 5);
                            binancePair[i] = base + "_" + quote;

                            Log.d("Binance4Pair", binancePair[i]);
                        }

                        if (binancePair[i].length() == 6) {
                            //Normalize data
                            String base = binancePair[i].substring(0, 3);
                            String quote = binancePair[i].substring(3, 6);
                            binancePair[i] = base + "_" + quote;

                            //                                       Log.d("Binance4Pair", binancePair[i]);

                        }


                        Log.d("BinanceSet", binancePair.length + " " + binancePair[i] + " " + binanceAsk[i].toString() + " " +
                                binanceBid[i].toString() + " " + binanceVolume[i].toString());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(binanceFeed);


        URL = "https://www.okex.com/api/spot/v3/instruments/ticker";

        JsonArrayRequest okexFeed = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject arrReq = null;
                try {

                    okexPair = new String[response.length()];
                    okexAsk = new BigDecimal[response.length()];
                    okexBid = new BigDecimal[response.length()];

                    for (int i = 0; i < response.length(); i++) {

                        arrReq = response.getJSONObject(i);

                        okexPair[i] = arrReq.getString("instrument_id");
                        okexBid[i] = BigDecimal.valueOf(Double.parseDouble(arrReq.getString("bid")));
                        okexAsk[i] = BigDecimal.valueOf(Double.parseDouble(arrReq.getString("ask")));
                        okexVolume[i] = BigDecimal.valueOf(Double.parseDouble(arrReq.getString("base_volume_24h")));

                        //Normalize data
                        okexPair[i] = okexPair[i].replace("-", "_");

                        Log.d("OKEX", okexPair.length
                                + " " + okexPair[i].toString() + " " + okexBid[i].toString() + " "
                                + okexAsk[i].toString() + " " + okexVolume[i].toString());


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(okexFeed);

        URL = "https://api.kraken.com/0/public/Ticker?pair=BCHEUR,BCHUSD,BCHXBT,DASHEUR,DASHUSD,DASHXBT,EOSETH,EOSXBT,GNOETH,GNOXBT,USDTZUSD,XETCXETH,XETCXXBT,XETCZEUR,XETCZUSD,XETHXXBT,XETHZGBP,XETHZJPY,XETHZUSD,XICNXETH,XICNXXBT,XLTCXXBT,XLTCZEUR,XLTCZUSD,XMLNXETH,XMLNXXBT,XREPXETH,XREPXXBT,XREPZEUR,XXBTZCAD,XXBTZEUR,XXBTZGBP,XXBTZJPY,XXBTZUSD,XXDGXXBT,XXLMXXBT,XXMRXXBT,XXMRZEUR,XXMRZUSD,XXRPXXBT,XXRPZEUR,XXRPZUSD,XZECXXBT,XZECZEUR,XZECZUSD";

        JsonObjectRequest krakenFeed = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {


                    int krakenCountprice = 0;
                    int krakenCountPair = 0;
                    JSONObject obj = response.getJSONObject("result");

                    Iterator keyPair = obj.keys();

                    int countK = obj.length();

                    krakenPair = new String[countK];
                    krakenAsk = new BigDecimal[countK];
                    krakenBid = new BigDecimal[countK];

                    while (keyPair.hasNext()) {


                        krakenPair[krakenCountPair] = (String) keyPair.next();
                        krakenCountPair++;

                    }

                    //Grab pairs**********************************************************
                    for (Iterator key = obj.keys(); key.hasNext(); ) {


                        JSONObject innerObj = (JSONObject) obj.get((String) key.next());

                        JSONArray askArr = innerObj.getJSONArray("a");
                        JSONArray bidArr = innerObj.getJSONArray("b");
                        String kask = askArr.getString(0);
                        String kbid = bidArr.getString(0);

                        krakenAsk[krakenCountprice] = BigDecimal.valueOf(Double.parseDouble(kask));
                        krakenBid[krakenCountprice] = BigDecimal.valueOf(Double.parseDouble(kbid));


                        //Normalize data
//

                        if (krakenPair[krakenCountprice].charAt(0) == 'X' && krakenPair[krakenCountprice].charAt(4) == ('X')) {
                            String base = krakenPair[krakenCountprice].substring(1, 4);
                            String quote = krakenPair[krakenCountprice].substring(5, 8);
                            krakenPair[krakenCountprice] = base + "_" + quote;
                        } else if (krakenPair[krakenCountprice].charAt(0) == 'X' && krakenPair[krakenCountprice].charAt(4) == ('Y')) {
                            String base = krakenPair[krakenCountprice].substring(1, 4);
                            String quote = krakenPair[krakenCountprice].substring(5, 8);
                            krakenPair[krakenCountprice] = base + "_" + quote;
                        } else if (krakenPair[krakenCountprice].length() == 6) {
                            String base = krakenPair[krakenCountprice].substring(0, 3);
                            String quote = krakenPair[krakenCountprice].substring(3, 6);
                            krakenPair[krakenCountprice] = base + "_" + quote;
                        } else if (krakenPair[krakenCountprice].contains("DASH")) {

                            int range = krakenPair[krakenCountprice].length();
                            String base = krakenPair[krakenCountprice].substring(0, 4);
                            String quote = krakenPair[krakenCountprice].substring(4, range);
                            krakenPair[krakenCountprice] = base + "_" + quote;
                        } else if (krakenPair[krakenCountprice].length() > 6 && krakenPair[krakenCountprice].charAt(0) == 'X') {

                            int range = krakenPair[krakenCountprice].length();
                            String base = krakenPair[krakenCountprice].substring(1, 4);
                            String quote = krakenPair[krakenCountprice].substring(4, range);
                            Log.d("LOLWTF", range + " " + base + " " + quote);
                            krakenPair[krakenCountprice] = base + "_" + quote;
                        } else if (krakenPair[krakenCountprice].equals("USDTZUSD")) {
                            krakenPair[krakenCountprice] = "USDT_USD";
                        }


                        krakenPair[krakenCountprice] = krakenPair[krakenCountprice].replace("XBT", "BTC");
                        krakenPair[krakenCountprice] = krakenPair[krakenCountprice].replace("ZUSD", "USD");
                        krakenPair[krakenCountprice] = krakenPair[krakenCountprice].replace("ZEUR", "EUR");
                        krakenPair[krakenCountprice] = krakenPair[krakenCountprice].replace("ZJPY", "JPY");
                        krakenPair[krakenCountprice] = krakenPair[krakenCountprice].replace("ZCAD", "CAD");
                        krakenPair[krakenCountprice] = krakenPair[krakenCountprice].replace("ZGBP", "GBP");


                        Log.d("KRAKEN set", krakenPair.length + " " + krakenPair[krakenCountprice] + " " + krakenAsk[krakenCountprice].toString() + " " + krakenBid[krakenCountprice].toString());
                        krakenCountprice++;


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(krakenFeed);

        URL = "https://poloniex.com/public?command=returnTicker";

        JsonObjectRequest poloniexFeed = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Iterator keys = response.keys();
                int poloniexCounter = 0;

                poloniexPair = new String[response.length()];
                poloniexAsk = new BigDecimal[response.length()];
                poloniexBid = new BigDecimal[response.length()];

                while (keys.hasNext()) {

                    String here = (String) keys.next();
                    poloniexPair[poloniexCounter] = here;


                    try {
                        JSONObject obj = response.getJSONObject(here);
                        poloniexAsk[poloniexCounter] = BigDecimal.valueOf(Double.parseDouble(obj.getString("lowestAsk")));
                        poloniexBid[poloniexCounter] = BigDecimal.valueOf(Double.parseDouble(obj.getString("highestBid")));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("POLONIEX_set", poloniexPair.length + " " + poloniexPair[poloniexCounter] + " " + poloniexAsk[poloniexCounter].toString() + " " + poloniexBid[poloniexCounter].toString());

                    poloniexCounter++;
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(poloniexFeed);


        //******************************************************************** JSON Parsing Complete


        String[] pairTokens = {};
        BigDecimal[] bidTokens = {};
        BigDecimal[] askTokens = {};


        pairTokens = concatenate(pairTokens, bitfinexPair);
        pairTokens = concatenate(pairTokens, bittrexPair);
        pairTokens = concatenate(pairTokens, binancePair);
        pairTokens = concatenate(pairTokens, okexPair);
        pairTokens = concatenate(pairTokens, krakenPair);
        pairTokens = concatenate(pairTokens, poloniexPair);

        bidTokens = concatenate(bidTokens, bitfinexxBid);
        bidTokens = concatenate(bidTokens, bittrexBid);
        bidTokens = concatenate(bidTokens, binanceBid);
        bidTokens = concatenate(bidTokens, okexBid);
        bidTokens = concatenate(bidTokens, krakenBid);
        bidTokens = concatenate(bidTokens, poloniexBid);

        askTokens = concatenate(askTokens, bitfinexAsk);
        askTokens = concatenate(askTokens, bittrexAsk);
        askTokens = concatenate(askTokens, binanceAsk);
        askTokens = concatenate(askTokens, okexAsk);
        askTokens = concatenate(askTokens, krakenAsk);
        askTokens = concatenate(askTokens, poloniexAsk);


        if ((!Arrays.asList(pairTokens).subList(0, pairTokens.length).contains(null))
                && (!Arrays.asList(askTokens).subList(0, askTokens.length).contains(null))
                && (!Arrays.asList(bidTokens).subList(0, bidTokens.length).contains(null))
                && (bitfinexPair.length < 1000 && bittrexPair.length < 1000 && binancePair.length < 1000 && okexPair.length < 1000
                && krakenPair.length < 1000 && poloniexPair.length < 1000)) {

            String[] exchange = new String[pairTokens.length];

        //************************************************************  [BASE]-[QUOTE]  TOKENS
            String[] baseTokens = {};
            String[] quoteTokens = {};

            for(int i =0; i< pairTokens.length; i++){

                if(pairTokens[i].indexOf("_") == -1){
                    baseTokens[i] = "EMPTY";
                    quoteTokens[i] = "EMPTY";
                }
                int indexOf_ = pairTokens[i].indexOf("_");
                baseTokens[i] = pairTokens[i].substring(0,indexOf_);
                quoteTokens[i] = pairTokens[i].substring(indexOf_+1,pairTokens[i].length());

            }
            //******************************************************


            for (int i = 0; i < pairTokens.length; i++) {
//                Log.d("GOLDRow", pairTokens[i] + " " + String.valueOf(askTokens[i]) + " " + String.valueOf(bidTokens[i]) + " i:" + i);
                Log.d("GOLDRow", pairTokens[i] + " " + " [" + baseTokens[i] + "] [" + quoteTokens[i] + "] \t " + String.valueOf(askTokens[i]) + " " + String.valueOf(bidTokens[i]) + " i:" + i);
            }
            Log.d("pairTokens", String.valueOf(pairTokens.length) + " " + binancePair.length + " " + bitfinexPair.length);
        }
    }


    public <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    private BigDecimal getMyBigDeci(String str) {

        BigDecimal b;
        Double.parseDouble(str);
        b = new BigDecimal(str, MathContext.DECIMAL64);

        return b;
    }

}