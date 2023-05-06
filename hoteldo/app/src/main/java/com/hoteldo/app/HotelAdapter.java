package com.hoteldo.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.net.URI;
import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder>{
    private ArrayList<Hotel> hotels;
    //private Context context;
    private HotelClickListener listener;

    private Context activityContext;
    public interface HotelClickListener{
        public void onClick(String id);
    }
    public HotelAdapter(HotelClickListener ctx, ArrayList<Hotel> hotels, Context context){
        this.hotels = hotels;
        listener = ctx;
        activityContext = context;
    }
    public void setHotels(ArrayList<Hotel> hotels){
        this.hotels = hotels;
    }

    @NonNull
    @Override
    public HotelAdapter.HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_card, parent, false);
        HotelViewHolder vh = new HotelViewHolder(v);
        return (vh);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelAdapter.HotelViewHolder holder, int position) {
        Hotel hotel = hotels.get(position);
        holder.hotelName.setText(hotel.getName());

        ArrayList<Room> allrooms = HomepageActivity.getRooms();
        ArrayList<Room> hotelrooms = new ArrayList<>();
        for (Room r:allrooms) {
            if (r.getHotelID().equals(hotel.getHotelID())){
                hotelrooms.add(r);
            }
        }
        float min = Float.MAX_VALUE;
        for (Room r:hotelrooms) {
            if (r.getPrice() < min){
                min = r.getPrice();
            }
        }
        holder.hotelPrice.setText("From $" + Float.toString(min));

        String[] address = hotel.getAddress().split(",");
        String city = address[0];
        holder.hotelLocation.setText(city);
        holder.hotelCity.setText(address[1]);
        holder.hotelRating.setText(Float.toString(hotel.getRatings()));
        //"https://wallpaperaccess.com/full/266770.jpg"
        // 12kHBMmLPnqfq-qQSW4JKzTpjEjD2-XzL
        String[] parts = hotel.getImageURL().split("/");
        String url = "https://drive.google.com/uc?export=view&id=" + parts[5];
        final int radius = 45;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius, margin);
        Picasso.with(activityContext)
                .load(url)
                .fit()
                .centerCrop()
                .transform(transformation)
                .error(R.drawable.img_hotelimage)
                .into(holder.hotelimage);
        // holder.hotehollImage -> download image and set it here

       // holder.hotelPrice.setText(Float.toString(hotel.get));
        // find the minimum room rpice of all the
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder{
        private ImageView hotelimage;
        private TextView hotelName;
        private TextView hotelLocation;
        private TextView hotelCity;
        private TextView hotelRating;
        private TextView hotelPrice;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelimage = (ImageView) itemView.findViewById(R.id.hotel_image);
            hotelName = (TextView)itemView.findViewById(R.id.hotel_name);
            hotelCity = (TextView)itemView.findViewById(R.id.hotel_city);
            hotelLocation = (TextView)itemView.findViewById(R.id.hotel_location);
            hotelRating = (TextView)itemView.findViewById(R.id.hotel_rating);
            hotelPrice = (TextView)itemView.findViewById(R.id.hotel_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    String hotelID = hotels.get(index).getHotelID();
                    listener.onClick(hotelID);
                }
            });
        }
    }
}

