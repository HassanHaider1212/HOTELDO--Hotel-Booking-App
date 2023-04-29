package com.hoteldo.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    Button btnLogIn_startup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);
        btnLogIn_startup = findViewById(R.id.btnLogIn_startup);
        FirebaseAuth.getInstance().signOut();
        //startActivity(new Intent(getApplicationContext(),HomepageActivity.class));
    }
    public void LoginButton(View view)
    {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
    public void SignUpButton(View view)
    {
        startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
    }


}