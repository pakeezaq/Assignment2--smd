// RestaurantAdapter.java
package com.example.assign2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private final Context context;
    private List<Restaurant> restaurantList;

    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.bind(restaurant);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void updateData(List<Restaurant> newRestaurantList) {
        this.restaurantList = newRestaurantList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView restname;
        TextView location;
        TextView phone;
        TextView rdescription;
        TextView rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restname = itemView.findViewById(R.id.restname);
            location = itemView.findViewById(R.id.location);
            phone = itemView.findViewById(R.id.phone);
            rdescription = itemView.findViewById(R.id.rdescription);
            rating = itemView.findViewById(R.id.rating);
        }

        public void bind(Restaurant restaurant) {
            restname.setText(restaurant.getName());
            location.setText(restaurant.getLocation());
            phone.setText(restaurant.getPhone());
            rdescription.setText(restaurant.getDescription());
            rating.setText(String.valueOf(restaurant.getRating()));
        }
    }
}