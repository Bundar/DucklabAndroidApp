package com.example.ducklabandroidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CompanyActivity extends AppCompatActivity{

    private static final String TAG = "CompanyActivity";
    private String companyName;
    private String companySymbol;
    private Double companyStockPrice;

    TextView companyNameDisplay;
    TextView companySymbolDisplay;
    TextView companyStockPriceDisplay;
    GraphView graphView;
    RecyclerView recyclerView;

    DatabaseHelper db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        companyNameDisplay = findViewById(R.id.companyName);
        companySymbolDisplay = findViewById(R.id.companySymbol);
        companyStockPriceDisplay = findViewById(R.id.stockPrice);
        graphView = findViewById(R.id.graph);
        recyclerView = findViewById(R.id.recyclerView);

        companyName=getIntent().getStringExtra("companyName");
        companySymbol=getIntent().getStringExtra("companySymb");
        Log.d(TAG, "onCreate: comnpanyName="+companyName);
        Log.d(TAG, "onCreate: comnpanySymb="+companySymbol);
        companyNameDisplay.setText(companyName);
        companySymbolDisplay.setText(companySymbol);

        db = new DatabaseHelper();
        companyStockPrice = db.getStockPrice(db.getCompanyId(companyName));
        Log.d(TAG, "onCreate: companystockpric="+companyStockPrice);
        companyStockPriceDisplay.setText("$"+companyStockPrice);
        ArrayList<StockHistory> stockHistoryArrayList = db.getStockHistory(db.getCompanyId(companyName));

        populateList(stockHistoryArrayList);
        plotData(stockHistoryArrayList);
    }

    private void plotData(ArrayList<StockHistory> stockHistoryArrayList) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        String dateTime;
        Double dateTimeDouble;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        for(StockHistory sh:stockHistoryArrayList){
            dateTime = sh.getDateTime();
            try {
                date = sdf.parse(dateTime);
            } catch(Exception e){
                Log.e(TAG, "plotData: error parsing date", e);
            }
            dateTimeDouble = Long.valueOf(date.getTime()).doubleValue();
            Log.d(TAG, "plotData: dateTime="+dateTime);
            Log.d(TAG, "plotData: dateTimeDOuble="+dateTimeDouble);
            series.appendData(new DataPoint(date,sh.getPrice()), true, 10000, false);
        }

        graphView.addSeries(series);
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(3);

        //graphView.getViewport().setMinX(d1.getTime());
        //graphView.getViewport().setMaxX(d3.getTime());
        //graphView.getViewport().setXAxisBoundsManual(true);

        graphView.getGridLabelRenderer().setHumanRounding(false);
    }

    private void populateList(ArrayList<StockHistory> stockHistoryArrayList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        CompanyHistoryAdapter marketAdapter = new CompanyHistoryAdapter(stockHistoryArrayList);
        recyclerView.setAdapter(marketAdapter);
    }
}
