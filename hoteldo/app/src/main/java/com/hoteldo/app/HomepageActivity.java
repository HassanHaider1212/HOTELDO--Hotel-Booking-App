package com.hoteldo.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {
    private static ArrayList<Hotel> hotels;
    private static ArrayList<Room> rooms;
    private static ArrayList<FavouriteHotel> favouriteHotels;
    private static ArrayList<Order> orders;
    private static String message = "hello";
    public static String getMessage(){
        return message;
    }

    public static ArrayList<Hotel> getHotels(){
        return hotels;
    }

    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    public static ArrayList<FavouriteHotel> getFavouriteHotels() {
        return favouriteHotels;
    }

    public static ArrayList<Order> getOrders() {
        return orders;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        BottomNavigationView navbar = (BottomNavigationView) findViewById(R.id.homenavbar);
        navbar.setSelectedItemId(R.id.nav_home);

        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.nav_favourite:
                        startActivity(new Intent(getApplicationContext(),FavouritespageActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_orderhistory:
                        startActivity(new Intent(getApplicationContext(),OrderhistorypageActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(),ProfilepageActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
