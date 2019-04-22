package com.example.ducklabandroidapp;

import android.content.Intent;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Connection con;
    String profileEmail = "";
    Integer userId = null;

    private DatabaseHelper db;
    private BottomNavigationView mainNav;
    private FrameLayout mainFrame;
    private HomeFragment homeFragment;
    private MarketFragment marketFragment;
    private GamesFragment gamesFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Bundle extras = getIntent().getExtras();
        db = new DatabaseHelper();
        if (extras != null) {
            profileEmail = extras.getString("email");
            userId = db.getUserId(profileEmail);
        }

        mainFrame = (FrameLayout)findViewById(R.id.mainFrame);
        mainNav = (BottomNavigationView)findViewById(R.id.bottomNavigationView);

        db = new DatabaseHelper();

        Bundle b = new Bundle();
        b.putString("profileEmail", profileEmail);
        homeFragment = new HomeFragment();
        homeFragment.setArguments(b);
        gamesFragment = new GamesFragment();
        gamesFragment.setArguments(b);
        marketFragment = new MarketFragment();

        if(savedInstanceState == null){
            setFragment(homeFragment);
        }
        mainNav.setSelectedItemId(R.id.nav_home);

        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_market:
                        setFragment(marketFragment);
                        return true;
                    case R.id.nav_games:
                        setFragment(gamesFragment);
                        return true;

                        default:
                            return false;

                }
            }
        });
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }


//    public void populateList(){
//
//        //get from sql db
//        String query1 = "select g.gameName, g.gameId, gu.availableBalance from GameUser gu " +
//                "inner join Game g on gu.gameId = g.gameId "+
//                "where gu.userId = "+userId;
//
//
//        ArrayList<String> gameNames = new ArrayList<String>();
//        ArrayList<String> gameBalances = new ArrayList<String>();
//        ArrayList<Integer> gameIds = new ArrayList<Integer>();
//        try{
//            Statement stat = con.createStatement();
//            ResultSet rs = stat.executeQuery(query1);
//
//            while(rs.next()){
//                gameNames.add(rs.getString("gameName"));
//                gameBalances.add(rs.getString("availableBalance"));
//                gameIds.add(rs.getInt("gameId"));
//            }
//
//            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), gameIds, gameNames, gameBalances);
//            gamesListView.setAdapter(arrayAdapter);
//        }
//        catch(Exception e){
//            Log.e("Error in Adapter: ", e.getMessage());
//        }
//    }

}
