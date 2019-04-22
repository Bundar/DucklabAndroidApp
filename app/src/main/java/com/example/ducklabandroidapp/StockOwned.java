package com.example.ducklabandroidapp;

class StockOwned {
    private Company company;
    private Integer quantity;

    public StockOwned(Company company, Integer quantity){
        this.company = company;
        this.quantity = quantity;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
