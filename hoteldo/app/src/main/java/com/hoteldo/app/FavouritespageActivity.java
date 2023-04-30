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
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class FavouritespageActivity extends AppCompatActivity implements HotelAdapter.HotelClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<FavouriteHotel> favourites;
    private ArrayList<Hotel> favouriteHotels = new ArrayList<>();
    FirebaseAuth auth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favouritespage);

        BottomNavigationView navbar = (BottomNavigationView) findViewById(R.id.favouritesnavbar);
        navbar.setSelectedItemId(R.id.nav_favourite);
        Toast.makeText(getApplicationContext(), HomepageActivity.getMessage(), Toast.LENGTH_LONG).show();

        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.nav_favourite:
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),HomepageActivity.class));
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
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        favourites = HomepageActivity.getFavouriteHotels();
        ArrayList<Hotel> hotels = HomepageActivity.getHotels();
        for (FavouriteHotel fv:favourites) {
            for (Hotel h : hotels) {
                if (h.getHotelID().equals(fv.getHotelID()) && Objects.equals(auth.getCurrentUser().getEmail(), fv.getUserID())){
                    favouriteHotels.add(h);
                }
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.fav_hotels_listing_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HotelAdapter(this, favouriteHotels);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(String id) {
        Intent i = new Intent(getApplicationContext(), Detailspage_Activity.class);
        i.putExtra("hotelID", id);
        startActivity(i);
    }
}
