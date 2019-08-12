package com.example.psi_arb;

//This class will connect to the server and download the data

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPCrpytoClient {

    private static String BASE_URL = "https://api.bitfinex.com/v1/pubticker/ethusd";


    public String getData(){

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        System.out.println("WOAJTOAEJTEAOTHEAOTJAEOTJ***************");

        try{

            httpURLConnection = (HttpURLConnection) (new URL(BASE_URL)).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();


            //Read the Response
            StringBuffer stringBuffer = new StringBuffer();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine())!=null){
                stringBuffer.append(line + "\n");
            }

            inputStream.close();
            httpURLConnection.disconnect();

            return stringBuffer.toString();

        }catch(Throwable t) {
            t.printStackTrace();
        }
        finally{
            try{
                inputStream.close();
                httpURLConnection.disconnect();
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
        return null;
    }


}
