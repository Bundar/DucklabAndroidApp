package com.example.ducklabandroidapp;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder> {
    ArrayList<UserLeaderBoard> leaderBoard;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView placing;
        public TextView username;
        public TextView balance;
        public ConstraintLayout parent;

        public MyViewHolder(View v) {
            super(v);
            parent = v.findViewById(R.id.parent);
            placing = v.findViewById(R.id.placing);
            username = v.findViewById(R.id.userName);
            balance = v.findViewById(R.id.balance);
        }
        public void bindData(UserLeaderBoard userLeaderBoard){
            placing.setText(""+userLeaderBoard.getPlacing());
            username.setText(userLeaderBoard.getUsername());
            balance.setText("$"+userLeaderBoard.getBalance());
        }
    }
    public LeaderBoardAdapter(ArrayList<UserLeaderBoard> leaderBoard) {
        this.leaderBoard=leaderBoard;
    }

    @NonNull
    @Override
    public LeaderBoardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
        return new LeaderBoardAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.bindData(leaderBoard.get(i));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return leaderBoard.size();
    }

    @Override
    public int getItemViewType(final int position){
        return R.layout.list_item_leaderboard;
    }

}
