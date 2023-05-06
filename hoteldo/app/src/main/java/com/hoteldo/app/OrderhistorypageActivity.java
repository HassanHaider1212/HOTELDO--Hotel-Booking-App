package com.hoteldo.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
                if (ContextCompat.checkSelfPermission(OrderhistorypageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Provide rationale for requesting the permission
                        Toast.makeText(OrderhistorypageActivity.this, "Permission is required for downloading.", Toast.LENGTH_SHORT).show();
                    }
                    // Request the permission using the launcher
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    // Permission is already granted, perform necessary operations
                    startDownloadService();
                }
            }
        });
    }
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    // Permission is granted, perform necessary operations
                    startDownloadService();
                } else {
                    // Permission is denied, show a message or handle accordingly
                    Toast.makeText(OrderhistorypageActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
    );

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