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

public class CompanyHistoryAdapter extends RecyclerView.Adapter<CompanyHistoryAdapter.MyViewHolder> {
    private ArrayList<StockHistory> stockHistories;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView dateTime;
        public TextView price;
        public ConstraintLayout parent;

        public MyViewHolder(View v) {
            super(v);
            parent = v.findViewById(R.id.layoutParent);
            dateTime = v.findViewById(R.id.dateTime);
            price = v.findViewById(R.id.price);
        }
        public void bindData(StockHistory sh){
            dateTime.setText(sh.getDateTime());
            price.setText("$"+sh.getPrice());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CompanyHistoryAdapter(ArrayList<StockHistory> stockHistories) {
        this.stockHistories = stockHistories;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.bindData(stockHistories.get(i));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return stockHistories.size();
    }

    @Override
    public int getItemViewType(final int position){
        return R.layout.list_item_stock_history;
    }


}