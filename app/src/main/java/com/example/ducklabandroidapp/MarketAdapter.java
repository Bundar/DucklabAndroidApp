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

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MyViewHolder> {
    private ArrayList<Company> companies;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView compName;
        public TextView compSymb;
        public ConstraintLayout parent;

        public MyViewHolder(View v) {
            super(v);
            parent = v.findViewById(R.id.parent);
            compName = v.findViewById(R.id.companyName);
            compSymb = v.findViewById(R.id.companySymbol);
        }
        public void bindData(Company c){
            compName.setText(c.getCompanyName());
            compSymb.setText(c.getCompanySymbol());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MarketAdapter(ArrayList<Company> companies) {
        this.companies = companies;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.bindData(companies.get(i));
        myViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), CompanyActivity.class);
                intent.putExtra("companyName", companies.get(i).getCompanyName());
                intent.putExtra("companySymb", companies.get(i).getCompanySymbol());
                v.getContext().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return companies.size();
    }

    @Override
    public int getItemViewType(final int position){
        return R.layout.list_item_market;
    }


}