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
    TextView number;
    TextView cnic;
    TextView HiName;
    Button LogoutButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        name = findViewById(R.id.profileNameinput);
        email = findViewById(R.id.profileEmailinput);
        LogoutButton = findViewById(R.id.btnprofileSave);
        number = findViewById(R.id.profileNumberinput);
        cnic = findViewById(R.id.profileCnicinput);
        HiName = findViewById(R.id.profileUserName);
        String FullName=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String[] parts = FullName.split(" ");
        String FirstName= parts[0];


        HiName.setText("Hi , " + FirstName);
        name.setText(FullName);
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        cnic.setText(HomepageActivity.user.getCNIC());
        number.setText(HomepageActivity.user.getNumber());

        BottomNavigationView navbar = (BottomNavigationView) findViewById(R.id.profilenavbar);
        navbar.setSelectedItemId(R.id.nav_profile);

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
                        startActivity(new Intent(getApplicationContext(),OrderhistorypageActivity.class));
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
