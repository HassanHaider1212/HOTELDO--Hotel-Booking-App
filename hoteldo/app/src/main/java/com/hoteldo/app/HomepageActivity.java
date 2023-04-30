package com.hoteldo.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;

public class HomepageActivity extends AppCompatActivity implements HotelAdapter.HotelClickListener {
    private static ArrayList<Hotel> hotels= new ArrayList<>();
    private static ArrayList<Room> rooms= new ArrayList<>();
    private static ArrayList<FavouriteHotel> favouriteHotels= new ArrayList<>();
    private static ArrayList<Order> orders= new ArrayList<>();
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


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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
                        return true;
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_orderhistory:
                        startActivity(new Intent(getApplicationContext(),OrderhistorypageActivity.class));
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(),ProfilepageActivity.class));
                        return true;
                }
                return false;
            }
        });

        hotels.clear();
        //favouriteHotels.clear();
        rooms.clear();
        //orders.clear();
        recyclerView = (RecyclerView) findViewById(R.id.mainpageHotelsview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HotelAdapter(this, hotels);
        recyclerView.setAdapter(adapter);

        // dummy data for testing
        hotels.add(new Hotel("Hotel One", "Faisal Town,Lahore Pakistan", "hotelone@test.com", 4.5f, null));
        hotels.add(new Hotel("Hotel Two", "DHA Phase V,Lahore Pakistan", "hoteltwo@test.com", 4.8f, null));

        favouriteHotels.add(new FavouriteHotel("zainwajid33@gmail.com", hotels.get(0).getHotelID()));

        rooms.add(new Room(hotels.get(0).getHotelID(), 12.99f, "Economy Suite", true));
        rooms.add(new Room(hotels.get(0).getHotelID(), 22.99f, "Luxury Suite", true));
        rooms.add(new Room(hotels.get(1).getHotelID(), 15.99f, "Gold Suite", true));
        rooms.add(new Room(hotels.get(1).getHotelID(), 30.99f, "Platinum Suite", true));

        orders.add(new Order("Imran",hotels.get(0).getHotelID(),rooms.get(0).getRoomID(),new Date(),new Date(),"aliRaza","AliRaza"));
        orders.add(new Order("Imran",hotels.get(1).getHotelID(),rooms.get(1).getRoomID(),new Date(),new Date(),"aliRaza","AliRaza"));


        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(String id) {
        Intent i = new Intent(getApplicationContext(), Detailspage_Activity.class);
        i.putExtra("hotelID", id);
        startActivity(i);
    }
}
