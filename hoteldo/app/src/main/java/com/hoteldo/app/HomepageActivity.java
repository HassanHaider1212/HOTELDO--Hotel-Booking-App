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
import java.util.Hashtable;

public class HomepageActivity extends AppCompatActivity implements HotelAdapter.HotelClickListener, FirebaseDataManager.DataObserver {
    private static ArrayList<Hotel> hotels= new ArrayList<>();
    private ArrayList<Hotel> filteredHotels = new ArrayList<>();
    private static ArrayList<Room> rooms= new ArrayList<>();
    private static ArrayList<FavouriteHotel> favouriteHotels= new ArrayList<>();
    private static ArrayList<Order> orders= new ArrayList<>();
    public static User user = new User();
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

    public static IDataManager dao;
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
                        hotels.sort((o1, o2) -> Float.compare(getHotelPrice(o1), getHotelPrice(o2)));
                    }
                }
                else if (selected.equals(("Price: Descending"))){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        filteredHotels.sort((o1, o2) -> Float.compare(getHotelPrice(o1), getHotelPrice(o2)));
                        Collections.reverse(filteredHotels);
                        hotels.sort((o1, o2) -> Float.compare(getHotelPrice(o1), getHotelPrice(o2)));
                        Collections.reverse(hotels);
                    }
                }
                else if (selected.equals("Ratings: Ascending")){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        filteredHotels.sort((o1, o2) -> Float.compare(o1.getRatings(), o2.getRatings()));
                        hotels.sort((o1, o2) -> Float.compare(o1.getRatings(), o2.getRatings()));
                    }
                }
                else if(selected.equals("Ratings: Descending")){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        filteredHotels.sort((o1, o2) -> Float.compare(o1.getRatings(), o2.getRatings()));
                        Collections.reverse(filteredHotels);
                        hotels.sort((o1, o2) -> Float.compare(o1.getRatings(), o2.getRatings()));
                        Collections.reverse(hotels);
                    }
                }

                adapter.notifyDataSetChanged();
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
        adapter = new HotelAdapter(this, filteredHotels, getApplicationContext());
        recyclerView.setAdapter(adapter);
        //
        dao = new FirebaseDataManager(this);

        for (Hotel h:hotels) {
            filteredHotels.add(h);
            adapter.notifyDataSetChanged();
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(String id) {
        Intent i = new Intent(getApplicationContext(), Detailspage_Activity.class);
        i.putExtra("hotelID", id);
        startActivity(i);
    }


    public Boolean isHotelLoaded(Hotel h){
        for (Hotel hotel:hotels) {
            if(hotel.getHotelID().equals(h.getHotelID())){
                return true;
            }
        }
        return false;
    }
    public Boolean isRoomLoaded(Room r){
        for (Room room:rooms) {
            if(room.getRoomID().equals(r.getRoomID())){
                return true;
            }
        }
        return false;
    }
    public Boolean isOrderLoaded(Order o){
        for (Order order:orders) {
            if(order.getOrderID().equals(o.getOrderID())){
                return true;
            }
        }
        return false;
    }
    public Boolean isFavouriteHotelLoaded(FavouriteHotel h){
        for (FavouriteHotel hotel:favouriteHotels) {
            if(hotel.getFavouriteID().equals(h.getFavouriteID())){
                return true;
            }
        }
        return false;
    }
    @Override
    public void updateHotels(ArrayList<Hashtable<String, String>> loadedhotels) {
        for (Hashtable<String, String> attributes:loadedhotels) {
            Hotel h = new Hotel();
            h.load(attributes);
            if (!isHotelLoaded(h)){
                hotels.add(h);
                filteredHotels.add(h);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateRooms(ArrayList<Hashtable<String, String>> loadedrooms) {
        for (Hashtable<String, String> attributes:loadedrooms) {
            Room r = new Room();
            r.load(attributes);
            if(!isRoomLoaded(r)) {
                rooms.add(r);
            }
        }
    }

    @Override
    public void updateOrders(ArrayList<Hashtable<String, String>> loadedorders) {
        for (Hashtable<String, String> attributes:loadedorders) {
            Order o = new Order();
            o.load(attributes);
            if(!isOrderLoaded(o)) {
                orders.add(o);
            }
        }
    }

    @Override
    public void updateFavourites(ArrayList<Hashtable<String, String>> loadedfavourites) {
        for (Hashtable<String, String> attributes:loadedfavourites) {
            FavouriteHotel f = new FavouriteHotel();
            f.load(attributes);
            if(!isFavouriteHotelLoaded(f)) {
                favouriteHotels.add(f);
            }
        }
    }

    @Override
    public void updateUser(ArrayList<Hashtable<String, String>> data) {
        for (Hashtable<String, String> attribute:data) {
            if(attribute.get("id").equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                user.load(attribute);
                break;
            }
        }
    }

}
