package com.example.ducklabandroidapp;

class StockHistory {
    private int companyId;
    private String dateTime;
    private Double price;
    public StockHistory(int companyId, String dateTime, Double price){
        this.dateTime = dateTime;
        this.price = price;
        this.companyId = companyId;
    }

    public Double getPrice() {
        return price;
    }

    public String getDateTime() {
        return dateTime;
    }
}
