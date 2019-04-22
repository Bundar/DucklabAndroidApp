package com.example.ducklabandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewMyStocksInGameActivity extends AppCompatActivity {
    public int userId;
    public int gameId;
    public ArrayList<StockOwned> myStocks;
    public DatabaseHelper db;
    public Double portVal;
    TextView portfolioValue;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_stocks_in_game);
        db = new DatabaseHelper();
        userId = getIntent().getIntExtra("userId", -1);
        gameId = getIntent().getIntExtra("gameId", -1);

        portVal = db.getMyGamePortfolioValue(userId , gameId);
        myStocks = db.getUsersStockInGame(userId, gameId);

        portfolioValue = findViewById(R.id.totalValue);
        recyclerView = findViewById(R.id.recyclerView2);

        portfolioValue.setText("$"+ String.format("%.2f",portVal));

        populateList();
    }

    private void populateList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        MyStocksAdapter myStocksAdapter = new MyStocksAdapter(myStocks);
        recyclerView.setAdapter(myStocksAdapter);
    }
}
