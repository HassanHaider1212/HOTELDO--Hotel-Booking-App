package com.hoteldo.app;

import java.io.Serializable;
import java.util.UUID;

public class Hotel implements Serializable {
    private String name;
    private String hotelID;
    private float ratings;
    private String address;
    private String email;
    private String imageURL;


    public Hotel(){
        name = "";
        hotelID = UUID.randomUUID().toString();
        address = "";
        email = "";
        ratings = 0.0f;
    }
    public Hotel(String name, String address, String email, float ratings, String imageURL){
        this.name = name;
        this.address = address;
        this.ratings = ratings;
        this.email = email;
        this.imageURL = imageURL;
        hotelID = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
