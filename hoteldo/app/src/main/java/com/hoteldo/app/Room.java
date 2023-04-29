package com.hoteldo.app;

import java.util.UUID;

public class Room {
    private String roomID;
    private String hotelID;
    private float price;
    private String name;

    public Room(String hotelID, float price, String name) {
        this.hotelID = hotelID;
        this.price = price;
        this.name = name;
        roomID = UUID.randomUUID().toString();
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
}
