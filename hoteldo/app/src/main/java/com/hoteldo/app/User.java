package com.hoteldo.app;

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


}
