package com.example.ducklabandroidapp;

import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseHelper {
    private static final String TAG = "DatabaseHelper";
    private Connection connection;
    private ConnectionHelper connectionHelper;


    public DatabaseHelper(){
        connectionHelper = new ConnectionHelper();
        this.connection = connectionHelper.connectionclass();
    }
    //returns userID for given email
    public int getUserId(String email){
        String query = "select userID from [User] where email = '"+email+"'";
        Integer userId = -1;
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()){
                userId = rs.getInt("userID");
            }
        }
        catch(Exception e){
            Log.e("Error getting user id", e.getMessage());
        }
        if(userId == -1){
            Log.d("error","Error getting user id, userId = -1");
        }
        return userId;
    }
    //returns list of all ids for games user is in
    public ArrayList<Integer> getUserGameIds(int userId){
        ArrayList<Integer> games = new ArrayList<Integer>();
        String query = "select GameUser.gameId from [GameUser] inner join [User] on GameUser.userId = [User].userId where GameUser.userId = '"+userId+"'";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while(rs.next()){
                games.add(rs.getInt("gameId"));
            }
        }
        catch(Exception e){
            Log.e("Error getting user game", e.getMessage());
        }
        if(userId == -1){
            Log.d("error","Error getting user id, userId = -1");
        }
        return games;
    }
    //returns game object for certain gameId
    public Game getGameDataFromId(int gameId){
        Game game = null;
        String query = "SELECT * FROM [dbo].[Game] where [gameId] ='"+gameId+"'";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()){
                //int gameId, String gameType, String gameName, int adminUID, String gameStatus, double startingBalance, String endDate, double profitGoal
                game = new Game(gameId, rs.getString("gameType"), rs.getString("gameName"), rs.getInt("adminId"), rs.getString("gameStatus"), rs.getDouble("startingBalance"), rs.getString("endDate"), rs.getDouble("profitGoal") );
            }
        }
        catch(Exception e){
            Log.e("Error getting game data", e.getMessage());
        }
        if(gameId == -1){
            Log.d("error","Error getting game data, gameId = -1");
        }
        return game;
    }
    //returns list of game objects that a certain user is in
    public ArrayList<Game> getGamesForUser(int userId){
        ArrayList<Game> games = new ArrayList<Game>();
        ArrayList<Integer> gameIds = getUserGameIds(userId);
        for(Integer i: gameIds){
            games.add(getGameDataFromId(i));
        }
        return games;
    }
    //returns username from email
    public String getUserName(String email){
        String query = "select [username] from [User] where email = '"+email+"'";
        String username = "";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()) {
                username = rs.getString("username");
            }
        }
        catch(Exception e){
            Log.e("Error getting user data", e.getMessage());
        }
        if(email == null){
            Log.d("error","Error getting username data, email == null");
        }
        return username;
    }
    //returns username from email
    public String getUserName(Integer userId){
        String query = "select [username] from [User] where userId = '"+userId+"'";
        String username = "";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()) {
                username = rs.getString("username");
            }
        }
        catch(Exception e){
            Log.e("Error getting user data", e.getMessage());
        }
        if(userId == null){
            Log.d("error","Error getting username data, userId == null");
        }
        return username;
    }
    //returns first name
    public String getFirstName(Integer userId){
        String query = "select [firstName] from [User] where userID = '"+userId+"'";
        String first = "";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()) {
                first = rs.getString("firstName");
            }
        }
        catch(Exception e){
            Log.e("Error getting user data", e.getMessage());
        }
        if(first == null){
            Log.d("error","Error getting first data, uid=="+userId);
        }
        return first;
    }
    //returns lastname
    public String getLastName(Integer userId){
        String query = "select [lastName] from [User] where userID = '"+userId+"'";
        String last = "";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()) {
                last = rs.getString("lastName");
            }
        }
        catch(Exception e){
            Log.e("Error getting last data", e.getMessage());
        }
        if(last == null){
            Log.d("error","Error getting lastname data, uid == "+userId);
        }
        return last;
    }
    public ArrayList<Company> getAllCompany(){
        ArrayList<Company> companies = new ArrayList<Company>();
        String query = "select top 1000 * from [Company]";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while(rs.next()){
                companies.add(new Company(rs.getInt("companyID"), rs.getString("companyName"), rs.getString("companySymbol")));
            }
        }
        catch(Exception e){
            Log.e("Error getting user game", e.getMessage());
        }
        if(companies.size() == 0){
            Log.d("error","Error getting companies, companies found=0");
        }
        return companies;
    }

    public ArrayList<StockHistory> getStockHistory(int companyId) {
        ArrayList<StockHistory> stockHistories = new ArrayList<StockHistory>();
        String query = "select * from [CompanyStock] where companyId='"+companyId+"'";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while(rs.next()){
                stockHistories.add(new StockHistory(companyId, rs.getString("stockTime"), rs.getDouble("stockPrice")));
            }
        }
        catch(Exception e){
            Log.e("Error getting user game", e.getMessage());
        }
        if(stockHistories.size() == 0){
            Log.d("error","Error getting companies, companies found=0");
        }
        return stockHistories;
    }

    public int getCompanyId(String companyName) {
        String query = "select companyID from [Company] where companyName = '"+companyName+"'";
        Integer companyId = -1;
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()){
                companyId = rs.getInt("companyID");
            }
        }
        catch(Exception e){
            Log.e("Error getting comp id", e.getMessage());
        }
        if(companyId == -1){
            Log.d("error","Error getting comp id, compid = -1");
        }
        return companyId;
    }

    public Double getStockPrice(int companyId) {
        String query = "select [stockPrice] from [CompanyStock] where companyStockId = (select Max(companyStockId) from [CompanyStock] where companyId = '"+companyId+"') ";
        Double price = 0.0;
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()){
                price = rs.getDouble("stockPrice");
            }
        }
        catch(Exception e){
            Log.e("Error getting price", e.getMessage());
        }
        if(companyId == -1){
            Log.d("error","Error getting price");
        }
        return price;
    }

    //return all games
    public ArrayList<Game> getAllGames(){
        ArrayList<Game> games = new ArrayList<Game>();
        String query = "select * from [Game]";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while(rs.next()){
                //public Game(int gameId, String gameType, String gameName, int adminUID,
                // String gameStatus, double startingBalance, String endDate, double profitGoal)
                Game g = new Game(rs.getInt("gameId"), rs.getString("gameType"), rs.getString("gameName"), rs.getInt("adminId")
                , rs.getString("gameStatus"), rs.getDouble("startingBalance"), rs.getString("endDate"), rs.getDouble("profitGoal"));
                games.add(g);
            }
        }
        catch(Exception e){
            Log.e("Error getting user game", e.getMessage());
        }
        if(games.size() == 0){
            Log.d(TAG,"Error getting companies, companies found=0");
        }
        return games;
    }

    public ArrayList<UserLeaderBoard> getLeaderBoard(int gameId) {
        ArrayList<UserLeaderBoard> leaderBoard = new ArrayList<>();

        String query = "select * from [GameUser] where gameId ="+gameId+" order by availableBalance desc";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            int i = 1;
            while(rs.next()){
                leaderBoard.add(new UserLeaderBoard(getUserName(rs.getInt("userId")), rs.getDouble("availableBalance"), i));
                i++;
            }
        }catch(Exception e){
            Log.e(TAG, "getLeaderBoard: ", e);
        }
        return leaderBoard;
    }

    public void addUserToGame(Integer userId, Integer gameId, Double startingBalance) {
        String stat = "insert into [GameUser] ([gameId],[userId],[availableBalance]) values (" + gameId + ", " + userId + ", '" + startingBalance + "')";
        try{
            Statement statement = connection.createStatement();
            int row = statement.executeUpdate(stat);
        }catch(Exception e){
            Log.e(TAG, "addUserToGame: ", e);
        }
    }

    public boolean userIsInGame(Integer userId, Integer gameId) {
        String query = "select * from [GameUser] where gameId = "+gameId+" and userId ="+userId;
        boolean userInGame = false;
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()){
               userInGame = true;
            }
        }catch(Exception e){
            Log.e(TAG, "userIsInGame: ", e);
        }
        return userInGame;
    }
}
