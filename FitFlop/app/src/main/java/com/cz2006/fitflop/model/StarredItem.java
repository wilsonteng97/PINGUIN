package com.cz2006.fitflop.model;

/**
 * Stores the name and address for each starred location
 */
public class StarredItem {
    private String name;
    private String address;

    public StarredItem(){
    }

    /**
     * Constructor to initialise starred item
     * @param name
     * @param address
     */
    public StarredItem(String name, String address){
        this.name = name;
        this.address = address;
    }

    /**
     * Retrieve name of starred location
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * Retrieve address of starred location (postal code)
     * @return
     */
    public String getAddress(){
        return this.address;
    }
}
