package com.hoteldo.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.rpc.BadRequest;

public class LoginActivity extends AppCompatActivity {
    Button btnLogIn;
    Button resetButton;
    EditText emailEditText;
    EditText passwordEditText;
    FirebaseAuth mAuth;
    private Boolean passwordVisible = true;

    AdView mAdView;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(),HomepageActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        btnLogIn = findViewById(R.id.btnLogIn);
        resetButton = findViewById(R.id.btnForgotPass);
        emailEditText = findViewById(R.id.loginEmailinput);
        passwordEditText = findViewById(R.id.loginPasswordinput);
        mAuth = FirebaseAuth.getInstance();
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right =2;
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if(event.getRawX() >= passwordEditText.getRight()-passwordEditText.getCompoundDrawables()[Right].getBounds().width())
                    {
                        int selection = passwordEditText.getSelectionEnd();
                        if(passwordVisible)
                        {
                            passwordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_visible_foreground,0);
                            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }
                        else
                        {
                            passwordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_visibleoff_foreground,0);
                            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        passwordEditText.setSelection(selection);
                        return true;
                    }
                }
                return false;
                }
        });


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




    }

    public void LoginSubmitButton(View view){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(email.isEmpty())
        {
            Toast.makeText(this, "Enter Email Please", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(password.isEmpty())
        {
            Toast.makeText(this, "Enter Password Please", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "User Authorized", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomepageActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, ""+task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });



    }
    public void ResetButton(View view)
    {
        String email = emailEditText.getText().toString();
        if(email.isEmpty())
        {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this, "Reset Email Sent", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}