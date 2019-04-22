package com.example.ducklabandroidapp;

import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.security.auth.login.LoginException;

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
        String query = "SELECT * FROM [Game] where gameId ="+gameId;
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()){
                String gameType = rs.getString("gameType");
                String gameName = rs.getString("gameName");
                int adminUID = rs.getInt("adminId");
                String gameStatus = rs.getString("gameStatus");
                double startingBalance = rs.getDouble("startingBalance");
                String endDate = rs.getString("endDate");
                double profitGoal = rs.getDouble("profitGoal");
                game = new Game(gameId, gameType, gameName, adminUID, gameStatus, startingBalance, endDate, profitGoal);
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
            Log.d(TAG, "getGamesForUser: gameIds="+i);
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
    public ArrayList<Company> searchCompany(String partialCompanyName){
        ArrayList<Company> companies = new ArrayList<Company>();
        String query = "select * from [Company] where companyName like '%"+partialCompanyName+"%' or companySymbol like '%"+partialCompanyName+"%'";
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
                int gameId = rs.getInt("gameId");
                String gameType = rs.getString("gameType");
                String gameName = rs.getString("gameName");
                int adminUID = rs.getInt("adminId");
                String gameStatus = rs.getString("gameStatus");
                double startingBalance = rs.getDouble("startingBalance");
                String endDate = rs.getString("endDate");
                double profitGoal = rs.getDouble("profitGoal");
                Game g = new Game(gameId, gameType, gameName, adminUID, gameStatus, startingBalance, endDate, profitGoal);
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
    //Returns all data for the leaderboard of a certain game
    public ArrayList<UserLeaderBoard> getLeaderBoard(int gameId) {
        ArrayList<UserLeaderBoard> leaderBoard = new ArrayList<>();
        ArrayList<Integer> userIdsInGame = new ArrayList<>();
        String query = "select * from [GameUser] where gameId ="+gameId;
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while(rs.next()){
                userIdsInGame.add(rs.getInt("userId"));
                leaderBoard.add(new UserLeaderBoard(getUserName(rs.getInt("userId")), rs.getDouble("availableBalance"), 0));

            }
            for(int i = 0; i < userIdsInGame.size(); i++){
                int userId = userIdsInGame.get(i);
                String query2 = "select * from [UserStock] where gameId = "+gameId+" and userId = "+userId;
                Statement stat2 = connection.createStatement();
                ResultSet rs2 = stat2.executeQuery(query2);
                if(rs2.next() == false){
                    Log.d(TAG, "getLeaderBoard: result set is empty for userId: "+userId);
                }else {
                    do{
                        String query3 = "select * from [CompanyStock] where companyId = " + rs2.getInt("companyId") + " order by stockTime desc";
                        Statement stat3 = connection.createStatement();
                        ResultSet rs3 = stat3.executeQuery(query3);
                        rs3.next();
                        Double currentPrice = rs3.getDouble("stockPrice");
                        leaderBoard.get(i).addToBalance(currentPrice * rs2.getInt("quantityPurchased"));
                    }
                    while (rs2.next());
                }
            }
        }catch(Exception e){
            Log.e(TAG, "getLeaderBoard: ", e);
        }
        //sort leaderboard:
        Collections.sort(leaderBoard);
        int place = 1;
        for(UserLeaderBoard userLeaderBoard:leaderBoard){
            userLeaderBoard.setPlacing(place);
            place++;
        }
        return leaderBoard;
    }
    public Double getMyGamePortfolioValue(int userId, int gameId){
        double totalBalance = 0.0;
        Log.d(TAG, "getMyGameBalance: uid="+userId+" gameId="+gameId);
        try {
            String query = "select * from [GameUser] where gameId ="+gameId+" and userId="+userId;
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()) {
                //totalBalance = rs.getDouble("availableBalance");
                Log.d(TAG, "getMyGameBalance: total balance init fot gameid ="+gameId+" is :"+totalBalance);
                String query2 = "select * from [UserStock] where gameId = "+gameId+" and userId = "+userId;
                Statement stat2 = connection.createStatement();
                ResultSet rs2 = stat2.executeQuery(query2);
                while (rs2.next()){
                    String query3 = "select top (1) * from [CompanyStock] where companyId = " + rs2.getInt("companyId") + " order by stockTime desc";
                    Statement stat3 = connection.createStatement();
                    ResultSet rs3 = stat3.executeQuery(query3);
                    if(rs3.next()) {
                        Double currentPrice = rs3.getDouble("stockPrice");
                        totalBalance = totalBalance + (currentPrice * rs2.getInt("quantityPurchased"));
                        Log.d(TAG, "getMyGameBalance:userId = "+userId+" total balance = " + totalBalance);
                    }
                }
            }

        }catch (Exception e){
            Log.e(TAG, "getMyGameBalance: ", e);
        }
        Log.d(TAG, "getMyGameBalance: total balance = "+totalBalance);
        return totalBalance;
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

    public int getStockQuantityOwned(int userId, int gameId, int companyId) {
        String query = "select quantityPurchased from [UserStock] where userId = "+userId+" and gameId = "+gameId+" and companyId = "+companyId;
        int count = 0;
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while(rs.next()){
                count += rs.getInt("quantityPurchased");
            }
        }catch(Exception e){
            Log.e(TAG, "getStockQuantityOwned: ", e);
        }
        Log.d(TAG, "getStockQuantityOwned: userId = "+userId+" count="+count);
        return count;
    }

    public void buyStock(int companyId, int userId, int gameId,int quant) {
        String query = "insert into [UserStock] (userId, gameId, companyId, timePurchased, quantityPurchased) values ("+userId+", "+gameId+", "+companyId+", NULL, "+quant+")";
        try{
            Statement stat = connection.createStatement();
            Double stockPrice = getStockPrice(companyId);
            Double moneySpent = stockPrice*quant;
            String query2 = "update [GameUser] set availableBalance = "+(getMyGameAvailableBalance(userId, gameId)-moneySpent)+" where userId = "+userId+" and gameId = "+gameId;
            Statement stat2 = connection.createStatement();
            int row = stat.executeUpdate(query);
            int row2 = stat.executeUpdate(query2);
            Log.d(TAG, "buyStock: row="+row+" quantity = "+quant+" comp="+companyId+" bought");
        }catch (Exception e){
            Log.e(TAG, "buyStock: ", e);
        }
        cleanUserStock(userId, gameId, companyId);
    }

    private void cleanUserStock(int userId, int gameId, int companyId) {
        String query1 = "select userId, gameId, companyId, sum(quantityPurchased) as quantityPurchased from [UserStock] where userId ="+userId+" and gameId = "+gameId+" and companyId ="+companyId+" group by userId, gameId, companyId";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query1);
            if(rs.next()){
                Log.d(TAG, "cleanUserStock: quantity summed");
                int totalQuantity = rs.getInt("quantityPurchased");
                String query2 = "delete from [UserStock] where userId ="+userId+" and gameId = "+gameId+" and companyId ="+companyId;
                int row1 = stat.executeUpdate(query2);
                Log.d(TAG, "cleanUserStock: row1="+row1);
                String query3 = "insert into [UserStock] (userId, gameId, companyId, timePurchased, quantityPurchased) values ("+userId+","+gameId+","+companyId+", NULL,"+totalQuantity+")";
                int row2 = stat.executeUpdate(query3);
                Log.d(TAG, "cleanUserStock: row2="+row2);
            }

        }catch(Exception e){
            Log.e(TAG, "cleanUserStock: ", e);
        }
    }

    public double getMyGameAvailableBalance(int userId, int gameId) {
        String query = "select availableBalance from [GameUser] where userId = "+userId +" and gameId = "+gameId;
        Double availableBalance = 0.0;
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()){
                availableBalance = rs.getDouble("availablebalance");
            }
        }catch(Exception e){
            Log.e(TAG, "getMyGameAvailableBalance: ", e);
        }
        return availableBalance;
    }

    public void sellStock(int companyId, int userId, int gameId, int quant) {
        //ERRER when selling stock for the second time
        String query = "update [UserStock] set quantityPurchased = "+(getStockQuantityOwned(userId, gameId, companyId)-quant)+" where userStockId = (select top (1) userStockId from [UserStock] where userId = "+userId+" and gameId = "+gameId+" and companyId = "+companyId+") ";
        try{
            Statement stat = connection.createStatement();
            int row = stat.executeUpdate(query);
            Log.d(TAG, "sellStock: row="+row+" quantity = "+quant+" comp="+companyId+" sold");
            String query2 = "update [GameUser] set availableBalance = "+(getMyGameAvailableBalance(userId, gameId)+quant*getStockPrice(companyId))+" where userId = "+userId+" and gameId = "+gameId;
            stat.executeUpdate(query2);
        }catch (Exception e){
            Log.e(TAG, "sellStock: ", e);
        }
        cleanUserStock(userId, gameId, companyId);
    }
    public void createGame(String gameType, String gameName, int adminId, String gameStatus, Double startingBalance, String endDate, Double profitGoal){
        //create game
        String query = "insert into [Game] (gameType, gameName, adminId, gameStatus, startingBalance, endDate, profitGoal) values ('"+gameType+"','"+gameName+"',"+adminId+",'"+gameStatus+"',"+startingBalance+",'"+endDate+"',"+profitGoal+")";
        try{
            Statement stat = connection.createStatement();
            int row = stat.executeUpdate(query);
            Log.d(TAG, "createGame: gamemade row="+row);
        }catch (Exception e){
            Log.e(TAG, "createGame: ", e);
        }
    }
    public ArrayList<StockOwned> getUsersStockInGame(int userId, int gameId){
        //return list of stocks owned by user
        ArrayList<StockOwned> myStocks = new ArrayList<StockOwned>();
        String query = "select * from [UserStock] where userId ="+userId+" and gameId="+gameId;
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while(rs.next()){
                myStocks.add(new StockOwned(new Company(rs.getInt("companyId"), getCompanyName(rs.getInt("companyId")), getCompanySymbol(rs.getInt("companyId"))), rs.getInt("quantityPurchased")));
            }
        }catch(Exception e){
            Log.e(TAG, "getUsersStockInGame: ", e);
        }
        return myStocks;
    }

    private String getCompanySymbol(int companyId) {
        String query = "select companySymbol from [Company] where companyId ="+companyId;
        String symbol ="";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()){
                symbol = rs.getString("companySymbol");
            }
        }catch(Exception e){
            Log.e(TAG, "getCompanySymbol: ", e);
        }
        return symbol;
    }

    private String getCompanyName(int companyId) {
        String query = "select companyName from [Company] where companyId ="+companyId;
        String name ="";
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()){
                name = rs.getString("companyName");
            }
        }catch(Exception e){
            Log.e(TAG, "getCompanyName: ", e);
        }
        return name;
    }
}
