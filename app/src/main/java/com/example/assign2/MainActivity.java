package com.example.assign2;

// MainActivity.java

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<Restaurant> restaurantList;
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve restaurant data from SharedPreferences
        String restaurantData = sharedPreferences.getString("restaurantData", "");
        Gson gson = new Gson();
        Type restaurantListType = new TypeToken<List<Restaurant>>() {}.getType();
        restaurantList = gson.fromJson(restaurantData, restaurantListType);
        if (restaurantList == null) {
            restaurantList = new ArrayList<>();
        }
        adapter = new RestaurantAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Initialize RestaurantAdapter with the retrieved restaurantList
        adapter = new RestaurantAdapter(this, restaurantList);
        recyclerView.setAdapter(adapter);

        // Add button click listener
        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open RegisterActivity to add a new restaurant
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Retrieve latest restaurant data from SharedPreferences
        String restaurantData = sharedPreferences.getString("restaurantData", "");
        Gson gson = new Gson();
        Type restaurantListType = new TypeToken<List<Restaurant>>() {}.getType();
        restaurantList = gson.fromJson(restaurantData, restaurantListType);
        if (restaurantList == null) {
            restaurantList = new ArrayList<>();
        }

        // Update RestaurantAdapter with the latest restaurantList
        adapter.updateData(restaurantList);
    }
}
