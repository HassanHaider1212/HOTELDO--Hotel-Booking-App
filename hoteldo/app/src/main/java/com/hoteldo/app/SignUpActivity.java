package com.hoteldo.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.BulletSpan;
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
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Hashtable;

public class SignUpActivity extends AppCompatActivity{

    FirebaseAuth mAuth;
    //FirebaseUser firebaseUser;
    EditText nameField;
    EditText emailField;
    EditText passwordField;
    EditText numberField;
    EditText cnicField;
    Button SignupSubmit;
    private Boolean passwordVisible = true;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);
        mAuth = FirebaseAuth.getInstance();
        nameField = findViewById(R.id.signupNameinput );
        emailField = findViewById(R.id.signupEmailinput);
        passwordField = findViewById(R.id.signupPasswordinput);
        passwordField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right =2;
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if(event.getRawX() >= passwordField.getRight()-passwordField.getCompoundDrawables()[Right].getBounds().width())
                    {
                        int selection = passwordField.getSelectionEnd();
                        if(passwordVisible)
                        {
                            passwordField.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_visible_foreground,0);
                            passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }
                        else
                        {
                            passwordField.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_visibleoff_foreground,0);
                            passwordField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        passwordField.setSelection(selection);
                        return true;
                    }
                }
                return false;
                }
            });

        numberField = findViewById(R.id.signupPhoneNumber);
        cnicField = findViewById(R.id.signupCNIC);
        SignupSubmit = findViewById(R.id.btnSignUp);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewSignUp);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    public void SignUpSubmitButton(View view)
    {
        String name;
        String email;
        String password;
        String number;
        String cnic;

        name = String.valueOf(nameField.getText());
        email = String.valueOf(emailField.getText());
        password = String.valueOf(passwordField.getText());
        number = String.valueOf(numberField.getText());
        cnic = String.valueOf(cnicField.getText());
        if(name.isEmpty()||email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "Mail or Pass cann't be empty", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            FirebaseUser user= mAuth.getCurrentUser();
                            //startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name)
                                    .build();
                            user.updateProfile(profileChangeRequest);

                            nameField.setText("");
                            emailField.setText("");
                            passwordField.setText("");
                            numberField.setText("");
                            cnicField.setText("");

                            User u = new User(name, cnic, number, email, password);
                            String id = mAuth.getCurrentUser().getUid();
                            u.save(id);
                            mAuth.signOut();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                        } else {
                            Toast.makeText(SignUpActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
}