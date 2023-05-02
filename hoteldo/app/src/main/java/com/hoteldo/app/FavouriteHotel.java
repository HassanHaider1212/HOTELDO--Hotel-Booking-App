// Favourite hotel needs to be saved in firebase database
package com.hoteldo.app;

import java.util.Hashtable;
import java.util.UUID;

public class FavouriteHotel {
    private String favouriteID;
    private String userID;
    private String hotelID;

    public FavouriteHotel(String userID, String hotelID){
        this.hotelID = hotelID;
        this.userID = userID;
        favouriteID = UUID.randomUUID().toString();
    }

    public FavouriteHotel() {
        hotelID = "";
        userID = "";
        favouriteID = "";
    }

    public void load(Hashtable<String, String> attributes){
        hotelID = attributes.get("hotelID");
        userID = attributes.get("userID");
        favouriteID = attributes.get("favouriteID");
    }

    public Hashtable<String, String> save(){
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("hotelID", hotelID);
        attributes.put("userID", userID);
        attributes.put("favouriteID", favouriteID);

        return attributes;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getFavouriteID() {
        return favouriteID;
    }
}
