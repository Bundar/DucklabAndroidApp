package com.example.ducklabandroidapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameDetailsActivity extends AppCompatActivity {
    private static final String TAG = "GameDetailsActivity";
    DatabaseHelper db;
    ArrayList<UserLeaderBoard> leaderboard;

    TextView gameTypeTV;
    TextView gameNameTV;
    TextView gameStartingBalanceTV;
    RecyclerView leaderBoardRV;
    Button joinGameButton;

    Integer gameId;
    Integer userId;
    Game game;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        db = new DatabaseHelper();

        joinGameButton = findViewById(R.id.joinButton);
        gameTypeTV = findViewById(R.id.gameType);
        gameNameTV = findViewById(R.id.gameName);
        gameStartingBalanceTV = findViewById(R.id.startingBalance);
        leaderBoardRV = findViewById(R.id.leaderboardList);

        gameId = getIntent().getIntExtra("gameId", -1);
        userId = getIntent().getIntExtra("userId", -1);
        Log.d(TAG, "onCreate: gameId="+gameId);
        Log.d(TAG, "onCreate: userId="+userId);
        game = db.getGameDataFromId(gameId);

        gameTypeTV.setText(game.getGameType());
        gameStartingBalanceTV.setText("$"+game.getStartingBalance());
        gameNameTV.setText(game.getGameName());

        leaderboard = db.getLeaderBoard(gameId);

        populateLeaderBoard(leaderboard);
        onJoinPress();
    }

    private void onJoinPress() {
        joinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.userIsInGame(userId, gameId)){
                    Toast.makeText(getApplicationContext(), "You are already in this game.", Toast.LENGTH_LONG).show();
                }else {
                    db.addUserToGame(userId, gameId, game.getStartingBalance());
                    leaderboard = db.getLeaderBoard(gameId);
                    populateLeaderBoard(leaderboard);
                }
            }
        });
    }

    private void populateLeaderBoard(ArrayList<UserLeaderBoard> leaderboard) {
        leaderBoardRV.setLayoutManager(new LinearLayoutManager(this));
        leaderBoardRV.setHasFixedSize(true);
        LeaderBoardAdapter leaderBoardAdapter = new LeaderBoardAdapter(leaderboard);
        leaderBoardRV.setAdapter(leaderBoardAdapter);
    }
}
