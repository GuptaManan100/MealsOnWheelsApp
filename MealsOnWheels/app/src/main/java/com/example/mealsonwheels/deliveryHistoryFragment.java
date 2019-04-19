package com.example.mealsonwheels;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mealsonwheels.Adapter.DelivererOrderAdapter;
import com.example.mealsonwheels.Adapter.OrderAdapter;
import com.example.mealsonwheels.Adapter.RestrauntAdapter;
import com.example.mealsonwheels.Models.Deliverer;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.User;
import com.example.mealsonwheels.Models.Vendor;
import com.example.mealsonwheels.Models.Deliverer;
import com.example.mealsonwheels.Models.DelivererHistory;


import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.firebase.ui.auth.AuthUI.TAG;

public class deliveryHistoryFragment extends Fragment {

    private Deliverer currUser;
    private String id;
    private String customerid;
    FirebaseDatabase database;
    DatabaseReference myRef ;
    List<DelivererHistory> list;
    List<User> list2;
    //List<DelivererHistory> list1;
    RecyclerView recycle;
    Button view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = deliveryHistoryFragment.this.getArguments();
        id = bundle.getString("delivererID");
        currUser = (Deliverer) bundle.getSerializable("delivererinfo");
        return inflater.inflate(R.layout.fragment_delivery_history,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycle = (RecyclerView) view.findViewById(R.id.recycler_order_deliverer);
        database = FirebaseDatabase.getInstance();
        Query query = database.getReference().child("Transactions").child("delivered").orderByChild("deliverer").equalTo(id);
        // Read from the database
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list = new ArrayList<DelivererHistory>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    DelivererHistory value = dataSnapshot1.getValue(DelivererHistory.class);
                    DelivererHistory fire = new DelivererHistory();
                    String date = value.getDate();
                     customerid = value.getCustomer();
                    String vendorName = value.getVendorName();
                    String transactionId = value.getTransactionId();
                    String totalAmount = value.getTotalAmount();
                    String paymentMode = value.getPaymentMode();
                    HashMap itemsOrdered = value.getItemsOrdered();

                    fire.setDate(date);
                    fire.setVendorName(vendorName);
                    fire.setTransactionId(transactionId);
                    fire.setTotalAmount(totalAmount);
                    fire.setPaymentMode(paymentMode);
                    fire.setItemsOrdered(itemsOrdered);

                    list.add(fire);
                    //Log.d(TAG, "Value is: " + value);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Query query2 = database.getReference().child("Users").equalTo(customerid);
        // Read from the database
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list2 = new ArrayList<User>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User value = dataSnapshot1.getValue(User.class);
                    User fire = new User();
                    String customer = value.getName();

                    fire.setName(customer);


                    list2.add(fire);
                    //Log.d(TAG, "Value is: " + value);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DelivererOrderAdapter recyclerAdapter = new DelivererOrderAdapter(list,getContext());
                //RecyclerView.LayoutManager recyce = new GridLayoutManager(getContext(),2);
                RecyclerView.LayoutManager recyce = new LinearLayoutManager(getContext());
                //recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recycle.setLayoutManager(recyce);
                recycle.setItemAnimator( new DefaultItemAnimator());
                recycle.setAdapter(recyclerAdapter);
            }
        });


    }
    }

