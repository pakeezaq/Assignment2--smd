package com.example.assign2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextLocation, editTextPhone, editTextDescription, editTextRatings;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Retrieve restaurant data from SharedPreferences
        List<Restaurant> restaurantList = new ArrayList<>();

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextRatings = findViewById(R.id.editTextRatings);
        Button buttonSave = findViewById(R.id.buttonSave);

        // Set click listener for Save button
        buttonSave.setOnClickListener(v -> saveRestaurant());
    }

    private void saveRestaurant() {
        // Get input values from EditText fields
        String name = editTextName.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String ratingsStr = editTextRatings.getText().toString().trim();

        // Validate input fields
        if (name.isEmpty() || location.isEmpty() || phone.isEmpty() || description.isEmpty() || ratingsStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert ratings to float
        float ratings = Float.parseFloat(ratingsStr);

        // Retrieve existing restaurant list from SharedPreferences
        String restaurantData = sharedPreferences.getString("restaurantData", "");
        Gson gson = new Gson();
        Type restaurantListType = new TypeToken<List<Restaurant>>() {
        }.getType();
        List<Restaurant> existingRestaurantList = gson.fromJson(restaurantData, restaurantListType);

        // Add new restaurant to existing list
        if (existingRestaurantList == null) {
            existingRestaurantList = new ArrayList<>();
        }
        existingRestaurantList.add(new Restaurant(name, location, phone, description, ratings));

        // Save updated restaurant list to SharedPreferences
        String updatedRestaurantData = gson.toJson(existingRestaurantList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("restaurantData", updatedRestaurantData);
        editor.apply();

        // Notify MainActivity to refresh RecyclerView
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // Show success message
        Toast.makeText(this, "Restaurant added successfully", Toast.LENGTH_LONG).show();

        // Finish activity
        finish();
    }
}
