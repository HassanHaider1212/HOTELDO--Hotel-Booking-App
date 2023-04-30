package com.hoteldo.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Detailspage_Activity extends AppCompatActivity implements Detailspage_Activity_Adapter.RoomClickListener {

    private static ArrayList<Hotel> hotels;
    private static ArrayList<Room> totalrooms;

    private static ArrayList<FavouriteHotel> favouriteHotels;
    private static ArrayList<Room> rooms=new ArrayList<>();
    private static Hotel hotel;
    TextView details_hotelname;
    TextView details_hoteladdress;
    TextView details_hotelcity;
    Button details_btnfavourite;
    ImageView details_Hotelimage;
    Detailspage_Activity_Adapter ad;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailspage);

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
        details_btnfavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create favourite object and add the hotel_id and userid.
                Toast.makeText(Detailspage_Activity.this, "Favourite Hotel!", Toast.LENGTH_SHORT).show();
                favouriteHotels=HomepageActivity.getFavouriteHotels();
                FavouriteHotel fav = new FavouriteHotel(userID, hotelId);
                favouriteHotels.add(fav);
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