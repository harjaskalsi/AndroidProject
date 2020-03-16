package com.harjas.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;



public class Login extends AppCompatActivity implements View.OnClickListener{
    Button bt1,bt2;
    TextInputLayout e1,e2;
    String name,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        bt1=findViewById(R.id.signUp);
        bt1.setOnClickListener(this);
        e1=findViewById(R.id.username);
        e2=findViewById(R.id.password);
        bt2=findViewById(R.id.login);
        bt2.setOnClickListener(this);


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
            e2.setError(null);
            Toast.makeText(getApplicationContext(), "login Successful", Toast.LENGTH_SHORT).show();
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
                name=e1.getEditText().getText().toString();
                pass=e2.getEditText().getText().toString();
                checkDataEntered();
                break;

        }
    }
}

