package com.example.psi_arb;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONcryptoParser {

    private static JSONObject getJSONObject(String keyName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject(keyName);
    }

    private static String getString(String keyName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString(keyName);
    }

    private static boolean getBool(String keyName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getBoolean(keyName);
    }

    private static int getInt(String keyName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt(keyName);
    }

    private static double getDouble(String keyName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getDouble(keyName);
    }

    private static float getFlaot(String keyName, JSONObject jsonObject) throws JSONException {
        return (float) jsonObject.getDouble(keyName);
    }


    // This is where the magic happens************************
    //
    public static PairCache getExchangeRates(String url, String myPair) throws JSONException {

        PairCache exchangeRates = new PairCache();
        JSONObject exchangeRateJsonObject = new JSONObject(url);
        JSONArray resultJsonArray = exchangeRateJsonObject.getJSONArray("result");
        JSONObject pairObject = resultJsonArray.getJSONObject(125);
      /*  for(int i =0; i < 300; i++){
            JSONObject pairObject = resultJsonArray.getJSONObject(i);
            if(pairObject.get("MarketName").equals(myPair)){
                exchangeRates.setBid(getString("Bid", pairObject));
                exchangeRates.setAsk(getString("Ask", pairObject));
                exchangeRates.setPair(getString("MarketName", pairObject));
                System.out.println("**************** PARSED SUCCESSFULLY at " + i + getString("MarketName",pairObject));


            }else{  */
                exchangeRates.setBid(getString("Bid", pairObject));
                exchangeRates.setAsk(getString("Ask", pairObject));
                exchangeRates.setPair(getString("MarketName", pairObject));
                System.out.println("**************** parse " + 125 + getString("MarketName",pairObject));

            //}
        //}


        return exchangeRates;
    }

}
