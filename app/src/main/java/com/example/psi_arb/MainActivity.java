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
import java.util.stream.Stream;

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



                for (int i = 0; i < 50; i++) {

                    try {

                        JSONArray innerArr = response.getJSONArray(i);


                        String pairInside = innerArr.getString(0);
                        BigDecimal bidBD = new BigDecimal(innerArr.getString(1));
                        BigDecimal askBD = new BigDecimal(innerArr.getString(3));

                        bitfinexxBid[i] = BigDecimal.valueOf(Double.parseDouble(innerArr.getString(1)));
                        bitfinexAsk[i] = BigDecimal.valueOf(Double.parseDouble(innerArr.getString(3)));
                        bitfinexVolume[i] = BigDecimal.valueOf(Double.parseDouble(innerArr.getString(8)));
                        bitfinexPair[i] = innerArr.getString(0);


                        //Normalize data
                        String base = bitfinexPair[i].substring(0, 3);
                        String quote = bitfinexPair[i].substring(3, 6);
                        bitfinexPair[i] = base + "_" + quote;


                        Log.d("COUNTBITFIN", bitfinexPair[i]);


                        Log.d("BITFINEX", bitfinexPair[i] + " " + bitfinexxBid[i].toString() + " " + bitfinexAsk[i].toString() + " " + bitfinexVolume[i].toString());

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


                    for (int i = 0; i < metadataArr.length(); i++) {

                        JSONObject obj = metadataArr.getJSONObject(i);

                        bittrexBid[i] = BigDecimal.valueOf(Double.parseDouble(obj.getString("Bid")));
                        bittrexAsk[i] = BigDecimal.valueOf(Double.parseDouble(obj.getString("Ask")));
                        bittrexVolume[i] = BigDecimal.valueOf(Double.parseDouble(obj.getString("BaseVolume")));
                        bittrexPair[i] = obj.getString("MarketName");

                        //Normalize data
                        bittrexPair[i].replace("-", "_");

                        Log.d("BittrexSet", bittrexPair[i] + bittrexVolume[i].toString());


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

                            //                                        Log.d("Binance4Pair", binancePair[i]);

                        }


                        Log.d("BinanceSet", binancePair[i] + " " + binanceAsk[i].toString() + " " +
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


                    for (int i = 0; i < response.length(); i++) {

                        arrReq = response.getJSONObject(i);

                        okexPair[i] = arrReq.getString("instrument_id");
                        okexBid[i] = BigDecimal.valueOf(Double.parseDouble(arrReq.getString("bid")));
                        okexAsk[i] = BigDecimal.valueOf(Double.parseDouble(arrReq.getString("ask")));
                        okexVolume[i] = BigDecimal.valueOf(Double.parseDouble(arrReq.getString("base_volume_24h")));

                        //Normalize data
                        okexPair[i] = okexPair[i].replace("-", "_");

                        Log.d("OKEX", okexPair[i].toString() + " " + okexBid[i].toString() + " "
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
                        Log.d("KRAKEN set", krakenPair[krakenCountprice] + " " + krakenAsk[krakenCountprice].toString() + " " + krakenBid[krakenCountprice].toString());
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
                    Log.d("POLONIEX_set", poloniexPair[poloniexCounter] + " " + poloniexAsk[poloniexCounter].toString() + " " + poloniexBid[poloniexCounter].toString());

                    poloniexCounter++;
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(poloniexFeed);


      String[] pairTokens = concatenate(bitfinexPair, binancePair);

        Log.d("pairTokens", String.valueOf(pairTokens.length));

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

    public static void normalize(String str, int i) {
        switch (str) {

            case "QTUMETH":
                binancePair[i] = "QTUM_ETH";
                break;
            case "BTCUSDT":
                binancePair[i] = "BTC_USDT";
                break;
            case "ETHUSDT":
                binancePair[i] = "ETH_USDT";
                break;
            case "QTUMBTC":
                binancePair[i] = "QTUM_BTC";
                break;
            case "YOYOBTC":
                binancePair[i] = "YOYO_BTC";
                break;
            case "STRATBTC":
                binancePair[i] = "STRAT_BTC";
                break;
            case "STRATETH":
                binancePair[i] = "STRAT_ETH";
                break;
            case "SNGLSBTC":
                binancePair[i] = "SNGLS_BTC";
                break;
            case "SNGLSETH":
                binancePair[i] = "SNGLS_ETH";
                break;
            case "IOTABTC":
                binancePair[i] = "IOTA_BTC";
                break;
            case "IOTAETH":
                binancePair[i] = "IOTA_ETH";
                break;
            case "LINKBTC":
                binancePair[i] = "LINK_BTC";
                break;
            case "LINKETH":
                binancePair[i] = "LINK_ETH";
                break;
            case "SALTBTC":
                binancePair[i] = "SALT_BTC";
                break;
            case "SALTETH":
                binancePair[i] = "SALT_ETH";
                break;
            case "DASHBTC":
                binancePair[i] = "DASH_BTC";
                break;
            case "DASHETH":
                binancePair[i] = "DASH_ETH";
                break;
            case "POWRBTC":
                binancePair[i] = "POWR_BTC";
                break;
            case "POWRETH":
                binancePair[i] = "POWR_ETH";
                break;
            case "YOYOETH":
                binancePair[i] = "YOYO_ETH";
                break;
            case "STORJBTC":
                binancePair[i] = "STORJ_BTC";
                break;
            case "STORJETH":
                binancePair[i] = "STORJ_ETH";
                break;
            case "BNBUSDT":
                binancePair[i] = "BNB_USDT";
                break;
            case "YOYOBNB":
                binancePair[i] = "YOYO_BNB";
                break;
            case "POWRBNB":
                binancePair[i] = "POWR_BNB";
                break;
            case "NULSBNB":
                binancePair[i] = "NULS_BNB";
                break;
            case "NULSBTC":
                binancePair[i] = "NULS_BTC";
                break;
            case "NULSETH":
                binancePair[i] = "NULS_ETH";
                break;
            case "BCCUSDT":
                binancePair[i] = "BCC_USDT";
                break;
            case "BCPTBTC":
                binancePair[i] = "BCPT_BTC";
                break;
            case "BCPTETH":
                binancePair[i] = "BCPT_ETH";
                break;
            case "BCPTBNB":
                binancePair[i] = "BCPT_BNB";
                break;
            case "NEOUSDT":
                binancePair[i] = "NEO_USDT";
                break;
            case "FUELBTC":
                binancePair[i] = "FUEL_BTC";
                break;
            case "FUELETH":
                binancePair[i] = "FUEL_ETH";
                break;
            case "MANABTC":
                binancePair[i] = "MANA_BTC";
                break;
            case "MANAETH":
                binancePair[i] = "MANA_ETH";
                break;
            case "IOTABNB":
                binancePair[i] = "IOTA_BNB";
                break;
            case "LENDBTC":
                binancePair[i] = "LEND_BTC";
                break;
            case "LENDETH":
                binancePair[i] = "LEND_ETH";
                break;
            case "WABIBTC":
                binancePair[i] = "WABI_BTC";
                break;
            case "WABIETH":
                binancePair[i] = "WABI_ETH";
                break;
            case "WABIBNB":
                binancePair[i] = "WABI_BNB";
                break;
            case "LTCUSDT":
                binancePair[i] = "LTCU_SDT";
                break;
            case "WAVESBTC":
                binancePair[i] = "WAVES_BTC";
                break;
            case "WAVESETH":
                binancePair[i] = "WAVES_ETH";
                break;
            case "WAVESBNB":
                binancePair[i] = "WAVES_BNB";
                break;
            case "AIONBTC":
                binancePair[i] = "AION_BTC";
                break;
            case "AIONETH":
                binancePair[i] = "AION_ETH";
                break;
            case "AIONBNB":
                binancePair[i] = "AION_BNB";
                break;
            case "NEBLBTC":
                binancePair[i] = "NEBL_BTC";
                break;
            case "NEBLETH":
                binancePair[i] = "NEBL_ETH";
                break;
            case "NEBLBNB":
                binancePair[i] = "NEBL_BNB";
                break;
            case "WINGSBTC":
                binancePair[i] = "WINGS_BTC";
                break;
            case "WINGSETH":
                binancePair[i] = "WINGS_ETH";
                break;
            case "APPCBTC":
                binancePair[i] = "APPC_BTC";
                break;
            case "APPCETH":
                binancePair[i] = "APPC_ETH";
                break;
            case "APPCBNB":
                binancePair[i] = "APPC_BNB";
                break;
            case "VIBEBTC":
                binancePair[i] = "VIBE_BTC";
                break;
            case "VIBEETH":
                binancePair[i] = "VIBE_ETH";
                break;
            case "PIVXBTC":
                binancePair[i] = "PIVX_BTC";
                break;
            case "PIVXETH":
                binancePair[i] = "PIVX_ETH";
                break;
            case "PIVXBNB":
                binancePair[i] = "PIVX_BNB";
                break;
            case "IOSTBTC":
                binancePair[i] = "IOST_BTC";
                break;
            case "IOSTETH":
                binancePair[i] = "IOST_ETH";
                break;
            case "STEEMBTC":
                binancePair[i] = "STEEM_BTC";
                break;
            case "STEEMETH":
                binancePair[i] = "STEEM_ETH";
                break;
            case "STEEMBNB":
                binancePair[i] = "STEEM_BNB";
                break;
            case "NANOBTC":
                binancePair[i] = "NANO_BTC";
                break;
            case "NANOETH":
                binancePair[i] = "NANO_ETH";
                break;
            case "NANOBNB":
                binancePair[i] = "NANO_BNB";
                break;
            case "AEBTC":
                binancePair[i] = "AE_BTC";
                break;
            case "AEETH":
                binancePair[i] = "AE_ETH";
                break;
            case "AEBNB":
                binancePair[i] = "AE_BNB";
                break;
            case "NCASHBTC":
                binancePair[i] = "NCASH_BTC";
                break;
            case "NCASHETH":
                binancePair[i] = "NCASH_ETH";
                break;
            case "NCASHBNB":
                binancePair[i] = "NCASH_BNB";
                break;
            case "STORMBTC":
                binancePair[i] = "STORM_BTC";
                break;
            case "STORMETH":
                binancePair[i] = "STORM_ETH";
                break;
            case "STORMBNB":
                binancePair[i] = "STORM_BNB";
                break;
            case "QTUMUSDT":
                binancePair[i] = "QTUM_USDT";
                break;
            case "QTUMBNB":
                binancePair[i] = "QTUM_BNB";
                break;
            case "ADAUSDT":
                binancePair[i] = "ADA_USDT";
                break;
            case "LOOMBTC":
                binancePair[i] = "LOOM_BTC";
                break;
            case "LOOMETH":
                binancePair[i] = "LOOM_ETH";
                break;
            case "LOOMBNB":
                binancePair[i] = "LOOM_BNB";
                break;
            case "XRPUSDT":
                binancePair[i] = "XRP_USDT";
                break;
            case "BTCTUSD":
                binancePair[i] = "BTC_TUSD";
                break;
            case "ETHTUSD":
                binancePair[i] = "ETH_TUSD";
                break;
            case "EOSUSDT":
                binancePair[i] = "EOS_USDT";
                break;
            case "THETABTC":
                binancePair[i] = "THETA_BTC";
                break;
            case "THETAETH":
                binancePair[i] = "THETA_ETH";
                break;
            case "THETABNB":
                binancePair[i] = "THETA_BNB";
                break;
            case "TUSDUSDT":
                binancePair[i] = "TUSD_USDT";
                break;
            case "IOTAUSDT":
                binancePair[i] = "IOTA_USDT";
                break;
            case "XLMUSDT":
                binancePair[i] = "XLM_USDT";
                break;
            case "IOTXBTC":
                binancePair[i] = "IOTX_BTC";
                break;
            case "IOTXETH":
                binancePair[i] = "IOTX_ETH";
                break;
            case "DATABTC":
                binancePair[i] = "DATA_BTC";
                break;
            case "DATAETH":
                binancePair[i] = "DATA_ETH";
                break;
            case "ONTUSDT":
                binancePair[i] = "ON_TUSDT";
                break;
            case "TRXUSDT":
                binancePair[i] = "TRX_USDT";
                break;
            case "ETCUSDT":
                binancePair[i] = "ETC_USDT";
                break;
            case "ICXUSDT":
                binancePair[i] = "ICX_USDT";
                break;
            case "NPXSBTC":
                binancePair[i] = "NPXS_BTC";
                break;
            case "NPXSETH":
                binancePair[i] = "NPXS_ETH";
                break;
            case "VENUSDT":
                binancePair[i] = "VEN_USDT";
                break;
            case "DENTBTC":
                binancePair[i] = "DENT_BTC";
                break;
            case "DENTETH":
                binancePair[i] = "DENT_ETH";
                break;
            case "ARDRBTC":
                binancePair[i] = "ARDR_BTC";
                break;
            case "ARDRETH":
                binancePair[i] = "ARDR_ETH";
                break;
            case "ARDRBNB":
                binancePair[i] = "ARDR_BNB";
                break;
            case "NULSUSDT":
                binancePair[i] = "NULSU_SDT";
                break;
            case "VETUSDT":
                binancePair[i] = "VET_USDT";
                break;
            case "DOCKBTC":
                binancePair[i] = "DOCK_BTC";
                break;
            case "DOCKETH":
                binancePair[i] = "DOCK_ETH";
                break;
            case "POLYBTC":
                binancePair[i] = "POLY_BTC";
                break;
            case "POLYBNB":
                binancePair[i] = "POLY_BNB";
                break;
            case "HCBTC":
                binancePair[i] = "HC_BTC";
                break;
            case "HCETH":
                binancePair[i] = "HC_ETH";
                break;
            case "GOBTC":
                binancePair[i] = "GO_BTC";
                break;
            case "GOBNB":
                binancePair[i] = "GO_BNB";
                break;
            case "PAXUSDT":
                binancePair[i] = "PAX_USDT";
                break;
            case "MITHBTC":
                binancePair[i] = "MITH_BTC";
                break;
            case "MITHBNB":
                binancePair[i] = "MITH_BNB";
                break;
            case "BCHABCBTC":
                binancePair[i] = "BCHABC_BTC";
                break;
            case "BCHSVUSDT":
                binancePair[i] = "BCHSV_USDT";
                break;
            case "BCHABCUSDT":
                binancePair[i] = "BCHABC_USDT";
                break;
            case "BNBTUSD":
                binancePair[i] = "BNB_TUSD";
                break;
            case "XRPTUSD":
                binancePair[i] = "XRP_TUSD";
                break;
            case "EOSTUSD":
                binancePair[i] = "EOS_TUSD";
                break;
            case "XLMTUSD":
                binancePair[i] = "XLM_TUSD";
                break;
            case "BNBUSDC":
                binancePair[i] = "BNB_USDC";
                break;
            case "BTCUSDC":
                binancePair[i] = "BTC_USDC";
                break;
            case "ETHUSDC":
                binancePair[i] = "ETH_USDC";
                break;
            case "XRPUSDC":
                binancePair[i] = "XRP_USDC";
                break;
            case "EOSUSDC":
                binancePair[i] = "EOS_USDC";
                break;
            case "XLMUSDC":
                binancePair[i] = "XLM_USDC";
                break;
            case "USDCUSDT":
                binancePair[i] = "USD_CUSDT";
                break;
            case "ADATUSD":
                binancePair[i] = "ADA_TUSD";
                break;
            case "TRXTUSD":
                binancePair[i] = "TRX_TUSD";
                break;
            case "NEOTUSD":
                binancePair[i] = "NEO_TUSD";
                break;
            case "PAXTUSD":
                binancePair[i] = "PAX_TUSD";
                break;
            case "USDCTUSD":
                binancePair[i] = "USDC_TUSD";
                break;
            case "USDCPAX":
                binancePair[i] = "USD_CPAX";
                break;
            case "LINKUSDT":
                binancePair[i] = "LINK_USDT";
                break;
            case "LINKTUSD":
                binancePair[i] = "LINK_TUSD";
                break;
            case "LINKPAX":
                binancePair[i] = "LINK_PAX";
                break;
            case "LINKUSDC":
                binancePair[i] = "LINK_USDC";
                break;
            case "WAVESUSDT":
                binancePair[i] = "WAVES_USDT";
                break;
            case "WAVESTUSD":
                binancePair[i] = "WAVES_TUSD";
                break;
            case "WAVESPAX":
                binancePair[i] = "WAVES_PAX";
                break;
            case "WAVESUSDC":
                binancePair[i] = "WAVES_USDC";
                break;
            case "BCHABCTUSD":
                binancePair[i] = "BCHABC_TUSD";
                break;
            case "BCHABCPAX":
                binancePair[i] = "BCHABC_PAX";
                break;
            case "BCHABCUSDC":
                binancePair[i] = "BCHABC_USDC";
                break;
            case "LTCTUSD":
                binancePair[i] = "LTC_TUSD";
                break;
            case "LTCUSDC":
                binancePair[i] = "LTC_USDC";
                break;
            case "TRXUSDC":
                binancePair[i] = "TRX_USDC";
                break;
            case "BTTUSDT":
                binancePair[i] = "BTT_USDT";
                break;
            case "BNBUSDS":
                binancePair[i] = "BNB_USDS";
                break;
            case "BTCUSDS":
                binancePair[i] = "BTC_USDS";
                break;
            case "USDSUSDT":
                binancePair[i] = "USDS_USDT";
                break;
            case "USDSPAX":
                binancePair[i] = "USDS_PAX";
                break;
            case "USDSTUSD":
                binancePair[i] = "USDS_TUSD";
                break;
            case "USDSUSDC":
                binancePair[i] = "USDS_USDC";
                break;
            case "BTTTUSD":
                binancePair[i] = "BTT_TUSD";
                break;
            case "BTTUSDC":
                binancePair[i] = "BTT_USDC";
                break;
            case "ONGUSDT":
                binancePair[i] = "ONG_USDT";
                break;
            case "HOTUSDT":
                binancePair[i] = "HOT_USDT";
                break;
            case "ZILUSDT":
                binancePair[i] = "ZIL_USDT";
                break;
            case "ZRXUSDT":
                binancePair[i] = "ZRX_USDT";
                break;
            case "FETUSDT":
                binancePair[i] = "FET_USDT";
                break;
            case "BATUSDT":
                binancePair[i] = "BAT_USDT";
                break;
            case "XMRUSDT":
                binancePair[i] = "XMR_USDT";
                break;
            case "ZECUSDT":
                binancePair[i] = "ZEC_USDT";
                break;
            case "ZECTUSD":
                binancePair[i] = "ZEC_TUSD";
                break;
            case "ZECUSDC":
                binancePair[i] = "ZEC_USDC";
                break;
            case "IOSTBNB":
                binancePair[i] = "IOST_BNB";
                break;
            case "IOSTUSDT":
                binancePair[i] = "IOST_USDT";
                break;
            case "CELRBNB":
                binancePair[i] = "CELR_BNB";
                break;
            case "CELRBTC":
                binancePair[i] = "CELR_BTC";
                break;
            case "CELRUSDT":
                binancePair[i] = "CELR_USDT";
                break;
            case "ADAUSDC":
                binancePair[i] = "ADA_USDC";
                break;
            case "NEOPAX":
                binancePair[i] = "NEO_PAX";
                break;
            case "NEOUSDC":
                binancePair[i] = "NEO_USDC";
                break;
            case "DASHBNB":
                binancePair[i] = "DASH_BNB";
                break;
            case "DASHUSDT":
                binancePair[i] = "DASH_USDT";
                break;
            case "NANOUSDT":
                binancePair[i] = "NANO_USDT";
                break;
            case "OMGUSDT":
                binancePair[i] = "OMG_USDT";
                break;
            case "THETAUSDT":
                binancePair[i] = "THETA_USDT";
                break;
            case "ENJUSDT":
                binancePair[i] = "ENJ_USDT";
                break;
            case "MITHUSDT":
                binancePair[i] = "MITH_USDT";
                break;
            case "MATICBNB":
                binancePair[i] = "MATIC_BNB";
                break;
            case "MATICBTC":
                binancePair[i] = "MATIC_BTC";
                break;
            case "MATICUSDT":
                binancePair[i] = "MATIC_USDT";
                break;
            case "ATOMBNB":
                binancePair[i] = "ATOM_BNB";
                break;
            case "ATOMBTC":
                binancePair[i] = "ATOM_BTC";
                break;
            case "ATOMUSDT":
                binancePair[i] = "ATOM_USDT";
                break;
            case "ATOMUSDC":
                binancePair[i] = "ATOM_USDC";
                break;
            case "ATOMPAX":
                binancePair[i] = "ATOM_PAX";
                break;
            case "ATOMTUSD":
                binancePair[i] = "ATOM_TUSD";
                break;
            case "ETCUSDC":
                binancePair[i] = "ETC_USDC";
                break;
            case "ETCTUSD":
                binancePair[i] = "ETC_TUSD";
                break;
            case "BATUSDC":
                binancePair[i] = "BAT_USDC";
                break;
            case "BATTUSD":
                binancePair[i] = "BAT_TUSD";
                break;
            case "PHBUSDC":
                binancePair[i] = "PHB_USDC";
                break;
            case "PHBTUSD":
                binancePair[i] = "PHB_TUSD";
                break;
            case "TFUELBNB":
                binancePair[i] = "TFUEL_BNB";
                break;
            case "TFUELBTC":
                binancePair[i] = "TFUEL_BTC";
                break;
            case "TFUELUSDT":
                binancePair[i] = "TFUEL_USDT";
                break;
            case "TFUELUSDC":
                binancePair[i] = "TFUEL_USDC";
                break;
            case "TFUELTUSD":
                binancePair[i] = "TFUEL_TUSD";
                break;
            case "TFUELPAX":
                binancePair[i] = "TFUEL_PAX";
                break;
            case "ONEUSDT":
                binancePair[i] = "ONE_USDT";
                break;
            case "ONETUSD":
                binancePair[i] = "ONET_USD";
                break;
            case "ONEUSDC":
                binancePair[i] = "ONE_USDC";
                break;
            case "FTMUSDT":
                binancePair[i] = "FTM_USDT";
                break;
            case "FTMTUSD":
                binancePair[i] = "FTM_TUSD";
                break;
            case "FTMUSDC":
                binancePair[i] = "FTM_USDC";
                break;
            case "BTCBBTC":
                binancePair[i] = "BTCB_BTC";
                break;
            case "BCPTTUSD":
                binancePair[i] = "BCPT_TUSD";
                break;
            case "BCPTPAX":
                binancePair[i] = "BCPT_PAX";
                break;
            case "BCPTUSDC":
                binancePair[i] = "BCPT_USDC";
                break;
            case "ALGOBNB":
                binancePair[i] = "ALGO_BNB";
                break;
            case "ALGOBTC":
                binancePair[i] = "ALGO_BTC";
                break;
            case "ALGOUSDT":
                binancePair[i] = "ALGO_USDT";
                break;
            case "ALGOTUSD":
                binancePair[i] = "ALGO_TUSD";
                break;
            case "ALGOPAX":
                binancePair[i] = "ALGO_PAX";
                break;
            case "ALGOUSDC":
                binancePair[i] = "ALGO_USDC";
                break;
            case "USDSBUSDT":
                binancePair[i] = "USDSB_USDT";
                break;
            case "USDSBUSDS":
                binancePair[i] = "USDSB_USDS";
                break;
            case "GTOUSDT":
                binancePair[i] = "GTO_USDT";
                break;
            case "GTOTUSD":
                binancePair[i] = "GTOT_USD";
                break;
            case "GTOUSDC":
                binancePair[i] = "GTO_USDC";
                break;
            case "ERDUSDT":
                binancePair[i] = "ERD_USDT";
                break;
            case "ERDUSDC":
                binancePair[i] = "ERD_USDC";
                break;
            case "DOGEBNB":
                binancePair[i] = "DOGE_BNB";
                break;
            case "DOGEBTC":
                binancePair[i] = "DOGE_BTC";
                break;
            case "DOGEUSDT":
                binancePair[i] = "DOGE_USDT";
                break;
            case "DOGEPAX":
                binancePair[i] = "DOGE_PAX";
                break;
            case "DOGEUSDC":
                binancePair[i] = "DOGE_USDC";
                break;
            case "DUSKBNB":
                binancePair[i] = "DUSK_BNB";
                break;
            case "DUSKBTC":
                binancePair[i] = "DUSK_BTC";
                break;
            case "DUSKUSDT":
                binancePair[i] = "DUSK_USDT";
                break;
            case "DUSKUSDC":
                binancePair[i] = "DUSK_USDC";
                break;
            case "DUSKPAX":
                binancePair[i] = "DUSK_PAX";
                break;
            case "BGBPUSDC":
                binancePair[i] = "BGBP_USDC";
                break;
            case "ANKRBNB":
                binancePair[i] = "ANKR_BNB";
                break;
            case "ANKRBTC":
                binancePair[i] = "ANKR_BTC";
                break;
            case "ANKRUSDT":
                binancePair[i] = "ANKR_USDT";
                break;
            case "ANKRTUSD":
                binancePair[i] = "ANKR_TUSD";
                break;
            case "ANKRPAX":
                binancePair[i] = "ANKR_PAX";
                break;
            case "ANKRUSDC":
                binancePair[i] = "ANKR_USDC";
                break;
            case "ONTUSDC":
                binancePair[i] = "ONT_USDC";
                break;
            case "WINUSDT":
                binancePair[i] = "WIN_USDT";
                break;
            case "WINUSDC":
                binancePair[i] = "WIN_USDC";
                break;
            case "COSUSDT":
                binancePair[i] = "COS_USDT";
                break;
            case "TUSDBTUSD":
                binancePair[i] = "TUSDB_TUSD";
                break;
            case "NPXSUSDT":
                binancePair[i] = "NPXS_USDT";
                break;
            case "NPXSUSDC":
                binancePair[i] = "NPXS_USDC";
                break;
            case "COCOSBNB":
                binancePair[i] = "COCOS_BNB";
                break;
            case "COCOSBTC":
                binancePair[i] = "COCOS_BTC";
                break;
            case "COCOSUSDT":
                binancePair[i] = "COCOS_USDT";
                break;
            case "MTLUSDT":
                binancePair[i] = "MTL_USDT";
                break;
            case "TOMOBNB":
                binancePair[i] = "TOMO_BNB";
                break;
            case "TOMOBTC":
                binancePair[i] = "TOMO_BTC";
                break;
            case "TOMOUSDT":
                binancePair[i] = "TOMO_USDT";
                break;
            case "TOMOUSDC":
                binancePair[i] = "TOMO_USDC";
                break;
            case "PERLBNB":
                binancePair[i] = "PERL_BNB";
                break;
            case "PERLBTC":
                binancePair[i] = "PERL_BTC";
                break;
            case "PERLUSDC":
                binancePair[i] = "PERL_USDC";
                break;
            case "PERLUSDT":
                binancePair[i] = "PERL_USDT";
                break;
            case "DENTUSDT":
                binancePair[i] = "DENT_USDT";
                break;
            case "MFTUSDT":
                binancePair[i] = "MFT_USDT";
                break;
            case "KEYUSDT":
                binancePair[i] = "KEY_USDT";
                break;
            case "STORMUSDT":
                binancePair[i] = "STORM_USDT";
                break;
            case "DOCKUSDT":
                binancePair[i] = "DOCK_USDT";
                break;
            case "WANUSDT":
                binancePair[i] = "WAN_USDT";
                break;
            case "FUNUSDT":
                binancePair[i] = "FUN_USDT";
                break;
            case "CVCUSDT":
                binancePair[i] = "CVC_USDT";
                break;
        }


    }

}