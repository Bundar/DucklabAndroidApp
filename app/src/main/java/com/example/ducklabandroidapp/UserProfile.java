package com.example.ducklabandroidapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserProfile extends AppCompatActivity {
    Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        con = connectionclass();
        populateList();
    }
    public void populateList(){


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
