package com.example.ducklabandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    Connection con;

    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String passwordConfirm;

    EditText emailET;
    EditText firstNameET;
    EditText lastNameET;
    EditText userNameET;
    EditText passwordET;
    EditText passwordConfirmET;

    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("pass");

        emailET = findViewById(R.id.emailInput);
        firstNameET = findViewById(R.id.firstInput);
        lastNameET = findViewById(R.id.lastIn);
        userNameET = findViewById(R.id.userName);
        passwordET = findViewById(R.id.password1);
        passwordConfirmET = findViewById(R.id.password2);
        registerButton = findViewById(R.id.button);
        emailET.setText(email);

        ConnectionHelper connectionHelper = new ConnectionHelper();
        con = connectionHelper.connectionclass();

        onRegisterPress();
    }
    public void onRegisterPress() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailET.getText().toString();
                firstName = firstNameET.getText().toString();
                lastName = lastNameET.getText().toString();
                userName = userNameET.getText().toString();
                password = passwordET.getText().toString();
                passwordConfirm = passwordConfirmET.getText().toString();
                Log.d(TAG, "onClick: email="+email);
                Log.d(TAG, "onClick: firstName="+firstName);
                Log.d(TAG, "onClick: lastName="+lastName);
                Log.d(TAG, "onClick: userName="+userName);
                Log.d(TAG, "onClick: password="+password);
                Log.d(TAG, "onClick: passwordConfirm="+passwordConfirm);

                if(!password.equals(passwordConfirm)){
                    Toast.makeText(getApplicationContext(),"Passwords must match. Try again.",Toast.LENGTH_LONG).show();
                }else if(email == null){
                    Toast.makeText(getApplicationContext(),"All fields must be filled out. Fill out email and try again.",Toast.LENGTH_LONG).show();
                }else if(firstName == null){
                    Toast.makeText(getApplicationContext(),"All fields must be filled out. Fill out first name and try again.",Toast.LENGTH_LONG).show();
                }else if(lastName == null){
                    Toast.makeText(getApplicationContext(),"All fields must be filled out. Fill out last name and try again.",Toast.LENGTH_LONG).show();
                }else if(userName == null){
                    Toast.makeText(getApplicationContext(),"All fields must be filled out. Fill out userName and try again.",Toast.LENGTH_LONG).show();
                }else if(password == null){
                    Toast.makeText(getApplicationContext(),"All fields must be filled out. Fill out password and try again.",Toast.LENGTH_LONG).show();
                }else {
                    String statement = "insert into [User] ([firstName],[lastName],[username],[password],[email],[active]) values ('" + firstName + "', '" + lastName + "', '" + userName + "', '" + password + "', '" + email + "', '1')";
                    try {
                        Statement stat = con.createStatement();
                        int row = stat.executeUpdate(statement);
                        Log.d(TAG, "onClick: row=" + row);
                        if (row != 0) {
                            //LOGIN SUCCESSFUL
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("pass", password);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        Log.e("Error Registering", e.getMessage());
                    }
                }
            }
        });
    }
}
