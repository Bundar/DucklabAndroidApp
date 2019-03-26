package com.example.ducklabandroidapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
    Connection con;
    TextView userNameDisplay;
    TextView firstNameLastNameDisplay;
    ListView gamesListView;
    String profileEmail = "";
    Integer userId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ConnectionHelper connectionHelper = new ConnectionHelper();
        con = connectionHelper.connectionclass();

        userNameDisplay = (TextView)findViewById(R.id.username);
        firstNameLastNameDisplay = (TextView)findViewById(R.id.firstnameLastname);
        gamesListView = (ListView)findViewById(R.id.gamesList);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            profileEmail = extras.getString("email");
        }
        Log.d("PROFILE EMAIL" , profileEmail);
        setUserNameDisplay(profileEmail);
        userId = getUserId(profileEmail);
        populateList();
    }
    public int getUserId(String email){
        String query = "select userID from [User] where email = '"+email+"'";
        Integer userId = -1;
        try{
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()){
                userId = rs.getInt("userID");
            }
        }
        catch(Exception e){
            Log.e("Error getting user id", e.getMessage());
        }
        if(userId == -1){
            Log.d("error","Error getting user id, userId = -1");
        }
        return userId;
    }
    public void populateList(){

        //get from sql db
        String query1 = "select g.gameName, g.gameId, gu.availableBalance from GameUser gu " +
                "inner join Game g on gu.gameId = g.gameId "+
                "where gu.userId = "+userId;


        ArrayList<String> gameNames = new ArrayList<String>();
        ArrayList<String> gameBalances = new ArrayList<String>();
        ArrayList<Integer> gameIds = new ArrayList<Integer>();
        try{
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(query1);

            while(rs.next()){
                gameNames.add(rs.getString("gameName"));
                gameBalances.add(rs.getString("availableBalance"));
                gameIds.add(rs.getInt("gameId"));
            }

            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), gameIds, gameNames, gameBalances);
            gamesListView.setAdapter(arrayAdapter);
        }
        catch(Exception e){
            Log.e("Error in Adapter: ", e.getMessage());
        }
    }
    public void setUserNameDisplay(String userEmail){
        String query = "select * from [User] where email = '" + userEmail + "'";
        try {
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if (rs.next()) {
                userNameDisplay.setText(rs.getString("username"));
                firstNameLastNameDisplay.setText(rs.getString("firstName") + " "+ rs.getString("lastName"));
            } else {
                //User does not exist or wrong info
                Toast.makeText(getApplicationContext(),"Error Loading User Data.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("Error Logging in", e.getMessage());
        }
    }

}
