package com.example.psi_arb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.psi_arb.NormalizePairs.normalize;
import static com.example.psi_arb.NormalizePairs.normalizeHitBTC;

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
    private static ScrollView scrollView;

    private static final String TAG = "";
    TextView text;
    Button btn;

    private double x = 0;
    public int path = 0;
    String optimalPathText;
    private static double maxBet = 100;

    static double profitTarget = 1;
    static String[] exchange;

    private static int timer = 5000;
    private static int[] chainIndex;
    private static ArrayList<String[]> arbChain = new ArrayList<>();

    public static BigDecimal cd1b = new BigDecimal(0);
    public static BigDecimal cd1a = new BigDecimal(0);
    public static BigDecimal cd2b = new BigDecimal(0);
    public static BigDecimal cd2a = new BigDecimal(0);
    public static BigDecimal cd3b = new BigDecimal(0);
    public static BigDecimal cd3a = new BigDecimal(0);
    Double triArbitrage = 0d;
    //ArrayList<Double> triArbitrage = new ArrayList<>();
    private static String tempPair;

    //***************************************************************
    //===========Instance variables of currency pairs================
    // Each object stores a bid and ask price of type BigDecimal.
    // Naming convention is important here, the triangular arbitrage
    // module matches base currency and quote currency in order to
    // calculate the arbitrage.
    //***************************************************************

    private static StringBuilder strBuilder = new StringBuilder();

    static String c1;
    static String c2;
    static String c3;

    static String c1Base;
    static String c2Base;
    static String c3Base;
    static String c1Quote;
    static String c2Quote;
    static String c3Quote;

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

    static String[] poloniexPair = new String[1000];
    private static BigDecimal[] poloniexBid = new BigDecimal[1000];
    private static BigDecimal[] poloniexAsk = new BigDecimal[1000];

    static String[] bitMartPair = new String[1000];
    private static BigDecimal[] bitmartAsk = new BigDecimal[1000];
    private static BigDecimal[] bitmartBid = new BigDecimal[1000];

    static String[] hitBTCPair = new String[1000];
    private static BigDecimal[] hitBTCBid = new BigDecimal[1000];
    private static BigDecimal[] hitBTCAsk = new BigDecimal[1000];

    static String[] bitZPair = new String[1000];
    private static BigDecimal[] bitZAsk = new BigDecimal[1000];
    private static BigDecimal[] bitZBid = new BigDecimal[1000];

    static String[] gateIOPair = new String[1000];
    private static BigDecimal[] gateIOAsk = new BigDecimal[1000];
    private static BigDecimal[] gateIOBid = new BigDecimal[1000];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        exRate = (TextView) findViewById(R.id.rate);
        exRate.setMovementMethod(new ScrollingMovementMethod());
        exRate.setText("Loading Data Please Wait");
        Toast.makeText(getApplicationContext(), "Loading Data Please Wait", LENGTH_LONG).show();

        getCryptoPair();
        //   new LongOperation().execute();

    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {

                z = 0;
                getCryptoPair();
                Thread.sleep(timer);

            } catch (InterruptedException e) {
                Thread.interrupted();
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {


            if (strBuilder != null) {
                exRate.setText(strBuilder.toString());
            } else {
                exRate.setText("Loading Data Please Wait");
            }

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    // ****************************************************
    //  REST api public end points to parse exchange rates
    //  in JSON format
    //*****************************************************

    //https://api.hitbtc.com/api/2/public/ticker
    //https://apiv2.bitz.com/Market/tickerall
    //https://api.liquid.com/products
    //https://api.huobi.pro/market/tickers
    //https://api.bithumb.com/public/ticker/ALL
    //https://openapi-v2.kucoin.com/api/v1/market/allTickers
    //https://api.gemini.com/v1/pubticker/btcusd


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
                        String quote = bitfinexPair[i].substring(4, bitfinexPair[i].length());
                        bitfinexPair[i] = base + "_" + quote;

                        Log.d("BITFINEX", bitfinexPair.length + "i:" + i + " " + (bitfinexPair[i]) + " " + bitfinexxBid[i].toString() + " " + bitfinexAsk[i].toString() + " " + bitfinexVolume[i].toString());

                        //    Log.d("Items: ", jArr.getString(i));



                        removeNullValues(bitfinexPair, bitfinexxBid, bitfinexAsk);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR parsing Bitfinex data", LENGTH_LONG).show();

            }
        });
        queue.add(jsonArr_bitfinex);

        URL = "https://api.bittrex.com/api/v1.1/public/getmarketsummaries";
        JsonObjectRequest bittrexObject = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {


                    if (response.getJSONArray("result") != null) {
                        JSONArray metadataArr = response.getJSONArray("result");

                        bittrexPair = new String[metadataArr.length()];
                        bittrexAsk = new BigDecimal[metadataArr.length()];
                        bittrexBid = new BigDecimal[metadataArr.length()];

                        for (int i = 0; i < metadataArr.length(); i++) {

                            JSONObject obj = metadataArr.getJSONObject(i);

                            bittrexBid[i] = BigDecimal.valueOf(Double.parseDouble(obj.getString("Bid")));
                            bittrexAsk[i] = BigDecimal.valueOf(Double.parseDouble(obj.getString("Ask")));
                            bittrexVolume[i] = BigDecimal.valueOf(Double.parseDouble(obj.getString("BaseVolume")));
                            //  bittrexPair[i] = obj.getString("MarketName");

                            String flip = obj.getString("MarketName");
                            int flipInt = flip.indexOf("-");
                            String base = flip.substring(0, flipInt);
                            String quote = flip.substring(flipInt + 1);
                            bittrexPair[i] = quote + "_" + base;
                            Log.d("checkBittrex", bittrexPair[i] + "\t BASE:" + base + " QUOTE:" + quote);

                            //Normalize data
                            bittrexPair[i] = bittrexPair[i].replace("-", "_");

                            Log.d("BittrexSet", bittrexPair.length + " " + bittrexPair[i] + bittrexVolume[i].toString());

                        }



                    }


                    removeNullValues(bittrexPair, bittrexBid, bittrexAsk);

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

                Toast.makeText(getApplicationContext(), "ERROR GATHER BITTREX DATA", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(bittrexObject);

        URL = "https://api.binance.com/api/v3/ticker/bookTicker";

        JsonArrayRequest binanceFeed = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
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



                   removeNullValues(binancePair, binanceBid, binanceAsk);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "ERROR GATHER Binance DATA", Toast.LENGTH_SHORT).show();

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


                    removeNullValues(okexPair, okexBid, okexAsk);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "ERROR GATHER OKEX DATA", Toast.LENGTH_SHORT).show();

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



                    removeNullValues(krakenPair, krakenBid, krakenAsk);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "ERROR GATHER Kraken DATA", Toast.LENGTH_SHORT).show();

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

                    //Data is normalized with baseTokens[i] and quoteTokens[i] because
                    //Poloniex has pairs reversed.  Ex. (BTC/USD  =  USDT_BTC)

                    String here = (String) keys.next();

                    String flip = here;
                    int flipInt = flip.indexOf("_");
                    String base = flip.substring(0, flipInt);
                    String quote = flip.substring(flipInt + 1);
                    poloniexPair[poloniexCounter] = quote + "_" + base;


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



                if (poloniexPair[0] != null) {
                    List<String> list = new ArrayList<String>();

                    for (String s : poloniexPair) {
                        if (s != null && s.length() > 0) {
                            list.add(s);

                        }
                    }

                    poloniexPair = list.toArray(new String[list.size()]);
                }

                if (poloniexAsk[0] != null) {
                    List<BigDecimal> list = new ArrayList<>();

                    for (BigDecimal b : poloniexAsk) {
                        if (b != null) {
                            list.add(b);

                        }
                    }

                    poloniexAsk = list.toArray(new BigDecimal[list.size()]);

                }

                if (poloniexBid[0] != null) {
                    List<BigDecimal> list = new ArrayList<>();

                    for (BigDecimal b : poloniexBid) {
                        if (b != null) {
                            list.add(b);

                        }
                    }

                    poloniexBid = list.toArray(new BigDecimal[list.size()]);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "ERROR GATHERING POLONIEX DATA", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(poloniexFeed);

        URL = "https://openapi.bitmart.com/v2/ticker";

        JsonArrayRequest bitMart = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject bitmart;

                    bitMartPair = new String[response.length()];
                    bitmartAsk = new BigDecimal[response.length()];
                    bitmartBid = new BigDecimal[response.length()];

                    for (int i = 0; i < response.length(); i++) {


                        bitmart = (JSONObject) response.get(i);

                        String pair = (String) bitmart.get("symbol_id");
                        String bid = (String) bitmart.get("bid_1");
                        String ask = (String) bitmart.get("ask_1");


                        if (pair != null) {
                            bitMartPair[i] = pair;
                            bitmartBid[i] = BigDecimal.valueOf(Double.parseDouble(bid));
                            bitmartAsk[i] = BigDecimal.valueOf(Double.parseDouble(ask));
                            System.out.println("bitmart" + pair + " " + bid + " " + ask + "\t" + bitMartPair.length);
                        }


                    }



                    if (bitMartPair[0] != null) {
                        List<String> list = new ArrayList<String>();

                        for (String s : bitMartPair) {
                            if (s != null && s.length() > 0) {
                                list.add(s);

                            }
                        }

                        bitMartPair = list.toArray(new String[list.size()]);
                    }

                    if (bitmartAsk[0] != null) {
                        List<BigDecimal> list = new ArrayList<>();

                        for (BigDecimal b : bitmartAsk) {
                            if (b != null) {
                                list.add(b);

                            }
                        }

                        bitmartAsk = list.toArray(new BigDecimal[list.size()]);

                    }

                    if (bitmartBid[0] != null) {
                        List<BigDecimal> list = new ArrayList<>();

                        for (BigDecimal b : bitmartBid) {
                            if (b != null) {
                                list.add(b);

                            }
                        }

                        bitmartBid = list.toArray(new BigDecimal[list.size()]);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR GATHER BITMART DATA", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(bitMart);

        URL = "https://api.hitbtc.com/api/2/public/ticker";

        JsonArrayRequest hitBTC = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                String pair;
                BigDecimal bid;
                BigDecimal ask;

                try {

                    for (int i = 0; i < 200; i++) {

                        JSONObject hitBTCobj = (JSONObject) response.get(i);

                        pair = (String) hitBTCobj.get("symbol");
                        ask = BigDecimal.valueOf(Double.parseDouble(String.valueOf(hitBTCobj.get("ask"))));
                        bid = BigDecimal.valueOf(Double.parseDouble(String.valueOf(hitBTCobj.get("bid"))));

                        if (pair != null) {
                            hitBTCPair[i] = pair;
                            hitBTCBid[i] = bid;
                            hitBTCAsk[i] = ask;
                        }

                        String base = hitBTCPair[i].substring(0, 3);
                        String quote = hitBTCPair[i].substring(3, hitBTCPair[i].length());

                        normalizeHitBTC(hitBTCPair[i], i);

                        if (hitBTCPair[i].substring(hitBTCPair[i].length() - 3, hitBTCPair[i].length()).equals("BTC")
                                || hitBTCPair[i].substring(hitBTCPair[i].length() - 3, hitBTCPair[i].length()).equals("ETH")
                                || hitBTCPair[i].substring(hitBTCPair[i].length() - 3, hitBTCPair[i].length()).equals("USD")
                                || hitBTCPair[i].substring(hitBTCPair[i].length() - 3, hitBTCPair[i].length()).equals("BCH")
                                || hitBTCPair[i].substring(hitBTCPair[i].length() - 3, hitBTCPair[i].length()).equals("USDT")

                        ) {
                            base = hitBTCPair[i].substring(0, hitBTCPair[i].length() - 3);
                            hitBTCPair[i] = base + "_" + quote;
                        }


                        if (!hitBTCPair[i].contains("_") || hitBTCPair[i].contains(("__"))) {
                            hitBTCPair[i] = "EMPTY";
                        }


                        System.out.println("HITBTC" + " " + hitBTCPair[i] + " " + hitBTCAsk[i] + " " + hitBTCBid[i]);

                    }

                    if (hitBTCPair[0] != null) {
                        List<String> list = new ArrayList<String>();

                        for (String s : hitBTCPair) {
                            if (s != null && s.length() > 0) {
                                list.add(s);

                            }
                        }

                        hitBTCPair = list.toArray(new String[list.size()]);
                    }

                    if (hitBTCAsk[0] != null) {
                        List<BigDecimal> list = new ArrayList<>();

                        for (BigDecimal b : hitBTCAsk) {
                            if (b != null) {
                                list.add(b);

                            }
                        }

                        hitBTCAsk = list.toArray(new BigDecimal[list.size()]);

                    }

                    if (hitBTCBid[0] != null) {
                        List<BigDecimal> list = new ArrayList<>();

                        for (BigDecimal b : hitBTCBid) {
                            if (b != null) {
                                list.add(b);

                            }
                        }

                        hitBTCBid = list.toArray(new BigDecimal[list.size()]);
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

        queue.add(hitBTC);

        URL = "https://apiv2.bitz.com/Market/tickerall";

        JsonObjectRequest bitZ = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONObject obj = (JSONObject) response.get("data");
                    Log.d("BITZ", obj.toString());

                    String pair;

                    int i = 0;


                    for (Iterator key = obj.keys(); key.hasNext(); ) {
                        JSONObject innerObj = (JSONObject) obj.get((String) key.next());


                        pair = (String) innerObj.get("symbol");
                        pair = pair.toUpperCase();

                        bitZPair[i] = pair;
                        String bid = String.valueOf(innerObj.get("askPrice"));
                        String ask = String.valueOf(innerObj.get("bidPrice"));
                        bitZBid[i] = BigDecimal.valueOf(Double.parseDouble(bid));
                        bitZAsk[i] = BigDecimal.valueOf(Double.parseDouble(ask));

                        Log.d("BITZ", innerObj.get("symbol").toString() + " " + pair + " " + bitZPair[i] + " " + bitZAsk[i] + " " + bitZBid[i] + " " + i);

                        i++;

                    }


                    if (bitZPair[0] != null) {
                        List<String> list = new ArrayList<String>();

                        for (String s : bitZPair) {
                            if (s != null && s.length() > 0) {
                                list.add(s);

                            }
                        }

                        bitZPair = list.toArray(new String[list.size()]);
                    }

                    if (bitZAsk[0] != null) {
                        List<BigDecimal> list = new ArrayList<>();

                        for (BigDecimal b : bitZAsk) {
                            if (b != null) {
                                list.add(b);

                            }
                        }

                        bitZAsk = list.toArray(new BigDecimal[list.size()]);

                    }

                    if (bitZBid[0] != null) {
                        List<BigDecimal> list = new ArrayList<>();

                        for (BigDecimal b : bitZBid) {
                            if (b != null) {
                                list.add(b);

                            }
                        }

                        bitZBid = list.toArray(new BigDecimal[list.size()]);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "ERROR GATHERING bitz DATA", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(bitZ);


        URL = "https://data.gateio.co/api2/1/tickers";


        JsonObjectRequest gateIO = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONObject obj = (JSONObject) response;


                try {

                    int i = 0;
                    for (Iterator FUCKYOU = obj.keys(); FUCKYOU.hasNext(); ) {

                        if (i < 210) {

                            String FUCKstr = FUCKYOU.next().toString().toUpperCase();
                            gateIOPair[i] = FUCKstr;
                        } else {
                            i = 0;
                        }
                        i++;
                    }

                    i = 0;

                    for (Iterator keys = obj.keys(); keys.hasNext(); ) {

                        if (i < 210) {

                            JSONObject innerObj = (JSONObject) obj.get(String.valueOf(keys.next()));
                            String bid = String.valueOf(innerObj.get("highestBid"));
                            String ask = String.valueOf(innerObj.get("lowestAsk"));

                            gateIOAsk[i] = BigDecimal.valueOf(Double.parseDouble(String.valueOf(ask)));
                            gateIOBid[i] = BigDecimal.valueOf(Double.parseDouble(String.valueOf(bid)));

                            // Log.d("GATEIOset")


                        } else {
                            i = 0;
                        }

                        i++;
                    }


                    if (gateIOPair[0] != null) {
                        List<String> list = new ArrayList<String>();

                        for (String s : gateIOPair) {
                            if (s != null && s.length() > 0) {
                                list.add(s);

                            }
                        }

                        gateIOPair = list.toArray(new String[list.size()]);
                    }

                    if (gateIOAsk[0] != null) {
                        List<BigDecimal> list = new ArrayList<>();

                        for (BigDecimal b : gateIOAsk) {
                            if (b != null) {
                                list.add(b);

                            }
                        }

                        gateIOAsk = list.toArray(new BigDecimal[list.size()]);

                    }

                    if (gateIOBid[0] != null) {
                        List<BigDecimal> list = new ArrayList<>();

                        for (BigDecimal b : gateIOBid) {
                            if (b != null) {
                                list.add(b);

                            }
                        }

                        gateIOBid = list.toArray(new BigDecimal[list.size()]);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error gathering Gate.IO data", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(gateIO);


        for (int i = 0; i < gateIOPair.length; i++) {
            Log.d("GATEIOset", "" + gateIOPair[i] + " \t" + gateIOAsk[i] + " "
                    + "\t " + gateIOBid[i] + " : ");
        }

        //******************************************************************** JSON Parsing Complete
        //
        //******************************   Assign tokens

        String[] pairTokens = {};
        BigDecimal[] bidTokens = {};
        BigDecimal[] askTokens = {};

        pairTokens = concatenate(pairTokens, bitfinexPair);
        pairTokens = concatenate(pairTokens, bittrexPair);
        pairTokens = concatenate(pairTokens, binancePair);
        pairTokens = concatenate(pairTokens, okexPair);
        pairTokens = concatenate(pairTokens, krakenPair);
        pairTokens = concatenate(pairTokens, poloniexPair);
        pairTokens = concatenate(pairTokens, bitMartPair);
        pairTokens = concatenate(pairTokens, hitBTCPair);
        pairTokens = concatenate(pairTokens, bitZPair);
        pairTokens = concatenate(pairTokens, gateIOPair);

        bidTokens = concatenate(bidTokens, bitfinexxBid);
        bidTokens = concatenate(bidTokens, bittrexBid);
        bidTokens = concatenate(bidTokens, binanceBid);
        bidTokens = concatenate(bidTokens, okexBid);
        bidTokens = concatenate(bidTokens, krakenBid);
        bidTokens = concatenate(bidTokens, poloniexBid);
        bidTokens = concatenate(bidTokens, bitmartBid);
        bidTokens = concatenate(bidTokens, hitBTCBid);
        bidTokens = concatenate(bidTokens, bitZBid);
        bidTokens = concatenate(bidTokens, gateIOBid);

        askTokens = concatenate(askTokens, bitfinexAsk);
        askTokens = concatenate(askTokens, bittrexAsk);
        askTokens = concatenate(askTokens, binanceAsk);
        askTokens = concatenate(askTokens, okexAsk);
        askTokens = concatenate(askTokens, krakenAsk);
        askTokens = concatenate(askTokens, poloniexAsk);
        askTokens = concatenate(askTokens, bitmartAsk);
        askTokens = concatenate(askTokens, hitBTCAsk);
        askTokens = concatenate(askTokens, bitZAsk);
        askTokens = concatenate(askTokens, gateIOAsk);

//*****************************************************************


        if ((!Arrays.asList(pairTokens).subList(0, pairTokens.length).contains(null))
                && (!Arrays.asList(askTokens).subList(0, askTokens.length).contains(null))
                && (!Arrays.asList(bidTokens).subList(0, bidTokens.length).contains(null))
                && (bitfinexPair.length < 1000 && bittrexPair.length < 1000 && binancePair.length < 1000 && okexPair.length < 1000
                && krakenPair.length < 1000 && poloniexPair.length < 1000 && bitMartPair.length < 1000 && hitBTCPair.length < 1000
                && bitZPair.length < 1000 && bitZPair.length < 1000 && gateIOPair.length < 1000)) {


            timer = 70000;
            if (strBuilder != null) {
                strBuilder.delete(0, strBuilder.length());
            }


            //************************************************************************ Exchanges
            exchange = new String[pairTokens.length];
            for (int i = 0; i < bitfinexPair.length; i++) {
                exchange[i] = "Bitfinex";
            }
            int market1 = bitfinexPair.length + bittrexPair.length;
            for (int i = bitfinexPair.length; i < market1; i++) {
                exchange[i] = "Bittrex";
            }
            int market2 = bitfinexPair.length + bittrexPair.length + binancePair.length;
            for (int i = market1; i < market2; i++) {
                exchange[i] = "Binance";
            }
            int market3 = bitfinexPair.length + bittrexPair.length + binancePair.length + okexPair.length;
            for (int i = market2; i < market3; i++) {
                exchange[i] = "OKEX";
            }
            int market4 = bitfinexPair.length + bittrexPair.length + binancePair.length + okexPair.length + krakenPair.length;
            for (int i = market3; i < market4; i++) {
                exchange[i] = "Kraken";
            }
            int market5 = bitfinexPair.length + bittrexPair.length + binancePair.length + okexPair.length + krakenPair.length + poloniexPair.length;
            for (int i = market4; i < market5; i++) {
                exchange[i] = "Poloniex";
            }
            int market6 = bitfinexPair.length + bittrexPair.length + binancePair.length + okexPair.length + krakenPair.length + poloniexPair.length + bitMartPair.length;
            for (int i = market5; i < market6; i++) {
                exchange[i] = "Bitmart";
            }
            int market7 = bitfinexPair.length + bittrexPair.length + binancePair.length + okexPair.length + krakenPair.length + poloniexPair.length + bitMartPair.length + hitBTCPair.length;
            for (int i = market6; i < market7; i++) {
                exchange[i] = "HitBTC";
            }
            int market8 = bitfinexPair.length + bittrexPair.length + binancePair.length + okexPair.length + krakenPair.length + poloniexPair.length + bitMartPair.length + hitBTCPair.length + bitZPair.length;
            for (int i = market7; i < market8; i++) {
                exchange[i] = "BitZ";
            }
            int market9 = bitfinexPair.length + bittrexPair.length + binancePair.length + okexPair.length + krakenPair.length + poloniexPair.length + bitMartPair.length + hitBTCPair.length + bitZPair.length + gateIOPair.length;
            for (int i = market8; i < market9; i++) {
                exchange[i] = "GateIO";
            }
            //


            //************************************************************  [BASE]-[QUOTE]  TOKENS
            String[] baseTokens = new String[pairTokens.length];
            String[] quoteTokens = new String[pairTokens.length];

            for (int i = 0; i < pairTokens.length; i++) {

                if (pairTokens[i].indexOf("_") == -1) {
                    baseTokens[i] = "EMPTY";
                    quoteTokens[i] = "EMPTY";
                } else {

                    int indexOf_ = pairTokens[i].indexOf("_");
                    baseTokens[i] = pairTokens[i].substring(0, indexOf_);
                    quoteTokens[i] = pairTokens[i].substring(indexOf_ + 1, pairTokens[i].length());
                }

                if (pairTokens[i].substring(0, 4).equals("USDT")) {
                    String tempB = baseTokens[i];
                    String tempQ = quoteTokens[i];
                    pairTokens[i] = tempQ + "_" + tempB;
                    baseTokens[i] = tempQ;
                    quoteTokens[i] = tempB;
                }

            }


            arbitrage(pairTokens, baseTokens, quoteTokens, bidTokens, askTokens);


        } else {

            new LongOperation().execute();
        }
    }


    private static boolean liquidCheck(String str) {

        switch (str) {

            case "ETH":
                return true;
            case "BTC":
                return true;
            case "USDT":
                return true;
            case "LTC":
                return true;
            case "REP":
                return true;
            case "BCH":
                return true;
            case "XRP":
                return true;
            case "EOS":
                return true;
            case "BNB":
                return true;
            case "XLM":
                return true;
            case "ADA":
                return true;
            case "NEO":
                return true;
            case "XMR":
                return true;
            case "BSV":
                return true;
            case "TRX":
                return true;
//            case "JPY":
//                return true;
            case "GAS":
                return true;
            case "STOREJ":
                return true;
            case "EUR":
                return true;
            case "GBP":
                return true;
            case "ETC":
                return true;
            case "DASH":
                return true;
            case "ZEC":
                return true;
            case "QTUM":
                return true;
            case "ATOM":
                return true;
            case "USDC":
                return true;
            case "HT":
                return true;
            case "BEAM":
                return true;
            case "NEM":
                return true;
            case "DOGE":
                return true;
            case "LAMB":
                return true;
            case "BTT":
                return true;
            case "DAI":
                return true;


        }

        return false;
    }

    public void arbitrage(String[] pairTokens, String[] baseTokens, String[] quoteTokens, BigDecimal[] bidTokens, BigDecimal[] askTokens) {


        String base[] = baseTokens;
        String quote[] = pairTokens;
        strBuilder.delete(0, strBuilder.length());
        List<String> currencyList = Arrays.asList(baseTokens);
        Set<String> currList = new HashSet<String>(currencyList);

        String[] currArr = new String[baseTokens.length];
        currArr = currList.toArray(new String[currList.size()]);
        HashMap<String, String> currMap = new HashMap<>();


        //******************************************************  Filter out liquid currencies
        ArrayList<String> baseTokensLiq = new ArrayList<>();
        ArrayList<String> quoteTokensLiq = new ArrayList<>();
        chainIndex = new int[pairTokens.length];
        for (int i = 0, ii = 0; i < baseTokens.length; i++) {
            if (liquidCheck(baseTokens[i]) && liquidCheck(quoteTokens[i])) {
                Log.d("CheckLiquid", baseTokens[i] + " " + quoteTokens[i] + " \t at index:" + i + " \t Exchange:" + exchange[i]);
                baseTokensLiq.add(baseTokens[i]);
                quoteTokensLiq.add(quoteTokens[i]);
                chainIndex[ii] = i;
                ii++;
            }

        }

        String[] baseTokensLiqArr = new String[baseTokensLiq.size()];
        baseTokensLiqArr = baseTokensLiq.toArray(baseTokensLiqArr);
        String[] quoteTokensLiqArr = new String[quoteTokensLiq.size()];
        quoteTokensLiqArr = quoteTokensLiq.toArray(quoteTokensLiqArr);

        Log.d("LiquidCoins", baseTokensLiq.size() + " " + String.valueOf(baseTokensLiq));
        Log.d("LiquidCoins", quoteTokensLiq.size() + " " + String.valueOf(quoteTokensLiq));

        String[] pairTokensLiquid = new String[baseTokens.length];

        //Load liquid pairs
        for (int i = 0; i < baseTokensLiqArr.length; i++) {
            pairTokensLiquid[i] = baseTokensLiqArr[i];
            pairTokensLiquid[i] += quoteTokensLiqArr[i];
        }

        for (int a = 0; a < baseTokensLiqArr.length; a++) {

            c1 = pairTokensLiquid[a];
            c1Base = baseTokensLiqArr[a];
            c1Quote = quoteTokensLiqArr[a];

            for (int b = 0; b < baseTokensLiqArr.length; b++) {

                c2 = pairTokensLiquid[b];
                c2Base = baseTokensLiqArr[b];
                c2Quote = quoteTokensLiqArr[b];

                for (int c = 0; c < baseTokensLiqArr.length; c++) {

                    c3 = pairTokensLiquid[c];
                    c3Base = baseTokensLiqArr[c];
                    c3Quote = quoteTokensLiqArr[c];


                    z++;

                    // Compare base currency with quote currency to secure round-trip triangular arbitrage
                    if (
                            (  // Path 1
                                    (c1Quote.equals(c2Base) && c2Quote.equals(c3Base))
                                            && (c1Base.equals(c3Quote)))

                                    ||
                                    (   // Path 2
                                            (c1Quote.equals(c2Base) && c2Quote.equals(c3Quote))
                                                    && (c1Base.equals(c3Base)))

                                    ||
                                    (   // Path 3
                                            (c1Quote.equals(c2Quote) && c2Base.equals(c3Base))
                                                    && (c1Base.equals(c3Quote)))

                                    ||
                                    (// Path 4
                                            (c1Quote.equals(c2Quote) && c2Base.equals(c3Quote))
                                                    && (c1Base.equals(c3Base)))

                                    ||
                                    (   // Path 5
                                            (c1Base.equals(c2Base) && c2Quote.equals(c3Base))
                                                    && (c1Quote.equals(c3Quote)))
                                    ||

                                    (   // Path 6
                                            (c1Base.equals(c2Base) && c2Quote.equals(c3Quote))
                                                    && (c1Quote.equals(c3Base)))

                                    ||
                                    (   // Path 7
                                            (c1Base.equals(c2Quote) && c2Base.equals(c3Base))
                                                    && (c1Quote.equals(c3Quote)))

                                    ||
                                    (   // Path 8
                                            (c1Base.equals(c2Quote) && c2Base.equals(c3Quote))
                                                    && (c1Quote.equals(c3Base)))
                    ) {


                        //  arbChain.add(c1 + " " + c2 + " " + c3);

                        timer = 70000;
                        new LongOperation().execute();


                        Log.d("CHAINS", "[" + exchange[chainIndex[a]] + "] [" + exchange[chainIndex[b]] + "] [" + exchange[chainIndex[c]] + "]");
                        Log.d("CHAINS", "C1: [" + pairTokens[chainIndex[a]] + "]\t C2: [" + pairTokens[chainIndex[b]] + "]\t C3: [" + pairTokens[chainIndex[c]]);//                        Log.d("ArbChain", "C1: [" + pairTokens[chainIndex[a]] + "]\t C2: [" + pairTokens[chainIndex[b]] + "]\t C3: [" + pairTokens[chainIndex[c]]);
                        Log.d("CHAINS", "C1: [" + bidTokens[chainIndex[a]] + "]\t C2: [" + bidTokens[chainIndex[b]] + "]\t C3: [" + bidTokens[chainIndex[c]]);
                        Log.d("CHAINS", "C1: [" + askTokens[chainIndex[a]] + "]\t C2:[" + askTokens[chainIndex[b]] + "]\t C3: [" + askTokens[chainIndex[c]]);


                        // BID / ASK RATES for 3 pairs *****************
                        cd1b = bidTokens[chainIndex[a]];   // C1 Bid
                        cd1a = askTokens[chainIndex[a]];   // C1 Ask
                        cd2b = bidTokens[chainIndex[b]];   // C2 Bid
                        cd2a = askTokens[chainIndex[b]];   // C2 Ask
                        cd3b = bidTokens[chainIndex[c]];   // C3 Bid
                        cd3a = askTokens[chainIndex[c]];   // C3 Ask
                        //**********************************************


                        // Calculate triangular arbitrage

//                        Log.d("DATACLEAN_PATH_4", "[" + c1 + "] [" + c2 + "] [" + c3 + "]");
//                        Log.d("DATACLEAN", "[" + exchange[chainIndex[a]] + "] [" + exchange[chainIndex[b]] + "] [" + exchange[chainIndex[c]] + "]");
//                        Log.d("DATACLEAN", "C1: [" + pairTokens[chainIndex[a]] + "]\t C2: [" + pairTokens[chainIndex[b]] + "]\t C3: [" + pairTokens[chainIndex[c]]);//                        Log.d("ArbChain", "C1: [" + pairTokens[chainIndex[a]] + "]\t C2: [" + pairTokens[chainIndex[b]] + "]\t C3: [" + pairTokens[chainIndex[c]]);
//                        Log.d("DATACLEAN", "C1: [" + bidTokens[chainIndex[a]] + "]\t C2: [" + bidTokens[chainIndex[b]] + "]\t C3: [" + bidTokens[chainIndex[c]]);
//                        Log.d("DATACLEAN", "C1: [" + askTokens[chainIndex[a]] + "]\t C2:[" + askTokens[chainIndex[b]] + "]\t C3: [" + askTokens[chainIndex[c]]);


                        if (  // CHAIN 1
                                (c1Quote.equals(c2Base) && c2Quote.equals(c3Base))
                                        && (c1Base.equals(c3Quote))) {


                            if (cd1b.doubleValue() > (1 / cd2b.doubleValue()) * (1 / cd3b.doubleValue())) {
                                // Transitivity is bigger than the Implied Synthetic Cross-Rate (CLOCKWISE)

                                triArbitrage = maxBet * cd1b.doubleValue(); // SELL
                                triArbitrage = triArbitrage * cd2b.doubleValue(); // SELL
                                triArbitrage = triArbitrage * cd3b.doubleValue(); // SELL

                                path = 1;

                            } else if (cd1a.doubleValue() < (1 / cd2a.doubleValue()) * (1 / cd3a.doubleValue())) {
                                // Transitivity is smaller than the Implied Synthetic Cross-Rate (COUNTER-CLOCKWISE)


                                triArbitrage = maxBet / cd3a.doubleValue(); // BUY
                                triArbitrage = triArbitrage / cd2a.doubleValue(); // BUY
                                triArbitrage = triArbitrage / cd1a.doubleValue(); // BUY
                                path = 16;

                            } else {
                                triArbitrage = Double.valueOf(0);
                            }


                        }


                        if (   // CHAIN 2
                                (c1Quote.equals(c2Base) && c2Quote.equals(c3Quote))
                                        && (c1Base.equals(c3Base))) {


                            if (cd1b.doubleValue() > (1 / cd2b.doubleValue()) * cd3a.doubleValue()) {
                                // Transitivity is bigger than the Implied Synthetic Cross-Rate

                                triArbitrage = maxBet * cd1b.doubleValue(); // SELL
                                triArbitrage = triArbitrage * cd2b.doubleValue(); // SELL
                                triArbitrage = triArbitrage / cd3a.doubleValue(); // BUY

                                path = 2;

                            } else if (cd1a.doubleValue() < (1 / cd2a.doubleValue()) * cd3b.doubleValue()) {
                                // Transitivity is bigger than the Implied Synthetic Cross-Rate

                                triArbitrage = maxBet * cd3b.doubleValue(); // SELL
                                triArbitrage = triArbitrage / cd2a.doubleValue(); // BUY
                                triArbitrage = triArbitrage / cd1a.doubleValue(); // BUY

                                path = 12;

                            } else {
                                triArbitrage = Double.valueOf(0);
                            }

                        }


                        if (   // CHAIN 3
                                (c1Quote.equals(c2Quote) && c2Base.equals(c3Base))
                                        && (c1Base.equals(c3Quote))) {


                            if (cd1b.doubleValue() > cd2a.doubleValue() * (1 / cd3b.doubleValue())) {
                                // Transitivity is bigger than the Implied Synthetic Cross-Rate

                                triArbitrage = maxBet * cd1b.doubleValue(); // SELL
                                triArbitrage = triArbitrage / cd2a.doubleValue(); // BUY
                                triArbitrage = triArbitrage * cd3b.doubleValue(); // SELL

                                path = 3;

                            } else if (cd1a.doubleValue() < cd2b.doubleValue() * (1 / cd3a.doubleValue())) {
                                // Transitivity is bigger than the Implied Synthetic Cross-Rate

                                triArbitrage = maxBet / cd3a.doubleValue(); // BUY
                                triArbitrage = triArbitrage * cd2b.doubleValue(); // SELL
                                triArbitrage = triArbitrage / cd1a.doubleValue(); // BUY

                                path = 14;

                            } else {
                                triArbitrage = Double.valueOf(0);
                            }


                        }


                        if (// CHAIN 4
                                (c1Quote.equals(c2Quote) && c2Base.equals(c3Quote))
                                        && (c1Base.equals(c3Base))) {

                            if (cd1b.doubleValue() > (cd2a.doubleValue() * cd3a.doubleValue())) {
                                // Transitivity is bigger than the Implied Synthetic Cross-Rate


                                triArbitrage = maxBet * cd1b.doubleValue(); // SELL
                                triArbitrage = triArbitrage / cd2a.doubleValue(); // BUY
                                triArbitrage = triArbitrage / cd3a.doubleValue(); // BUY

                                path = 4;

                            } else if (cd1a.doubleValue() < (cd2b.doubleValue() * cd3b.doubleValue())) {
                                // Transitivity is bigger than the Implied Synthetic Cross-Rate


                                triArbitrage = maxBet * cd3b.doubleValue(); // SELL
                                triArbitrage = triArbitrage * cd2b.doubleValue(); // SELL
                                triArbitrage = triArbitrage / cd1a.doubleValue(); // BUY

                                path = 10;

                            } else {
                                triArbitrage = Double.valueOf(0);
                            }

                        }

                        if (   // CHAIN 5
                                (c1Base.equals(c2Base) && c2Quote.equals(c3Base))
                                        && (c1Quote.equals(c3Quote))) {


                            if (cd1a.doubleValue() < (cd2b.doubleValue() * cd3b.doubleValue())) {
                                // Transitivity is bigger than the Implied Synthetic Cross-Rate

                                triArbitrage = maxBet / cd1a.doubleValue(); // BUY
                                triArbitrage = triArbitrage * cd2b.doubleValue(); // SELL
                                triArbitrage = triArbitrage * cd3b.doubleValue(); // SELL

                                path = 5;

                            } else if (cd1b.doubleValue() > (cd2a.doubleValue() * cd3a.doubleValue())) {
                                // Transitivity is bigger than the Implied Synthetic Cross-Rate

                                triArbitrage = maxBet / cd3a.doubleValue(); // BUY
                                triArbitrage = triArbitrage / cd2a.doubleValue(); // BUY
                                triArbitrage = triArbitrage * cd1b.doubleValue(); // SELL

                                path = 15;

                            } else {
                                triArbitrage = Double.valueOf(0);
                            }

                        }

                        if (   // CHAIN 6
                                (c1Base.equals(c2Base) && c2Quote.equals(c3Quote))
                                        && (c1Quote.equals(c3Base))) {

                            if (cd1b.doubleValue() > (cd2a.doubleValue() * (1 / cd3b.doubleValue()))) {

                                // Transitivity is bigger than the Implied Synthetic Cross-Rate

                                triArbitrage = maxBet * cd3b.doubleValue(); // SELL
                                triArbitrage = triArbitrage / cd2a.doubleValue(); // BUY
                                triArbitrage = triArbitrage * cd1b.doubleValue(); // SELL

                                path = 11;

                            } else if (cd1a.doubleValue() < (cd2b.doubleValue() * (1 / cd3a.doubleValue()))) {
                                // Transitivity is smaller than the Implied Synthetic Cross-Rate

                                triArbitrage = maxBet / cd1a.doubleValue(); // BUY
                                triArbitrage = triArbitrage * cd2b.doubleValue(); // SELL
                                triArbitrage = triArbitrage / cd3a.doubleValue(); // BUY
                                path = 6;

                            } else {
                                triArbitrage = Double.valueOf(0);
                            }

                        }


                        if (   // CHAIN 7
                                (c1Base.equals(c2Quote) && c2Base.equals(c3Base))
                                        && (c1Quote.equals(c3Quote))) {


                            if (cd1a.doubleValue() < (1 / cd2a.doubleValue()) * cd3b.doubleValue()) {
                                // Transitivity is bigger than the Implied Synthetic Cross-Rate


                                triArbitrage = maxBet / cd1a.doubleValue(); // BUY
                                triArbitrage = triArbitrage / cd2a.doubleValue(); // BUY
                                triArbitrage = triArbitrage * cd3b.doubleValue(); // SELL

                                path = 7;

                            } else if (cd1b.doubleValue() > (1 / cd2b.doubleValue()) * cd3a.doubleValue()) {
                                // Transitivity is bigger than the Implied Synthetic Cross-Rate


                                triArbitrage = maxBet / cd3a.doubleValue(); // BUY
                                triArbitrage = triArbitrage * cd2b.doubleValue(); // SELL
                                triArbitrage = triArbitrage * cd1b.doubleValue(); // SELL
                                path = 13;

                            } else {
                                triArbitrage = Double.valueOf(0);
                            }

                        }


                        if (   // CHAIN 8
                                (c1Base.equals(c2Quote) && c2Base.equals(c3Quote))
                                        && (c1Quote.equals(c3Base))) {

                            //	// ********* CHAIN 8


                            if (cd1a.doubleValue() < (1 / cd2a.doubleValue()) * (1 / cd3a.doubleValue())) {
                                // Transitivity is smaller than the Implied Synthetic Cross-Rate (CLOCKWISE)

                                triArbitrage = maxBet / cd1a.doubleValue(); // BUY
                                triArbitrage = triArbitrage / cd2a.doubleValue(); // BUY
                                triArbitrage = triArbitrage / cd3a.doubleValue(); // BUY

                                path = 8;

                            } else if (cd1b.doubleValue() > (1 / cd2b.doubleValue()) * (1 / cd3b.doubleValue())) {
                                // Transitivity is bigger than the Implied Synthetic Cross-Rate (COUNTER-CLOCKWISE)

                                triArbitrage = maxBet * cd3b.doubleValue(); // SELL
                                triArbitrage = triArbitrage * cd2b.doubleValue(); // SELL
                                triArbitrage = triArbitrage * cd1b.doubleValue(); // SELL
                                path = 9;

                            } else {
                                triArbitrage = Double.valueOf(0);
                            }

                        }

                        optimalPathText = (

                                //CLOCKWISE

                                ((path == 1) ? " SELL: " + c1 + " " + " SELL: " + c2 + " " + " SELL: " + c3 : "")
                                        + ((path == 2) ? " SELL: " + c1 + " " + " SELL: " + c2 + " " + " BUY: " + c3 : "")
                                        + ((path == 3) ? " SELL: " + c1 + " " + " BUY: " + c2 + " " + " SELL: " + c3 : "")
                                        + ((path == 4) ? " SELL: " + c1 + " " + " BUY: " + c2 + " " + " BUY: " + c3 : "")
                                        + ((path == 5) ? " BUY: " + c1 + " " + " SELL: " + c2 + " " + " SELL: " + c3 : "")
                                        + ((path == 6) ? " BUY: " + c1 + " " + " SELL: " + c2 + " " + " BUY: " + c3 : "")
                                        + ((path == 7) ? " BUY: " + c1 + " " + " BUY: " + c2 + " " + " SELL: " + c3 : "")
                                        + ((path == 8) ? " BUY: " + c1 + " " + " BUY: " + c2 + " " + " BUY: " + c3 : "")

                                        //COUNTER-CLOCKWISE

                                        + ((path == 9) ? " SELL: " + c3 + " " + " SELL: " + c2 + " " + " SELL: " + c1 : "")
                                        + ((path == 10) ? " SELL: " + c3 + " " + " SELL: " + c2 + " " + " BUY: " + c1 : "")
                                        + ((path == 11) ? " SELL: " + c3 + " " + " BUY: " + c2 + " " + " SELL: " + c1 : "")
                                        + ((path == 12) ? " SELL: " + c3 + " " + " BUY: " + c2 + " " + " BUY: " + c1 : "")
                                        + ((path == 13) ? " BUY: " + c3 + " " + " SELL: " + c2 + " " + " SELL: " + c1 : "")
                                        + ((path == 14) ? " BUY: " + c3 + " " + " SELL: " + c2 + " " + " BUY: " + c1 : "")
                                        + ((path == 15) ? " BUY: " + c3 + " " + " BUY: " + c2 + " " + " SELL: " + c1 : "")
                                        + ((path == 16) ? " BUY: " + c3 + " " + " BUY: " + c2 + " " + " BUY: " + c1 : "")

                        );

                        //***********************************************************

                        if (
                                triArbitrage > 101 && triArbitrage != 0

                                    //    && exchange[chainIndex[a]].equals(exchange[chainIndex[b]])

                        ) {


                            triArbitrage -= 100;


                            strBuilder.append("[" + exchange[chainIndex[a]] + "] [" + exchange[chainIndex[b]] + "] [" + exchange[chainIndex[c]] + "]\n");
                            strBuilder.append(optimalPathText + "\n");
                            //  strBuilder.append("Trade 1: [" + pairTokens[chainIndex[a]] + "]\t Trade 2: [" + pairTokens[chainIndex[b]] + "]\t Trade 3: [" + pairTokens[chainIndex[c]] + "]\n");
                            strBuilder.append("Ask: [" + askTokens[chainIndex[a]] + "]\t Ask:[" + askTokens[chainIndex[b]] + "]\t Ask: [" + askTokens[chainIndex[c]] + "]\n");
                            strBuilder.append("Bid: [" + bidTokens[chainIndex[a]] + "]\t Bid: [" + bidTokens[chainIndex[b]] + "]\t Bid: [" + bidTokens[chainIndex[c]] + "]\n");
                            strBuilder.append("Arb Spread: [ " + triArbitrage.toString() + "% ] \t CHAIN:" + path + "\n\n");


                            Log.d("ArbPROFIT", "[" + exchange[chainIndex[a]] + "] [" + exchange[chainIndex[b]] + "] [" + exchange[chainIndex[c]] + "]");
                            Log.d("ArbPROFIT", "C1: [" + pairTokens[chainIndex[a]] + "]\t C2: [" + pairTokens[chainIndex[b]] + "]\t C3: [" + pairTokens[chainIndex[c]] + "]");//                        Log.d("ArbChain", "C1: [" + pairTokens[chainIndex[a]] + "]\t C2: [" + pairTokens[chainIndex[b]] + "]\t C3: [" + pairTokens[chainIndex[c]]);
                            Log.d("ArbPROFIT", "C1: [" + askTokens[chainIndex[a]] + "]\t C2:[" + askTokens[chainIndex[b]] + "]\t C3: [" + askTokens[chainIndex[c]] + "]");
                            Log.d("ArbPROFIT", "C1: [" + bidTokens[chainIndex[a]] + "]\t C2: [" + bidTokens[chainIndex[b]] + "]\t C3: [" + bidTokens[chainIndex[c]] + "]");
                            Log.d("ArbPROFIT", String.valueOf(triArbitrage) + " \t PATH:" + path + "\n****");

                        }

                    }


                }
            }
        }


    }


    public void removeNullValues(String[] pairs, BigDecimal[] bids, BigDecimal[] asks){


        if (pairs[0] != null) {
            List<String> list = new ArrayList<String>();

            for (String s : pairs) {
                if (s != null && s.length() > 0) {
                    list.add(s);

                }
            }

            pairs = list.toArray(new String[list.size()]);
        }

        if (bids[0] != null) {
            List<BigDecimal> list = new ArrayList<>();

            for (BigDecimal b : bids) {
                if (b != null) {
                    list.add(b);

                }
            }

            bids = list.toArray(new BigDecimal[list.size()]);

        }

        if (asks[0] != null) {
            List<BigDecimal> list = new ArrayList<>();

            for (BigDecimal b : asks) {
                if (b != null) {
                    list.add(b);

                }
            }

            asks = list.toArray(new BigDecimal[list.size()]);
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