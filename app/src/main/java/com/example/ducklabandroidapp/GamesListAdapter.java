package com.example.ducklabandroidapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GamesListAdapter extends RecyclerView.Adapter<GamesListAdapter.MyViewHolder> {
    ArrayList<Game> games;
    Integer userId;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView gameName;
        public TextView gameType;
        public ConstraintLayout parent;

        public MyViewHolder(View v) {
            super(v);
            parent = v.findViewById(R.id.parent);
            gameName = v.findViewById(R.id.gameName);
            gameType = v.findViewById(R.id.gameType);
        }
        public void bindData(Game g){
            gameName.setText(g.getGameName());
            gameType.setText(g.getGameType());
        }
    }
    public GamesListAdapter(ArrayList<Game> games, Integer userId) {
        this.games=games;
        this.userId = userId;
    }

    @NonNull
    @Override
    public GamesListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
        return new GamesListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesListAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.bindData(games.get(i));
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
        return R.layout.list_item_games;
    }

}
