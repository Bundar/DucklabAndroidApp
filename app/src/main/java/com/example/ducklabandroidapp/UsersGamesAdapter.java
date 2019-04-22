package com.example.ducklabandroidapp;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class UsersGamesAdapter extends RecyclerView.Adapter<UsersGamesAdapter.MyViewHolder> {
    private ArrayList<Game> games;
    private int userId;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "MyViewHolder";
        // each data item is just a string in this case
        public TextView gameName;
        public TextView balance;
        public Button button;
        public ConstraintLayout parent;
        public int userId;
        DatabaseHelper db;
        public MyViewHolder(View v) {
            super(v);
            db = new DatabaseHelper();
            parent = v.findViewById(R.id.parent);
            gameName = v.findViewById(R.id.gameName);
            balance = v.findViewById(R.id.balance);
            button = v.findViewById(R.id.manageButton);
        }
        public void bindData(final Game g, final int userId){
            gameName.setText(g.getGameName());
            balance.setText("$"+String.format("%.2f",g.getBalance()));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext().getApplicationContext(), ManageGameActivity.class);
                    intent.putExtra("gameId", g.getGameId());
                    intent.putExtra("userId", userId);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UsersGamesAdapter(ArrayList<Game> games, int userId) {
        this.userId = userId;
        this.games = games;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.bindData(games.get(i), userId);
        myViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), GameDetailsActivity.class);
                intent.putExtra("gameId", games.get(i).getGameId());
                intent.putExtra("userId", userId);
                v.getContext().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return games.size();
    }

    @Override
    public int getItemViewType(final int position){
        return R.layout.list_item;
    }


}