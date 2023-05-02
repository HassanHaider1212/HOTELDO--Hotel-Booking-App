// orders need to be saved to the online database
package com.hoteldo.app;

import com.google.type.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Order {
    private String orderID;
    private String userID;
    private String hotelID;
    private String roomID;
    private Date arrivalDate;
    private Date departureDate;
    private Date placedOn;
    private String guestMail;
    private String guestName;
    private float total;


    public Order(String userID, String hotelID, String roomID, Date arrivalDate, Date departureDate, String guestMail, String guestName) {
        this.orderID = UUID.randomUUID().toString();
        this.userID = userID;
        this.hotelID = hotelID;
        this.roomID = roomID;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.guestMail = guestMail;
        this.guestName = guestName;
        placedOn = new Date();
        int days = (int) TimeUnit.MILLISECONDS.toDays(departureDate.getTime() - arrivalDate.getTime()) % 365;
        // total = room.getprice*days
        //set room availability to false
        float nightPrice = 0.0f;
        for (Room r:HomepageActivity.getRooms()) {
            if (r.getRoomID().equals(roomID)){
                nightPrice = r.getPrice();
                break;
            }
        }
        total = days*nightPrice;
    }

    public Order() {
        this.orderID = "";
        this.userID = "";
        this.hotelID = "";
        this.roomID = "";
        this.arrivalDate = null;
        this.departureDate = null;
        this.guestMail = "";
        this.guestName = "";
        placedOn = null;
    }
    public void load(Hashtable<String, String> attributes){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");
        try{
            placedOn = dateFormat.parse(attributes.get("placedOn"));
            arrivalDate = dateFormat.parse(attributes.get("arrivalDate"));
            departureDate = dateFormat.parse(attributes.get("departureDate"));
        }
        catch(ParseException ex){

        }
        this.orderID = attributes.get("orderID");
        this.userID = attributes.get("userID");
        this.hotelID = attributes.get("hotelID");
        this.roomID = attributes.get("roomID");
        this.guestMail = attributes.get("guestMail");
        this.guestName = attributes.get("guestName");
        this.total = Float.parseFloat(attributes.get("total"));
    }
    public Hashtable<String, String> save(){
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("orderID", orderID);
        attributes.put("hotelID", hotelID);
        attributes.put("userID", userID);
        attributes.put("roomID", roomID);
        attributes.put("guestMail", guestMail);
        attributes.put("guestName", guestName);
        attributes.put("total", Float.toString(total));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");
        attributes.put("placedOn",dateFormat.format(placedOn));
        attributes.put("arrivalDate",dateFormat.format(arrivalDate));
        attributes.put("departureDate",dateFormat.format(departureDate));

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

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getPlacedOn() {
        return placedOn;
    }

    public String getPlacedOnString(){
        try {

            int Day = placedOn.getDay();
            int month = placedOn.getMonth();
            int hour = placedOn.getHours();
            int min = placedOn.getMinutes();

            String d = "";
            switch(Day){
                case 0:
                    d = "Sun";
                case 1:
                    d = "Mon";
                case 2:
                    d = "Tue";
                case 3:
                    d = "Wed";
                case 4:
                    d = "Thu";
                case 5:
                    d = "Fri";
                case 6:
                    d = "Sat";
            }

            String m = "";
            switch (month){
                case 0:
                    m = "Jan";
                case 1:
                    m = "Feb";
                case 2:
                    m = "Mar";
                case 3:
                    m = "Apr";
                case 4:
                    m = "May";
                case 5:
                    m = "Jun";
                case 6:
                    m = "Jul";
                case 7:
                    m = "Aug";
                case 8:
                    m = "Sep";
                case 9:
                    m = "Oct";
                case 10:
                    m = "Nov";
                case 11:
                    m = "Dec";
            }

            String time = d + ", " + m + " " + Integer.toString(hour) + ":" + Integer.toString(min);


            return time;
        }
        catch (NullPointerException e){
            return "";
        }
    }

    public void setPlacedOn(Date placedOn) {
        this.placedOn = placedOn;
    }

    public String getGuestMail() {
        return guestMail;
    }

    public void setGuestMail(String guestMail) {
        this.guestMail = guestMail;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getOrderID() {
        return orderID;
    }
}
