package com.hoteldo.app;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.UUID;

public class Room implements Serializable {
    private String roomID;
    private String hotelID;
    private float price;
    private String name;
    private Boolean availability;

    public Room(String hotelID, float price, String name, Boolean availability) {
        this.hotelID = hotelID;
        this.price = price;
        this.name = name;
        this.availability = availability;
        roomID = UUID.randomUUID().toString();
    }

    public Room() {
        this.hotelID = "";
        this.price = 0.0f;
        this.name = "";
        this.availability = true;
        roomID = "";
    }
    public void load(Hashtable<String, String> attributes){
        hotelID = attributes.get("hotelID");
        price = Float.parseFloat(attributes.get("price"));
        name = attributes.get("name");
        availability = Boolean.parseBoolean(attributes.get("availability"));
        roomID = attributes.get("roomID");
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}
