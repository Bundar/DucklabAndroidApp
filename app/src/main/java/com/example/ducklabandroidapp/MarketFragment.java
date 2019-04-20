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
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketFragment extends Fragment {
    DatabaseHelper db;
    RecyclerView marketList;
    EditText searchQueryInput;
    Button searchButton;
    ArrayList<Company> companies;
    public MarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new DatabaseHelper();
        View v = inflater.inflate(R.layout.fragment_market, container, false);
        marketList = v.findViewById(R.id.recyclerView);
        searchButton = v.findViewById(R.id.button);
        searchQueryInput = v.findViewById(R.id.editText);
        companies = db.getAllCompany();
        populateList(companies);
        onSearchPress();
        return v;
    }

    private void onSearchPress() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchQueryInput.getText().toString();
                if(query != ""){
                    companies = db.searchCompany(query);
                    populateList(companies);
                }
            }
        });
    }

    private void populateList(ArrayList<Company> companies) {
        marketList.setLayoutManager(new LinearLayoutManager(getContext()));
        marketList.setHasFixedSize(true);
        MarketAdapter marketAdapter = new MarketAdapter(companies);
        marketList.setAdapter(marketAdapter);
    }

}
