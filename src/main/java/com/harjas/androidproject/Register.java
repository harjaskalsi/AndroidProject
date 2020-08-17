package com.harjas.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Register extends AppCompatActivity {
    TextInputLayout e1,e2,e3,e4;
    Button b;
    String name,password,contact,email;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e1=findViewById(R.id.username);
        e2=findViewById(R.id.password);
        e3=findViewById(R.id.email);
        e4=findViewById(R.id.contact);
        b=findViewById(R.id.register);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             name=e1.getEditText().getText().toString();
             password=e2.getEditText().getText().toString();
             email=e3.getEditText().getText().toString();
             contact=e4.getEditText().getText().toString();



             //new WebRegister().execute();

                checkDataEntered();

            }
        });


    }


    boolean isEmpty(TextInputLayout text)
    {
        //CharSequence is a readable sequence of characters
        CharSequence str=text.getEditText().getText().toString();

        //TextUtils--> provides set of utility functions to do operations on string
        //all the functiojns TextUtils only returns boolean value
        //isEmpty-->This function is inbuilt function in TextUtils not our maded function

        return TextUtils.isEmpty(str);


    }

    boolean isEmail(TextInputLayout text)
    {
        CharSequence email=text.getEditText().getText().toString();
        return(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isValidPhoneNumber(TextInputLayout text)

    {
        String phone=text.getEditText().getText().toString();

        if(!TextUtils.isEmpty(phone))
            return(Patterns.PHONE.matcher(phone).matches() && phone.length()==10);
        else
            return false;
    }


    public void checkDataEntered()
    {
        if(isEmpty(e1))
        {
            Toast.makeText(getApplicationContext(), "Username can't be empty", Toast.LENGTH_SHORT).show();
        }

        else if(isEmpty(e2))
        {
            e2.setError("Enter Password");
        }
        else if (isEmail(e3)==false)
        {
            e3.setError("Enter Valid Email");
        }

        else if(isValidPhoneNumber(e4)==false)
        {
            e4.setError("Enter Valid Phone number");
        }
        else
        {
            new WebRegister().execute();
        }
    }
    public class WebRegister extends AsyncTask<Void,Void,String>
    {
        String result="";


        @Override
        protected void onPreExecute() {
            e2.setError(null);
            e3.setError(null);
            e4.setError(null);
            progressBar=findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                Log.i("checkdata",name+" "+password+" "+email+" "+contact);
                URL url = new URL("\n" + "http://13.232.142.121/android/index.php?username=" + name + "&password=" + password + "&email=" + email + "&contact=" + contact);
                //URL is used to fetch data from any server

                InputStream stream=url.openConnection().getInputStream();
                //stream means sequence of bytes
                InputStreamReader ir=new InputStreamReader(stream);
                BufferedReader br=new BufferedReader(ir);
                String line="";
                while ((line=br.readLine())!=null)
                {
                    result=result+line+"\n";
                }
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace(); }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.setVisibility(View.INVISIBLE);

            String data=s.trim();//trim removes white spaces
            if(data.matches("true")){
                Toast.makeText(getApplicationContext(), "User Registeration Successful!!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "User Registeration Failed!!", Toast.LENGTH_SHORT).show();

            }
            e1.getEditText().setText("");
            e2.getEditText().setText("");
            e3.getEditText().setText("");
            e4.getEditText().setText("");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent login=new Intent(getApplicationContext(),Login.class);
                    startActivity(login);
                    finish();
                }
            },1000);


        }
    }


}
