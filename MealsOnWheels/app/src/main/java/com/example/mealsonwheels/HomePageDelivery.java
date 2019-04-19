package com.example.mealsonwheels;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealsonwheels.Models.Deliverer;
import com.example.mealsonwheels.Models.User;
import com.example.mealsonwheels.R;
import com.example.mealsonwheels.mapFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePageDelivery extends AppCompatActivity {


    private Deliverer currUser;
    private String id;
    private DatabaseReference ref;
    private DatabaseReference childref;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    Fragment selectFrag = new mapFragment();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_duty:
                    selectFrag = new mapFragment();
                    break;
                case R.id.navigation_orderHistory:
                    selectFrag = new deliveryHistoryFragment();
                    break;
                case R.id.navigation_account:
                    selectFrag = new accountFragmentDelivrer();
                    break;
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("delivererinfo", currUser);
            bundle.putString("delivererID", id);
            selectFrag.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_delivery, selectFrag).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_delivery);

        currUser = (Deliverer) getIntent().getSerializableExtra("delivererinfo");
        id = getIntent().getStringExtra("delivererID");
        ref = FirebaseDatabase.getInstance().getReference("Deliverers");

        //check phone ADN LOCATION PERMISSTION
        if(checkAndRequestPermissions()){
            Fragment selectFrag = new mapFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("delivererinfo",currUser);
            bundle.putString("delivererID",id);
            selectFrag.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_delivery, selectFrag).commit();
        }
        else{
            Fragment selectFrag = new mapFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("delivererinfo",currUser);
            bundle.putString("delivererID",id);
            selectFrag.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_delivery, selectFrag).commit();
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationDelivery);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //Onclick Switch Button function
        Switch Delivrer_isfree = (Switch) findViewById(R.id.isFreeSwitch);
        Delivrer_isfree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    currUser.setIsFree("Yes");
                }else {
                    currUser.setIsFree("No");
                }
                ref.child(id).setValue(currUser);
            }
        });

        childref = ref.child(id).child("isFree");
        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(String.class).equals("InProcess")){
                    Toast.makeText(HomePageDelivery.this, "Getting Pickup Address", Toast.LENGTH_SHORT).show();
                    Intent RestaurantPickup = new Intent(HomePageDelivery.this,RestaurantPickupDeliverer.class);
                    RestaurantPickup.putExtra("delivererinfo",currUser);
                    RestaurantPickup.putExtra("delivererID",id);
                    startActivity(RestaurantPickup);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomePageDelivery.this, "DataBase me kuch error", Toast.LENGTH_SHORT).show();
            }
        });




    }




    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}

