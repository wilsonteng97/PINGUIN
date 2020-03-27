package com.cz2006.fitflop.model;

public class StarredItem {
    private String name;
    private String address;

    public StarredItem(){
    }

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
