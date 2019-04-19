package com.example.mealsonwheels;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mealsonwheels.Models.Deliverer;

public class RestaurantPickupDeliverer extends AppCompatActivity {

    private Deliverer currUser;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurant_pickup_deliverer);
        currUser = (Deliverer) getIntent().getSerializableExtra("delivererinfo");
        id = getIntent().getStringExtra("delivererID");

        Fragment selectFrag = new mapFragmentPickup();
        Bundle bundle = new Bundle();
        bundle.putSerializable("delivererinfo",currUser);
        bundle.putString("delivererID",id);
        selectFrag.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_delivery_pickup, selectFrag).commit();

        Button ReachedPickup = (Button) findViewById(R.id.Reached_Pickup);
        ReachedPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RestaurantPickupDeliverer.this,OrderDetails_Deliverer.class);
                i.putExtra("delivererinfo",currUser);
                i.putExtra("delivererID",id);
                startActivity(i);
                finish();
            }
        });

    }
}
