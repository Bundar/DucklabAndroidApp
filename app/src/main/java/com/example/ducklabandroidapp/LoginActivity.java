package com.example.ducklabandroidapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button signInButton;
    Button registerButton;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.signin);
        registerButton = (Button)findViewById(R.id.register);

        ConnectionHelper connectionHelper = new ConnectionHelper();
        con = connectionHelper.connectionclass();

        onSignInPress();
        onRegisterPress();
    }

    public void onSignInPress() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailEntered = email.getText().toString();
                String passwordEntered = password.getText().toString();
                //check db
                Log.d("email=", emailEntered);
                Log.d("password=", passwordEntered);
                String query = "select password from [User] where email = '" + emailEntered + "' and password = '" + passwordEntered + "'";
                try {
                    Statement stat = con.createStatement();
                    ResultSet rs = stat.executeQuery(query);
                    if (rs.next()) {
                        //LOGIN SUCCESSFUL
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("email", emailEntered);
                        startActivity(intent);
                    } else {
                        //User does not exist or wrong info
                        Toast.makeText(getApplicationContext(),"User Credentials Are Incorrect. Try Again.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("Error Logging in", e.getMessage());
                }

            }
        });
    }
    public void onRegisterPress() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailEntered = email.getText().toString();
                String passwordEntered = password.getText().toString();
                //check db
                Log.d("email=", emailEntered);
                Log.d("password=", passwordEntered);
                String query = "select password from [User] where email = '" + emailEntered + "' and password = '" + passwordEntered + "'";
                try {
                    Statement stat = con.createStatement();
                    ResultSet rs = stat.executeQuery(query);
                    if (!rs.next()) {
                        //LOGIN SUCCESSFUL
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        intent.putExtra("email", emailEntered);
                        intent.putExtra("pass", passwordEntered);
                        startActivity(intent);
                    } else {
                        //User does not exist or wrong info
                        Toast.makeText(getApplicationContext(),"User Credentials Already Exist.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("Error Logging in", e.getMessage());
                }

            }
        });
    }
}
