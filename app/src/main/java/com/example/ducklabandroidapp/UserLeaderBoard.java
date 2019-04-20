package com.example.ducklabandroidapp;

import android.support.annotation.NonNull;

public class UserLeaderBoard implements Comparable<UserLeaderBoard>{
    private String username;
    private double balance;
    private int placing;

    public UserLeaderBoard(String username, double balance, int placing){

        this.username = username;
        this.balance = balance;
        this.placing = placing;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public int getPlacing() {
        return placing;
    }

    public void addToBalance(Double addMe){balance += addMe;}

    @Override
    public int compareTo(@NonNull UserLeaderBoard userLeaderBoard) {
        if(userLeaderBoard.getBalance() == this.balance){
            return 0;
        }else if(userLeaderBoard.getBalance() > this.balance){
            return 1;
        }
        return -1;
    }

    public void setPlacing(int place) {
        this.placing = place;
    }
}
