package com.hoteldo.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class HomepageActivity extends AppCompatActivity implements HotelAdapter.HotelClickListener {
    private static ArrayList<Hotel> hotels= new ArrayList<>();
    private ArrayList<Hotel> filteredHotels = new ArrayList<>();
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

    public float getHotelPrice(Hotel h){
        float min = Float.MAX_VALUE;
        for (Room r: rooms ) {
            if (r.getHotelID().equals(h.getHotelID())){
                if(r.getPrice()<min){
                    min = r.getPrice();
                }
            }
        }

        return min;
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

        Spinner filters = (Spinner) findViewById(R.id.spinnerFilters);


        filters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected = parentView.getItemAtPosition(position).toString();

                if (selected.equals("Price: Ascending")){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        filteredHotels.sort((o1, o2) -> Float.compare(getHotelPrice(o1), getHotelPrice(o2)));
                        adapter.notifyDataSetChanged();
                    }
                }
                else if (selected.equals(("Price: Descending"))){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        filteredHotels.sort((o1, o2) -> Float.compare(getHotelPrice(o1), getHotelPrice(o2)));
                        Collections.reverse(filteredHotels);
                        adapter.notifyDataSetChanged();
                    }
                }
                else if (selected.equals("Ratings: Ascending")){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        filteredHotels.sort((o1, o2) -> Float.compare(o1.getRatings(), o2.getRatings()));
                        adapter.notifyDataSetChanged();
                    }
                }
                else if(selected.equals("Ratings: Descending")){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        filteredHotels.sort((o1, o2) -> Float.compare(o1.getRatings(), o2.getRatings()));
                        Collections.reverse(filteredHotels);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        SearchView searchBar = (SearchView) findViewById(R.id.etSearchbar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredHotels.clear();
                for (Hotel h: hotels ) {
                    if (h.getName().toLowerCase().contains(newText.toLowerCase()) || h.getAddress().toLowerCase().contains(newText.toLowerCase())){
                        filteredHotels.add(h);
                    }
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.mainpageHotelsview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HotelAdapter(this, filteredHotels);
        recyclerView.setAdapter(adapter);

        // dummy data for testing
        hotels.add(new Hotel("Hotel One", "Faisal Town,Lahore Pakistan", "hotelone@test.com", 4.5f, null));
        hotels.add(new Hotel("Hotel Two", "DHA Phase V,Lahore Pakistan", "hoteltwo@test.com", 4.8f, null));

        for (Hotel h:hotels) {
            filteredHotels.add(h);
            adapter.notifyDataSetChanged();
        }
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
