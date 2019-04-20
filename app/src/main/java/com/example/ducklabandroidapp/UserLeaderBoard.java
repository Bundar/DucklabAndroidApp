package com.example.ducklabandroidapp;

class UserLeaderBoard {
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
}
