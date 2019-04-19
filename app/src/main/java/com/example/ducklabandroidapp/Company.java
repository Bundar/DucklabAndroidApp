package com.example.ducklabandroidapp;

class Company {
    private int companyId;
    private String companyName;
    private String companySymbol;

    public Company(int companyId, String companyName, String companySymbol){
        this.companyId = companyId;
        this.companyName = companyName;
        this.companySymbol = companySymbol;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanySymbol() {
        return companySymbol;
    }
}
