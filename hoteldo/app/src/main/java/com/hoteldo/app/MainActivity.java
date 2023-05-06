package com.hoteldo.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    Button btnLogIn_startup;
    InterstitialAd mInterstitialAd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);
        btnLogIn_startup = findViewById(R.id.btnLogIn_startup);
        //FirebaseAuth.getInstance().signOut();
        //startActivity(new Intent(getApplicationContext(),HomepageActivity.class));
        Ad();

    }

    public void Ad()
    {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Toast.makeText(MainActivity.this, "Ad Clicked", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Toast.makeText(MainActivity.this, "Ad dismissed fullscreen content.", Toast.LENGTH_SHORT).show();
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Toast.makeText(MainActivity.this, "Ad failed to show fullscreen content.", Toast.LENGTH_SHORT).show();
                                mInterstitialAd = null;
                            }
                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Toast.makeText(MainActivity.this, "Ad recorded an impression.", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Toast.makeText(MainActivity.this, "Ad showed fullscreen content.", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                }
                else {
                    Log.e("AdPending","Ad is not ready");
                }
            }
        },10000);


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