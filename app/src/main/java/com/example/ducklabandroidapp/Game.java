package com.example.ducklabandroidapp;

class Game {
    //user data
    private int userId;
    private double balance;
    //game data
    private int gameId;
    private String gameType;
    private String gameName;
    private int adminUID;
    private String gameStatus;
    private double startingBalance;
    private String endDate;
    private double profitGoal;

    public Game(int gameId, String gameType, String gameName, int adminUID, String gameStatus, double startingBalance, String endDate, double profitGoal){
        this.gameId = gameId;
        this.gameType = gameType;
        this.gameName = gameName;
        this.adminUID = adminUID;
        this.gameStatus = gameStatus;
        this.startingBalance = startingBalance;
        this.endDate = endDate;
        this.profitGoal = profitGoal;
    }
    public int getGameId() {
        return gameId;
    }
    public String getGameType() {
        return gameType;
    }
    public String getGameName() {
        return gameName;
    }
    public int getAdminUID() {
        return adminUID;
    }
    public String getGameStatus() {
        return gameStatus;
    }
    public double getStartingBalance() {
        return startingBalance;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getProfitGoal() {
        return profitGoal;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
