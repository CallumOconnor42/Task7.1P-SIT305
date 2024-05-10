package com.example.lostandfound;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewListingActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhoneNumber;
    private EditText editTextDescription;
    private EditText editTextDate;
    private EditText editTextLocation;
    private RadioButton radioButtonLost;
    private RadioButton radioButtonFound;
    private Button btnSaveListing;

    private ListingDAO listingDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_listing);

        // Initialize views
        editTextName = findViewById(R.id.edit_text_name);
        editTextPhoneNumber = findViewById(R.id.edit_text_phone_number);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextDate = findViewById(R.id.edit_text_date);
        editTextLocation = findViewById(R.id.edit_text_location);
        radioButtonLost = findViewById(R.id.radio_lost);
        radioButtonFound = findViewById(R.id.radio_found);
        btnSaveListing = findViewById(R.id.btn_save_listing);

        // Initialize ListingDAO
        listingDAO = new ListingDAO(this);

        // Set click listener for Save button
        btnSaveListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveListing();
            }
        });
    }

    private void saveListing() {
        String type = radioButtonLost.isChecked() ? "Lost" : "Found";
        String name = editTextName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();

        // Validate input fields
        if (name.isEmpty() || phoneNumber.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current date if not provided
        if (date.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            date = dateFormat.format(new Date());
        }

        // Save listing to database
        long result = listingDAO.addListing(type, name, phoneNumber, description, date, location);
        if (result != -1) {
            Toast.makeText(this, "Listing saved successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close activity after saving listing
        } else {
            Toast.makeText(this, "Error saving listing", Toast.LENGTH_SHORT).show();
        }
    }
}