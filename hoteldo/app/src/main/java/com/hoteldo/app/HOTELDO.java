package com.hoteldo.app;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class HOTELDO extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}