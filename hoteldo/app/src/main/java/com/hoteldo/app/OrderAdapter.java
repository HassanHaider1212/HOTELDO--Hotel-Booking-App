package com.hoteldo.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    ArrayList<Order> orders ;

    public OrderAdapter(ArrayList<Order> orders) {
        this.orders = orders;

    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderhistory_item, parent, false);
        OrderAdapter.OrderViewHolder vh = new OrderViewHolder(v);
        return (vh);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        ArrayList<Hotel> allHotel =HomepageActivity.getHotels();
        ArrayList<Room> allRoom= HomepageActivity.getRooms();
        holder.Datee.setText(order.getPlacedOnString());
        Hotel tempH = new Hotel();
        for (Hotel hot : allHotel) {
            if (hot.getHotelID().equals(order.getHotelID())) {
                tempH = hot;
                break;
            }
        }
        holder.HotelName.setText(tempH.getName());
        Room tempRoom = null;
        for (Room roo : allRoom)
        {
            if(roo.getRoomID().equals(order.getRoomID()))
            {
                tempRoom = roo;
            }
        }
        holder.RoomName.setText(tempRoom.getName());
        holder.Price.setText("$"+String.valueOf( order.getTotal() ));
        


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        private TextView Datee;
        private TextView HotelName;
        private TextView RoomName;
        private TextView Price;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            Datee = (TextView)itemView.findViewById(R.id.orderhistoryitem_date);
            HotelName = (TextView)itemView.findViewById(R.id.orderhistoryitem_hotel);
            RoomName = (TextView)itemView.findViewById(R.id.orderhistoryitem_roomname);
            Price = (TextView)itemView.findViewById(R.id.orderhistoryitem_total);

        }
    }
}
