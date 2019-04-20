package com.example.ducklabandroidapp;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class UsersGamesAdapter extends RecyclerView.Adapter<UsersGamesAdapter.MyViewHolder> {
    private ArrayList<Game> games;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "MyViewHolder";
        // each data item is just a string in this case
        public TextView gameName;
        public TextView endDate;
        public TextView balance;
        DatabaseHelper db;
        public MyViewHolder(View v) {
            super(v);
            db = new DatabaseHelper();
            gameName = v.findViewById(R.id.gameName);
            balance = v.findViewById(R.id.balance);
        }
        public void bindData(Game g){
            gameName.setText(g.getGameName());
            balance.setText("$"+g.getBalance());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UsersGamesAdapter(ArrayList<Game> games) {
        this.games = games;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bindData(games.get(i));
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