//package com.example.ducklabandroidapp;
//
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.CheckedTextView;
//import android.widget.TextView;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
//
//public class ArrayAdapter extends BaseAdapter{
//    Context	context;
//    LayoutInflater inflater;
//    ArrayList<String> gameNames;
//    ArrayList<String> gameBalances;
//    ArrayList<Integer> gameIds;
//    public ArrayAdapter(Context context,ArrayList<Integer> gameIds, ArrayList<String> gameNames, ArrayList<String> gameBalances) {
//        this.context = context;
//        this.gameNames = gameNames;
//        this.gameBalances = gameBalances;
//        this.gameIds = gameIds;
//        inflater = (LayoutInflater.from(context));
//    }
//    @Override
//    public View getView(int i, View view, ViewGroup vg){
//        view = inflater.inflate(R.layout.list_item, null);
//        final TextView gameName = (TextView)view.findViewById(R.id.gameName);
//        final TextView gameBalance = (TextView)view.findViewById(R.id.balance);
//        final TextView gameId = (TextView)view.findViewById(R.id.gameId);
//
//        gameName.setText(gameNames.get(i));
//        gameBalance.setText("$"+gameBalances.get(i));
//        gameId.setText(gameIds.get(i).toString());
//
//        gameName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String selected = (String)gameId.getText();
//                //Intent myIntent = new Intent(context, GameActivity.class);
//                Bundle b = new Bundle();
//                b.putString("gameId",selected);
//                //myIntent.putExtras(b);
//                //context.startActivity(myIntent);
//            }
//        });
//        return view;
//    }
//    @Override
//    public int getCount() {
//        return gameNames.size();
//    }
//    @Override
//    public Object getItem(int position) {
//        return gameNames.get(position);
//    }
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//
//}
