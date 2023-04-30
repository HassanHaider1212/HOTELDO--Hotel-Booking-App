package com.hoteldo.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.net.PasswordAuthentication;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    //FirebaseUser firebaseUser;
    EditText nameField;
    EditText emailField;
    EditText passwordField;
    EditText numberField;
    EditText cnicField;
    Button SignupSubmit;
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
        setContentView(R.layout.activity_signuppage);
        mAuth = FirebaseAuth.getInstance();
        nameField = findViewById(R.id.signupNameinput );
        emailField = findViewById(R.id.signupEmailinput);
        passwordField = findViewById(R.id.signupPasswordinput);
        numberField = findViewById(R.id.signupPhoneNumber);
        cnicField = findViewById(R.id.signupCNIC);
        SignupSubmit = findViewById(R.id.btnSignUp);




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
                            mAuth.signOut();

                        } else {


                            Toast.makeText(SignUpActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}