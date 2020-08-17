package com.harjas.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class UserData extends AppCompatActivity {


   TextView t1,t2,t3;
   ImageView i1;
    String name , email , contact,image;
    private static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        t1=findViewById(R.id.tv_name);
        t2=findViewById(R.id.tv_email);
        t3=findViewById(R.id.tv_contact);
        i1=findViewById(R.id.imagepro);

        Intent intent=getIntent();
        String email1=intent.getStringExtra("EMAIL");
        url="http://13.232.142.121/android/jsondata.php?email="+email1;
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
                    image= data.getString("Image");

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
            Picasso.get().load(image).into(i1,new Callback(){

                @Override
                public void onSuccess() {
                    Bitmap imageBitmap = ((BitmapDrawable) i1.getDrawable()).getBitmap();
                    RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                    imageDrawable.setCircular(true);
                    imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                    i1.setImageDrawable(imageDrawable);
                }

                @Override
                public void onError(Exception e) {
                    i1.setImageResource(R.drawable.hackvedalogo1);

                }
            });

            super.onPostExecute(s);
        }
        }
    }
