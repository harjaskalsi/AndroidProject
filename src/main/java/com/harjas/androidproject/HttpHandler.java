package com.harjas.androidproject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class HttpHandler {
    //HttpHandler is a class used to process http requests
    private static final String TAG=HttpHandler.class.getName();
    public HttpHandler(){}

    public String makeServiceCall(String reqUrl)
    {
        String response = null;

        try {
            URL url=new URL(reqUrl);
            //it is used to make a single request-->HttpURLConnection
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //read the response
            InputStream in=new BufferedInputStream(conn.getInputStream());
           response= convertStreamToString(in);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String convertStreamToString(InputStream in) {
        BufferedReader reader=new BufferedReader(new InputStreamReader(in));
        //String and StringBuilder Diffrence
        //String is an inmutable[unmodifiable] class
        //StrinBulder is mutable classes new memory nhi baanegi purani waali memory main hi kaam ho jayega
        StringBuilder sb=new StringBuilder();
        String line;
        try
        {
            while ((line=reader.readLine())!=null)
            {
             sb.append(line).append('\n');
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
return sb.toString();
    }

}
