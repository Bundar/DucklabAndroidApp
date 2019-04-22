package com.example.ducklabandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ManageGameActivity extends AppCompatActivity {
    private static final String TAG = "ManageGameActivity";
    public int gameId;
    public int userId;
    public ArrayList<Company> companies;
    public Double balanceAvailable;
    public Double portfolioValue;

    public DatabaseHelper db;

    TextView gameName;
    TextView balance;
    TextView portfolioValueDisplay;
    RecyclerView recyclerView;
    Button searchButton;
    Button viewStocksButton;
    EditText queryInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_game);
        db = new DatabaseHelper();

        gameId = getIntent().getIntExtra("gameId", -1);
        userId = getIntent().getIntExtra("userId", -1);
        companies = db.getAllCompany();
        balanceAvailable = db.getMyGameAvailableBalance(userId, gameId);
        portfolioValue = db.getMyGamePortfolioValue(userId, gameId);

        portfolioValueDisplay = findViewById(R.id.portfolioValue);
        gameName = findViewById(R.id.gameName);
        balance = findViewById(R.id.balance);
        recyclerView = findViewById(R.id.recyclerView);
        searchButton = findViewById(R.id.searchButton);
        queryInput = findViewById(R.id.queryInput);
        viewStocksButton = findViewById(R.id.viewMyStocksButton);

        gameName.setText(db.getGameDataFromId(gameId).getGameName());
        String balanceString = String.format("%.2f", balanceAvailable);
        Log.d(TAG, "onCreate: balanceString="+balanceString);
        balance.setText("$"+balanceString);
        portfolioValueDisplay.setText(String.format("%.2f", portfolioValue));
        onSearchPress();
        onViewStockPress();
        populateList();

    }

    private void onViewStockPress() {
        viewStocksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start view stocks activity...
                Intent intent = new Intent(v.getContext().getApplicationContext(), ViewMyStocksInGameActivity.class);
                intent.putExtra("gameId", gameId);
                intent.putExtra("userId", userId);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void onSearchPress() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = queryInput.getText().toString();
                if(query != ""){
                    companies = db.searchCompany(query);
                    populateList();
                }
            }
        });
    }

    private void populateList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ManageGamesAdapter manageGamesAdapter = new ManageGamesAdapter(companies, userId, gameId, balanceAvailable, balance, portfolioValueDisplay);
        recyclerView.setAdapter(manageGamesAdapter);
    }
}
