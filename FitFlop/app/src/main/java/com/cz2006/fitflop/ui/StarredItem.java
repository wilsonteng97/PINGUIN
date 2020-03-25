package com.cz2006.fitflop.ui;

public class StarredItem {
    private String name;
    private String address;

    public StarredItem(String name, String address){
        this.name = name;
        this.address = address;
    }

    public String getName(){
        return this.name;
    }

    public String getAddress(){
        return this.address;
    }
}
