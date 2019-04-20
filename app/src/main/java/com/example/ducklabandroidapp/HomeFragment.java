package com.example.ducklabandroidapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    TextView username;
    TextView firstNameLastName;
    RecyclerView gamesList;
    DatabaseHelper db;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        username = v.findViewById(R.id.username);
        firstNameLastName = v.findViewById(R.id.firstNameLastName);
        gamesList = v.findViewById(R.id.gamesList);


        db = new DatabaseHelper();

        String email = getArguments().getString("profileEmail");
        String userName = db.getUserName(email);
        int userId = db.getUserId(email);
        String firstName = db.getFirstName(userId);
        String lastName = db.getLastName(userId);
        ArrayList<Game> games = new ArrayList<Game>();
        games = db.getGamesForUser(userId);
        username.setText(userName);
        firstNameLastName.setText(firstName+" "+lastName);
        for(Game g: games){
            Log.d("games",g.getGameName());
        }
        populateList(games);
        return v;
    }

    private void populateList(ArrayList<Game> games) {
        gamesList.setLayoutManager(new LinearLayoutManager(getContext()));
        gamesList.setHasFixedSize(true);
        UsersGamesAdapter usersGamesAdapter = new UsersGamesAdapter(games);
        gamesList.setAdapter(usersGamesAdapter);
    }
}