package com.hoteldo.app;

import java.util.Hashtable;

public class User {
    private String Name;

    private String CNIC;
    private String PhoneNumber;
    private String Email;
    private String Password;

    public User(String Name, String CNIC, String phoneNumber, String email, String password) {
        this.Name = Name;
        this.CNIC = CNIC;
        PhoneNumber = phoneNumber;
        Email = email;
        Password = password;
    }

    public User(){
        Name = "";
        CNIC = "";
        PhoneNumber = "";
        Email = "";
        Password = "";
    }

    public void load(Hashtable<String, String> data){
        Name = data.get("name");
        CNIC = data.get("cnic");
        Email = data.get("email");
        PhoneNumber = data.get("number");
    }


    public String getCNIC() {
        return CNIC;
    }

    public String getNumber() {
        return PhoneNumber;
    }
}
