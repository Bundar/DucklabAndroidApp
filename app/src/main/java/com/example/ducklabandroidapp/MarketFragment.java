package com.example.ducklabandroidapp;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketFragment extends Fragment {
    DatabaseHelper db;
    RecyclerView marketList;
    public MarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new DatabaseHelper();
        View v = inflater.inflate(R.layout.fragment_market, container, false);
        marketList = v.findViewById(R.id.recyclerView);
        ArrayList<Company> companies = db.getAllCompany();
        populateList(companies);
        return v;
    }

    private void populateList(ArrayList<Company> companies) {
        marketList.setLayoutManager(new LinearLayoutManager(getContext()));
        marketList.setHasFixedSize(true);
        MarketAdapter marketAdapter = new MarketAdapter(companies);
        marketList.setAdapter(marketAdapter);
    }

}
