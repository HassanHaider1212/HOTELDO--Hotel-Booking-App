package com.hoteldo.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.os.Environment;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

public class OrderhistorypageActivity extends AppCompatActivity {
    private static ArrayList<Order> orders= new ArrayList<>();
    Button btnDownloadOrderHistory;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    protected void onCreate(Bundle savedInstanceState) {
        orders.clear();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhistorypage);

        recyclerView = (RecyclerView) findViewById(R.id.orderhistory_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Order> allOrders = HomepageActivity.getOrders();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uID = auth.getCurrentUser().getEmail();
        for (Order o: allOrders) {
            if (o.getUserID().equals(uID)){
                orders.add(o);
            }
        }
        OrderAdapter adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        BottomNavigationView navbar = (BottomNavigationView) findViewById(R.id.orderhistory_navbar);
        navbar.setSelectedItemId(R.id.nav_orderhistory);

        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.nav_favourite:
                        startActivity(new Intent(getApplicationContext(),FavouritespageActivity.class));
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),HomepageActivity.class));
                        return true;
                    case R.id.nav_orderhistory:
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(),ProfilepageActivity.class));
                        return true;
                }
                return false;
            }
        });

        btnDownloadOrderHistory = findViewById(R.id.btnDownloadOrderHistory);
        btnDownloadOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the permission is already granted
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        // Permission is already granted, perform necessary operations
                        startDownloadService();
                    } else {
                        // Permission is not granted, request the permission
                        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        startActivityForResult(intent, STORAGE_PERMISSION_REQUEST_CODE);
                    }
                } else {
                    // For Android versions prior to 11, handle permission as before
                    if (ContextCompat.checkSelfPermission(OrderhistorypageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Permission is not granted, request the permission
                        ActivityCompat.requestPermissions(OrderhistorypageActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                STORAGE_PERMISSION_REQUEST_CODE);
                    } else {
                        // Permission is already granted, perform necessary operations
                        startDownloadService();
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()) {
                // Permission has been granted
                startDownloadService();
            } else {
                // Permission has been denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to start download service
    private void startDownloadService() {
        if (!orders.isEmpty()) {
            // Create intent to start DownloadService
            Intent intent = new Intent(OrderhistorypageActivity.this, DownloadService.class);
            intent.putExtra("orders", orders);
            startService(intent);
        } else {
            // Show error message if orders ArrayList is empty
            Toast.makeText(this, "No orders found", Toast.LENGTH_SHORT).show();
        }
    }
}