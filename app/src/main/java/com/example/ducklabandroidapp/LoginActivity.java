package com.example.ducklabandroidapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button signInButton;

    Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.signin);

        con = connectionclass();
        onButtonPress();

    }
    public void onButtonPress(){
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailEntered = email.getText().toString();
                String passwordEntered = password.getText().toString();
                //check db
                String query = "select password from User where email = "+emailEntered+" and password = "+ passwordEntered;
                try{
                    Statement stat = con.createStatement();
                    ResultSet rs = stat.executeQuery(query);
                    if(rs.next()){
                        //LOGIN SUCCESSFUL
                        startActivity(new Intent(LoginActivity.this, UserProfile.class));
                    }
                    else{
                        //User does not exist or wrong info
                    }
                }
                catch (Exception e){
                    Log.e("Error Logging in: ", e.getMessage());
                }

            }
        });
    }

    public Connection connectionclass(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = "jdbc:jtds:sqlserver://ducklabdata.database.windows.net:1433;DatabaseName=ducklabdb;user=ducklab@ducklabdata;password={Hiss4212};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(connectionURL);
        }
        catch (SQLException e){
            Log.e("Error connection 1: ", e.getMessage());
        }
        catch (ClassNotFoundException e){
            Log.e("Error connection 2: ", e.getMessage());
        }
        catch (Exception e){
            Log.e("Error connection 3: ", e.getMessage());
        }
        return connection;
    }
}

