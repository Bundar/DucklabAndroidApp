package com.example.ducklabandroidapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GamesFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseHelper db;
    String userEmail;
    Integer userId;
    public GamesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_games, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);

        userEmail = getArguments().getString("profileEmail");
        db = new DatabaseHelper();
        ArrayList<Game> games = db.getAllGames();
        userId = db.getUserId(userEmail);
        populateList(games);
        return v;
    }

    private void populateList(ArrayList<Game> games) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        GamesListAdapter gamesListAdapter = new GamesListAdapter(games, userId);
        recyclerView.setAdapter(gamesListAdapter);
    }

}
