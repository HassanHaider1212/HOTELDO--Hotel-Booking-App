package com.hoteldo.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Detailspage_Activity extends AppCompatActivity implements Detailspage_Activity_Adapter.RoomClickListener {

    private static ArrayList<Hotel> hotels;
    private static ArrayList<Room> totalrooms;

    private static ArrayList<FavouriteHotel> favouriteHotels;
    private static ArrayList<Room> rooms=new ArrayList<>();
    private static Hotel hotel;
    private FavouriteHotel favObject = null;
    TextView details_hotelname;
    TextView details_hoteladdress;
    TextView details_hotelcity;
    Button details_btnfavourite;
    Button details_btnmessage;
    ImageView details_Hotelimage;
    Detailspage_Activity_Adapter ad;
    RecyclerView recyclerView;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailspage);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Get the hotel id from homepage using intent.
        Intent intent = getIntent();
        String hotelId = intent.getStringExtra("hotelID");

        //search the hotel object from
        hotels = HomepageActivity.getHotels();
        for(int i=0; i<hotels.size(); i++){
            if(Objects.equals(hotels.get(i).getHotelID(), hotelId)){
                hotel=hotels.get(i);
                break;
            }
        }

        for (FavouriteHotel f:HomepageActivity.getFavouriteHotels()) {
            if (f.getHotelID().equals(hotelId) && f.getUserID().equals(currentUser.getEmail())){
                favObject = f;
                break;
            }
        }

        //set hotel image
        //details_Hotelimage = findViewById(R.id.details_Hotelimage);
        //details_Hotelimage.setImageResource(R.drawable.);

        //set hotel name
        details_hotelname = findViewById(R.id.details_hotelname);
        details_hotelname.setText(hotel.getName());

        //set hotel address & set city
        String[]address= hotel.getAddress().split(",");
        details_hoteladdress = findViewById(R.id.details_hoteladdress);
        details_hoteladdress.setText(address[0]);
        details_hotelcity = findViewById(R.id.details_hotelcity);
        details_hotelcity.setText(address[1]);

        //set rooms in recycler view
        rooms.clear();
        totalrooms= HomepageActivity.getRooms();
        for(int i=0; i<totalrooms.size(); i++){
            if(Objects.equals(totalrooms.get(i).getHotelID(), hotelId)){
                rooms.add(totalrooms.get(i));
            }
        }


        recyclerView = findViewById(R.id.recyclerRoomsrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ad = new Detailspage_Activity_Adapter(this,rooms);
        recyclerView.setAdapter(ad);
        ad.notifyDataSetChanged();
        //recyclerView.scrollToPosition(rooms.size() - 1);

        //add favourite button listener
        String userID = intent.getStringExtra("userID");

        details_btnfavourite=findViewById(R.id.details_btnfavourite);

        if(favObject != null){

            details_btnfavourite.setBackgroundResource(R.drawable.heart_filled);
        }
        details_btnfavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favObject == null) {
                    details_btnfavourite.setBackgroundResource(R.drawable.heart_filled);
                    Toast.makeText(Detailspage_Activity.this, "Favourite Hotel Added!", Toast.LENGTH_SHORT).show();
                    favouriteHotels = HomepageActivity.getFavouriteHotels();
                    FavouriteHotel fav = new FavouriteHotel(currentUser.getEmail(), hotelId);
                    favouriteHotels.add(fav);
                }
                else{
                    details_btnfavourite.setBackgroundResource(R.drawable.heart);
                    Toast.makeText(Detailspage_Activity.this, "Favourite Hotel Removed!", Toast.LENGTH_SHORT).show();
                    HomepageActivity.getFavouriteHotels().remove(favObject);
                    HomepageActivity.getFavouriteHotels().size();

                }
            }
        });

        details_btnmessage=findViewById(R.id.details_btnmessage);
        details_btnmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String recipient = hotel.getEmail();
                String sender = currentUser.getEmail();
                String body = "Hello dear!!\n From = " + sender;
                String mailTo = "mailto:" + recipient + "?subject=Subject" + "&body=" + Uri.encode(body);
                Intent emailIntent = new Intent(Intent.ACTION_VIEW);
                emailIntent.setData(Uri.parse(mailTo));
                startActivity(emailIntent);

            }
        });

    }

    @Override
    public void onClick(int pos) {
        Intent intent = new Intent(getApplicationContext(), Checkoutpage_Activity.class);

        intent.putExtra("myHotel", hotel);
        intent.putExtra("myRoom", rooms.get(pos));

        startActivity(intent);
    }
}