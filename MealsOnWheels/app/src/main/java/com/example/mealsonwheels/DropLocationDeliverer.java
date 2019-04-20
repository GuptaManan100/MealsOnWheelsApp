package com.example.mealsonwheels;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealsonwheels.Models.Deliverer;
import com.example.mealsonwheels.Models.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class DropLocationDeliverer extends AppCompatActivity implements LocationListener {

    private Deliverer currUser;
    private String id;
    private String orderid;
    public Order currOrder;
    private String Location_Deliverer;
    private DatabaseReference ref;
    LocationManager locationManager;
    private Button Reached_drop;
    private Query query;
    private ValueEventListener listener;
    private LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_location_deliverer);
        Reached_drop = (Button) findViewById(R.id.Reached_Drop);
        currUser = (Deliverer) getIntent().getSerializableExtra("delivererinfo");
        id = getIntent().getStringExtra("delivererID");
        //ref = FirebaseDatabase.getInstance().getReference().child("Transactions").child("notDelivered");
        query = FirebaseDatabase.getInstance().getReference().child("Transactions").child("notDelivered").orderByChild("deliverer").equalTo(id);
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    orderid = child.getKey();
                    currOrder = child.getValue(Order.class);
                    ref = FirebaseDatabase.getInstance().getReference().child("Transactions").child("notDelivered").child(orderid);
                    getLocation();
                    setButton();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(listener);


        Fragment selectFrag = new mapFragmentDrop();
        Bundle bundle = new Bundle();
        bundle.putSerializable("delivererinfo",currUser);
        bundle.putString("delivererID",id);
        selectFrag.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_delivery_drop, selectFrag).commit();

        locationListener = this;
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Location_Deliverer = (location.getLatitude() + "," + location.getLongitude());
        setRefernce();

        /*try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        }catch(Exception e)
        {

        }*/
    }


    private void setRefernce(){
        currOrder.setDelivererLocation(Location_Deliverer);
        Toast.makeText(DropLocationDeliverer.this, "drtdrtduytduyt", Toast.LENGTH_SHORT).show();
        ref.setValue(currOrder);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(DropLocationDeliverer.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    private void setButton(){
        Reached_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //locationManager.removeUpdates(DropLocationDeliverer.class);
                Intent i = new Intent(DropLocationDeliverer.this,OrderSummaryDeliverer.class);
                i.putExtra("delivererinfo",currUser);
                i.putExtra("delivererID",id);
                query.removeEventListener(listener);
                locationManager.removeUpdates(locationListener);
                startActivity(i);
                finish();
            }
        });
    }
}
