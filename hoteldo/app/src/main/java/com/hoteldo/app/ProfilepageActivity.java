package com.hoteldo.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfilepageActivity extends AppCompatActivity {

    TextView name;
    TextView email;

    TextView HiName;

    Button LogoutButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        name = findViewById(R.id.profileNameinput);
        email = findViewById(R.id.profileEmailinput);
        LogoutButton = findViewById(R.id.btnprofileSave);
        HiName = findViewById(R.id.profileUserName);
        String FullName=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String FirstName= FullName.substring(0,FullName.indexOf(' '));

        HiName.setText("Hi ," + FirstName);
        name.setText(FullName);
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        BottomNavigationView navbar = (BottomNavigationView) findViewById(R.id.profilenavbar);
        navbar.setSelectedItemId(R.id.nav_profile);

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
                        startActivity(new Intent(getApplicationContext(),HomepageActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_orderhistory:
                        startActivity(new Intent(getApplicationContext(),OrderhistorypageActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_profile:
                        return true;
                }
                return false;
            }
        });
    }

    public void LogOutButton(View view)
    {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

}
