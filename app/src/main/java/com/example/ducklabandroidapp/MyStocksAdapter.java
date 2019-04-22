package com.example.ducklabandroidapp;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class MyStocksAdapter extends RecyclerView.Adapter<MyStocksAdapter.MyViewHolder>{
    ArrayList<StockOwned> myStocks;
    public MyStocksAdapter(ArrayList<StockOwned> myStocks) {
        this.myStocks = myStocks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
        return new MyStocksAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bindData(myStocks.get(i));
    }

    @Override
    public int getItemCount() {
        return myStocks.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_item_view_my_stocks;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView compName;
        public TextView compSymbol;
        public TextView stockQuant;
        public TextView stockPrice;
        public DatabaseHelper db;

        public MyViewHolder(View v) {
            super(v);
            db = new DatabaseHelper();
            compName = v.findViewById(R.id.compName);
            compSymbol = v.findViewById(R.id.compSymb);
            stockQuant = v.findViewById(R.id.quantityOwned);
            stockPrice = v.findViewById(R.id.priceper);
        }
        public void bindData(StockOwned stockData){
            compName.setText(stockData.getCompany().getCompanyName());
            compSymbol.setText(stockData.getCompany().getCompanySymbol());
            stockQuant.setText(""+stockData.getQuantity());
            stockPrice.setText("$"+String.format("%.2f",db.getStockPrice(stockData.getCompany().getCompanyId())));
        }
    }
}
