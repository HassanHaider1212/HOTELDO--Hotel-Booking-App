package com.hoteldo.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FavouritespageActivity extends AppCompatActivity {

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
                        overridePendingTransition(0,0);
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
