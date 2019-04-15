package com.example.mealsonwheels;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mealsonwheels.Adapter.OrderAdapter;
import com.example.mealsonwheels.Models.CartItem;
import com.example.mealsonwheels.Models.Deliverer;
import com.example.mealsonwheels.Models.MenuItem;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class paymentOrder extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Order newOrder;
    private RecyclerView recycler_orders;
    private OrderAdapter adapter;

    private RadioButton cashRadio;
    private RadioButton paytmRadio;
    private Button submitButton;
    private User currUser;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_order);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        newOrder = (Order) getIntent().getSerializableExtra("order");
        currUser = (User) getIntent().getSerializableExtra("userInfo");
        userID = getIntent().getStringExtra("userId");
        cashRadio = (RadioButton) findViewById(R.id.cashRadioButton);
        paytmRadio = (RadioButton) findViewById(R.id.payTMradioButton);
        recycler_orders = (RecyclerView) findViewById(R.id.recycler_order);
        submitButton = (Button) findViewById(R.id.checkoutButton);
        recycler_orders.hasFixedSize();
        recycler_orders.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderAdapter(this);
        recycler_orders.setAdapter(adapter);

        List<Order> NewOrdres = new ArrayList<>();
        List<String> NewOrdresIds = new ArrayList<>();
        NewOrdres.add(newOrder);
        NewOrdresIds.add("dontCare");
        adapter.addAll(NewOrdres,5,NewOrdresIds);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cashRadio.isChecked())
                {
                    Query query = myRef.child("Deliverers").orderByChild("isFree").equalTo("Yes");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int x = 0;
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                x = 1;
                                Deliverer deliverer = child.getValue(Deliverer.class);
                                newOrder.setDeliverer(child.getKey());
                                newOrder.setDelivererName(deliverer.getName());
                                deliverer.setIsFree("No");
                                myRef.child("Deliverers").child(child.getKey()).setValue(deliverer);
                                Log.d("checkout", newOrder.toString());
                                newOrder.setPaymentMode("Cash On Delivery");
                                newOrder.setTransactionId("None");
                                String id = myRef.child("Transactions").child("notDelivered").push().getKey();
                                myRef.child("Transactions").child("notDelivered").child(id).setValue(newOrder);
                                Intent mainIntent = new Intent(paymentOrder.this, userHomePage.class);
                                mainIntent.putExtra("userinfo",currUser);
                                mainIntent.putExtra("userID",userID);
                                startActivity(mainIntent);
                                finish();
                            }
                            if(x==0)
                            {
                                Toast.makeText(paymentOrder.this, "No deliverer free right now!!", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                }
                else
                {

                }
            }
        });

        /*
        Query query = myRef.child("Deliverers").orderByChild("isFree").equalTo("Yes");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int x = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    x = 1;
                    Deliverer deliverer = child.getValue(Deliverer.class);
                    Order newOrder = new Order();
                    newOrder.setDeliverer(child.getKey());
                    newOrder.setDelivererName(deliverer.getName());
                    deliverer.setIsFree("InProcess");
                    myRef.child("Deliverers").child(child.getKey()).setValue(deliverer);
                    Log.d("checkout", newOrder.toString());
                    break;
                }
                if(x==0)
                {
                    Toast.makeText(paymentOrder.this, "No deliverer free right now!!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });*/
    }
}
