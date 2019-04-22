package com.example.ducklabandroidapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManageGamesAdapter extends RecyclerView.Adapter<ManageGamesAdapter.MyViewHolder> {
    private static final String TAG = "ManageGamesAdapter";
    ArrayList<Company> companies;
    int userId;
    int gameId;
    TextView balanceDisplay;
    Double balanceAvailable;
    TextView portfolioValueDisplay;

    public ManageGamesAdapter(ArrayList<Company> companies, int userId, int gameId, Double balanceAvailable, TextView balanceDisplay, TextView portfolioValueDisplay){
        this.companies = companies;
        this.userId = userId;
        this.gameId = gameId;
        this.balanceDisplay = balanceDisplay;
        this.balanceAvailable = balanceAvailable;
        this.portfolioValueDisplay = portfolioValueDisplay;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "MyViewHolder";
        public ConstraintLayout parent;
        public TextView compName;
        public TextView compSymb;
        public TextView price;
        public EditText quantity;
        public ImageButton moreButton;
        public ImageButton lessButton;
        public Button buyButton;
        public Button sellButton;

        public TextView parentBalanceDisplay;
        public TextView parentPortfolioValueDisplay;

        public int quantityInt;
        public int userId;
        public int gameId;
        public Double priceVal;
        public double balanceAvailable;
        public Double portfolioValue;
        public DatabaseHelper db;

        public MyViewHolder(View v, int userId, int gameId, Double balanceAvailable, TextView balanceDisplay, TextView portfolioValueDisplay) {
            super(v);
            db = new DatabaseHelper();
            this.userId = userId;
            this.gameId = gameId;
            this.balanceAvailable = balanceAvailable;
            portfolioValue = db.getMyGamePortfolioValue(userId, gameId);
            quantityInt = 0;

            parentBalanceDisplay = balanceDisplay;
            parentPortfolioValueDisplay = portfolioValueDisplay;

            //header views
            parent = v.findViewById(R.id.parent);
            compName = v.findViewById(R.id.companyName);
            compSymb = v.findViewById(R.id.companySymbol);
            price = v.findViewById(R.id.price);
            //data view
            quantity = v.findViewById(R.id.quantity);
            //action views
            moreButton = v.findViewById(R.id.moreButton);
            lessButton = v.findViewById(R.id.lessButton);
            buyButton = v.findViewById(R.id.buyButton);
            sellButton = v.findViewById(R.id.sellButton);
        }
        public void bindData(final Company c){
            //set header values
            compName.setText(c.getCompanyName());
            compSymb.setText(c.getCompanySymbol());
            priceVal = db.getStockPrice(c.getCompanyId());
            updatePrice();
            bindQuantity(quantityInt);
            //set on click listeners...
            //Add stock to quantity
            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantityInt++;
                    bindQuantity(quantityInt);
                    updatePrice();
                    Log.d(TAG, "onClick.more: userId = "+userId+" gameId = "+gameId+" companyId = "+c.getCompanyId()+" quantityInt = "+quantityInt);

                }
            });
            //Subtract stock from quantity
            lessButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(quantityInt <= 0){
                        Toast.makeText(v.getContext(), "Cannot go negative.", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onClick.less: failed userId = "+userId+" gameId = "+gameId+" companyId = "+c.getCompanyId()+" quantityInt = "+quantityInt);

                        return;
                    }
                    Log.d(TAG, "onClick.less: valid userId = "+userId+" gameId = "+gameId+" companyId = "+c.getCompanyId()+" quantityInt = "+quantityInt);

                    quantityInt--;
                    bindQuantity(quantityInt);
                    updatePrice();
                }
            });
            //buy <quantityIn> stocks of company c
            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(balanceAvailable >= priceVal){
                        Log.d(TAG, "onClick: buy "+quantityInt+" companyname="+c.getCompanyName());
                        db.buyStock(c.getCompanyId(), userId, gameId, quantityInt);
                        balanceAvailable = db.getMyGameAvailableBalance(userId, gameId);
                        portfolioValue = db.getMyGamePortfolioValue(userId, gameId);
                        quantityInt = 0;
                        updatePrice();
                        updateBalance();
                        updatePortfolioValue();
                        bindQuantity(quantityInt);
                    }else{
                        Log.d(TAG, "onClick.buy: failed userId = "+userId+" gameId = "+gameId+" companyId = "+c.getCompanyId()+" quantityInt = "+quantityInt);

                        Toast.makeText(v.getContext(), "Cannot afford to buy this many stocks.", Toast.LENGTH_LONG).show();
                    }
                }
            });
            //sell <quantityInt> stocks of company c
            sellButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(db.getStockQuantityOwned(userId, gameId, c.getCompanyId()) >= quantityInt){
                        Log.d(TAG, "onClick: sell "+quantityInt+" companyname="+c.getCompanyName());
                        Log.d(TAG, "onClick.sell: userId = "+userId+" gameId = "+gameId+" companyId = "+c.getCompanyId()+" quantityInt = "+quantityInt);
                        db.sellStock(c.getCompanyId(), userId, gameId, quantityInt);
                        balanceAvailable = db.getMyGameAvailableBalance(userId, gameId);
                        portfolioValue = db.getMyGamePortfolioValue(userId, gameId);
                        quantityInt = 0;
                        updatePrice();
                        updatePortfolioValue();
                        updateBalance();
                        bindQuantity(quantityInt);
                    }else{
                        Log.d(TAG, "onClick.sell: failed userId = "+userId+" gameId = "+gameId+" companyId = "+c.getCompanyId()+" quantityInt = "+quantityInt);
                        Toast.makeText(v.getContext(), "Cannot sell more stocks than you own.", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        private void updateBalance() {
            String balanceString = String.format("%.2f", balanceAvailable);
            parentBalanceDisplay.setText("$"+balanceString);
        }
        private void updatePortfolioValue() {
            String balanceString = String.format("%.2f", portfolioValue);
            parentPortfolioValueDisplay.setText("$"+balanceString);
        }
        private void bindQuantity(int quant) {
            quantity.setText(""+quant);
        }
        private void updatePrice(){
            price.setText("$"+priceVal*quantityInt);
        }
    }

    @NonNull
    @Override
    public ManageGamesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
        return new ManageGamesAdapter.MyViewHolder(view, userId, gameId, balanceAvailable, balanceDisplay, portfolioValueDisplay);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageGamesAdapter.MyViewHolder myViewHolder, final int i) {
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
        return R.layout.list_item_manage_game;
    }

}
