package com.harjas.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class UserData extends AppCompatActivity {


   TextView t1,t2,t3;
    String name , email , contact;
    private static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        t1=findViewById(R.id.tv_name);
        t2=findViewById(R.id.tv_email);
        t3=findViewById(R.id.tv_contact);

        Intent intent=getIntent();
        String email1=intent.getStringExtra("EMAIL");
        url="http://18.224.135.157/android/json.php?email="+email1;
        new LoadUserData().execute();

    }
    public class LoadUserData extends AsyncTask<Void,Void,String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONObject data = jsonObject.getJSONObject("Result");
                    name = data.getString("Name");
                    email = data.getString("Email");
                    contact = data.getString("Contact");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
                return null;
            }


        @Override
        protected void onPostExecute(String s) {
            t1.setText(name);
            t2.setText(email);
            t3.setText(contact);
            super.onPostExecute(s);
        }
        }
    }
