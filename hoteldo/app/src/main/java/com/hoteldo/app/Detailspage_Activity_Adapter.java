package com.hoteldo.app;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Detailspage_Activity_Adapter extends RecyclerView.Adapter<Detailspage_Activity_Adapter.rooms_ViewHolder> {
    ArrayList<Room> localDataSet;
    private static RoomClickListener listener;
    public interface RoomClickListener{
        public void onClick(int pos);
    }

    public Detailspage_Activity_Adapter(RoomClickListener ctx, ArrayList<Room> dataSet) {
        this.localDataSet = dataSet;
        this.listener = ctx;
    }

    @NonNull
    @Override
    public Detailspage_Activity_Adapter.rooms_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_room_item, parent, false);
        rooms_ViewHolder vh = new rooms_ViewHolder(view);
        return (vh);
    }

    @Override
    public void onBindViewHolder(@NonNull Detailspage_Activity_Adapter.rooms_ViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            holder.room_item_name.setText(localDataSet.get(position).getName());
            String Price = Float.toString(localDataSet.get(position).getPrice());
            Price="$"+Price+" per Night";
            holder.room_item_price.setText(Price);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
    public static class rooms_ViewHolder extends RecyclerView.ViewHolder {
        public TextView room_item_name;
        public TextView room_item_price;
        public rooms_ViewHolder(View view) {
            super(view);
            room_item_name= view.findViewById(R.id.room_item_name);
            room_item_price= view.findViewById(R.id.room_item_price);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
