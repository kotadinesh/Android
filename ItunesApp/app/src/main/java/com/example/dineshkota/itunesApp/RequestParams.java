package com.example.dineshkota.itunesApp;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by dineshkota on 10/7/17.
 */

public class RequestParams {
    String method,baseurl;
    HashMap<String,String> params = new HashMap<String,String>();


    public RequestParams(String method,String baseurl){
        super();
        this.method=method;
        this.baseurl=baseurl;
    }

    public void addParams(String key, String value){
        params.put(key,value);
    }



    public String getEncodedParams(){
        StringBuilder sb = new StringBuilder();

        String value = null;
        try {
            value = URLEncoder.encode(params.get("track"),"UTF-8");
            if(sb.length()>0){
                sb.append("&");
            }
            sb.append("track"+"="+value);

            value = URLEncoder.encode(params.get("api_key"),"UTF-8");
            if(sb.length()>0){
                sb.append("&");
            }
            sb.append("api_key"+"="+value);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        return sb.toString();

    }

    public String getEncodedUrl(){
        Log.d("tfi"," "+this.baseurl+"?"+getEncodedParams());
        return this.baseurl+getEncodedParams();
    }

    public HttpURLConnection setupConnection() throws IOException {
        if(method.equals("GET")){
            URL url = new URL(getEncodedUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            return con;
        }
        return null;
    }
}