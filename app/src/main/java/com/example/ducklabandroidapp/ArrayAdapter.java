package com.example.ducklabandroidapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ArrayAdapter extends BaseAdapter{
    Context	context;
    LayoutInflater inflater;
    Connection con;
    int userId;
    ArrayList<String> gameNames;
    ArrayList<String> gameBalances;
    public ArrayAdapter(Context context, Connection con, int userId) {
        this.context = context;
        this.con = con;
        this.userId = userId;
        inflater = (LayoutInflater.from(context));
        //get from sql db
        String query1 = "select g.gameName and gu.availableBalance from GameUser gu" +
                        "inner join Game g on gu.gameId = g.gameId"+
                        "where gu.userId = "+userId;
        try{
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(query1);
            gameNames = (ArrayList)rs.getArray("gameName");
            gameBalances = (ArrayList)rs.getArray("availableBalance");
        }
        catch(Exception e){
            Log.e("Error in Adapter: ", e.getMessage());
        }
    }
    @Override
    public View getView(int i, View view, ViewGroup vg){
        view = inflater.inflate(R.layout.list_item, null);
        final TextView gameName = (TextView)view.findViewById(R.id.gameName);
        final TextView gameBalance = (TextView)view.findViewById(R.id.balance);

        gameName.setText(gameNames.get(i));
        gameBalance.setText(gameBalances.get(i));

        gameName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = (String)gameName.getText();
                Intent myIntent = new Intent(context, GameActivity.class);
                Bundle b = new Bundle();
                b.putString("name",selected);
                myIntent.putExtras(b);
                context.startActivity(myIntent);
            }
        });
        return view;
    }
    @Override
    public int getCount() {
        return gameNames.size();
    }
    @Override
    public Object getItem(int position) {
        return gameNames.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


}
