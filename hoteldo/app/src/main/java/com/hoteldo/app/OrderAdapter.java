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
    ArrayList<Order> localDataset;
    public OrderAdapter(ArrayList<Order> orders) {
        localDataset = orders;
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
        Order order = localDataset.get(position);
        holder.Datee.setText(order.getArrivalDate().toString());
        holder.RoomName.setText(order.getRoomID());


    }

    @Override
    public int getItemCount() {
        return 0;
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
