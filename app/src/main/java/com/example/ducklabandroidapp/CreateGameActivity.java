package com.example.ducklabandroidapp;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateGameActivity extends AppCompatActivity {

    EditText gameNameInput;
    EditText gameTypeInput;
    EditText startingBalanceInput;
    EditText endDateInput;
    EditText profitGoalInput;
    Button createNewgameButton;
    DatabaseHelper db;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        userId = getIntent().getIntExtra("userId", -1);
        db = new DatabaseHelper();
        gameNameInput = findViewById(R.id.gameName);
        gameTypeInput = findViewById(R.id.gameType);
        startingBalanceInput = findViewById(R.id.startingBalance);
        endDateInput = findViewById(R.id.endDate);
        profitGoalInput = findViewById(R.id.profitGoal);
        createNewgameButton = findViewById(R.id.createGameButton);

        createNewgameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gameType = gameTypeInput.getText().toString();
                String gameName = gameNameInput.getText().toString();
                Double startBal = Double.parseDouble(startingBalanceInput.getText().toString());
                String endDate = endDateInput.getText().toString();
                Double profitGoal = Double.parseDouble(profitGoalInput.getText().toString());
                db.createGame(gameType, gameName, userId, "Active", startBal, endDate, profitGoal);
                onBackPressed();
            }
        });
    }
}
