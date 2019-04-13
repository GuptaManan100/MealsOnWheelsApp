package com.example.mealsonwheels;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mealsonwheels.Models.Vendor;

public class restrauntPage extends AppCompatActivity {

    private Vendor curr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restraunt_page);
        curr = (Vendor) getIntent().getSerializableExtra("vendorInfo");
        Toast.makeText(this, curr.getName(), Toast.LENGTH_SHORT).show();
    }
}
