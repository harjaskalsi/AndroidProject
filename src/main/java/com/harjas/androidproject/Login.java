package com.harjas.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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


public class Login extends AppCompatActivity implements View.OnClickListener{
    Button bt1,bt2,bt3;
    EditText e1,e2;
    String name,pass;
    ProgressBar progressBar;
    CheckBox ch;
    private SharedPreferences mPrefs;
    private static final String PREFS_NAME="PrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        bt1=findViewById(R.id.signUp);
        bt1.setOnClickListener(this);
        e1=findViewById(R.id.username);
        e2=findViewById(R.id.password);
        ch=findViewById(R.id.checkbox);
        bt2=findViewById(R.id.login);
        mPrefs=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
      getPreferencesData();
        bt2.setOnClickListener(this);


    }

    private void getPreferencesData() {
        SharedPreferences sp=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if(sp.contains("pref_name"))
        {
            String u=sp.getString("pref_name","not found");
            e1.setText(u);

        }

        if(sp.contains("pref_pass"))
        {
            String p=sp.getString("pref_pass","not found");
            e2.setText(p);

        }
        if(sp.contains("pref_check"))
        {
            Boolean b=sp.getBoolean("pref_check",false);
            ch.setChecked(b);

        }


    }

    boolean isEmpty(EditText text)
    {
        //CharSequence is a readable sequence of characters
        CharSequence str=text.getText().toString();

        //TextUtils--> provides set of utility functions to do operations on string
        //all the functiojns TextUtils only returns boolean value
        //isEmpty-->This function is inbuilt function in TextUtils not our maded function

        return TextUtils.isEmpty(str);



    }

    public void  checkDataEntered()
    {
        if(isEmpty(e1))
        {
            Toast.makeText(getApplicationContext(), "Username can't be empty", Toast.LENGTH_SHORT).show();

        }

        else if(isEmpty(e2))
        {
            e2.setError("Enter Password");

        }

        else {
            new LoginDb().execute();
        }
    }

    public class LoginDb extends AsyncTask<Void,Void,String>
    {  String result="";

        @Override
        protected void onPreExecute() {
            e2.setError(null);
            progressBar=findViewById(R.id.progressBar1);
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url=new URL("http://13.232.142.121/android/login.php?email="+name+"&pswd="+pass);
                InputStream stream=url.openConnection().getInputStream();
                InputStreamReader ir=new InputStreamReader(stream);
                BufferedReader br=new BufferedReader(ir);
                String line="";
                while((line=br.readLine())!=null)
                {
                    result=result + line + "\n";
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            String data =s.trim();
            if(data.matches("true"))
            {
                  if(ch.isChecked())
                  {
                      Boolean boolIsChecked=ch.isChecked();
                      SharedPreferences.Editor editor=mPrefs.edit();
                      editor.putString("pref_name",name);
                      editor.putString("pref_pass",pass);
                      editor.putBoolean("pref_check",boolIsChecked);
                      editor.apply();
                      Toast.makeText(getApplicationContext(), "Settings have been saved", Toast.LENGTH_SHORT).show();
                  }else {
                      mPrefs.edit().clear().apply();
                  }


                Toast.makeText(getApplicationContext(), "VALID USER", Toast.LENGTH_SHORT).show();
                Intent log=new Intent(getApplicationContext(),UserData.class);
                log.putExtra("EMAIL",name);
                startActivity(log);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "INVALID USER", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
           case R.id.signUp:
               Intent sign =new Intent(getApplicationContext(),Register.class);
            startActivity(sign);
            break;

            case R.id.login:
                name=e1.getText().toString();
                pass=e2.getText().toString();
                checkDataEntered();
                break;

        }
    }
}

