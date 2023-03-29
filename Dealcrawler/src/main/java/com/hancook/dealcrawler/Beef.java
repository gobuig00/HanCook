package com.hancook.dealcrawler;

public class Beef {
    //"날짜,대,중,소,Kg,income,가격")
    private String date;
    private String large;
    private String medium;
    private String origin;
    private int isKg;
    private int income;
    private String price;

    public Beef(String date, String large, String medium, String origin, int isKg, int income, String price) {
        this.date = date;
        this.large = large;
        this.medium = medium;
        this.origin = origin;
        this.isKg = isKg;
        this.income = income;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public String getLarge() {
        return large;
    }

    public String getMedium() {
        return medium;
    }

    public String getOrigin() {
        return origin;
    }

    public int getIsKg() {
        return isKg;
    }

    public int getIncome() {
        return income;
    }

    public String getPrice() {
        return price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setIsKg(int isKg) {
        this.isKg = isKg;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Beef() {
    }
}
