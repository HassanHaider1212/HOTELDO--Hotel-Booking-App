// Favourite hotel needs to be saved in firebase database
package com.hoteldo.app;

public class FavouriteHotel {
    private String userID;
    private String hotelID;

    public FavouriteHotel(String userID, String hotelID){
        this.hotelID = hotelID;
        this.userID = userID;
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
}
