package com.example.mealsonwheels;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealsonwheels.Adapter.MenuAdapter;
import com.example.mealsonwheels.Adapter.RestrauntAdapter;
import com.example.mealsonwheels.Models.CartItem;
import com.example.mealsonwheels.Models.Deliverer;
import com.example.mealsonwheels.Models.MenuItem;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.User;
import com.example.mealsonwheels.Models.Vendor;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
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

public class restrauntPage extends AppCompatActivity {

    private Vendor curr;
    private User currUser;
    private String userID;
    private String Id;
    private TextView textV;
    private List<MenuItem> finalList;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Button checkOutButton;
    private RecyclerView recycler_menu;
    private MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restraunt_page);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        checkOutButton = findViewById(R.id.checkoutButton);
        checkOutButton.setEnabled(false);
        curr = (Vendor) getIntent().getSerializableExtra("vendorInfo");
        currUser  = (User) getIntent().getSerializableExtra("userInfo");
        userID  = getIntent().getStringExtra("userId");

        //Toast.makeText(this, curr.getName(), Toast.LENGTH_SHORT).show();

        Query query = myRef.child("Vendors").orderByChild("email").equalTo(curr.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren())
                {
                    Id = child.getKey();
                    //Toast.makeText(restrauntPage.this, Id, Toast.LENGTH_SHORT).show();
                    afterID();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void afterID() {
        textV = (TextView) findViewById(R.id.textView);
        textV.setText(curr.getName());
        checkOutButton.setEnabled(true);
        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.hasFixedSize();
        recycler_menu.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuAdapter(this);
        recycler_menu.setAdapter(adapter);
        loadMenu();

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = myRef.child("Deliverers").orderByChild("isFree").equalTo("Yes");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int x =0;
                        for (DataSnapshot child : dataSnapshot.getChildren())
                        {
                            x = 1;
                            Deliverer deliverer = child.getValue(Deliverer.class);
                            finalList = adapter.getMenu();
                            Order newOrder = new Order();
                            newOrder.setVendor(Id);
                            newOrder.setCustomer(userID);
                            newOrder.setVendorName(curr.getName());
                            newOrder.setDate(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));

                            HashMap<String,CartItem> newCart = new HashMap<>();
                            float amount=0;
                            for (MenuItem item : finalList) {
                                if(!item.getQuantity().equals("0"))
                                {
                                    amount += Integer.parseInt(item.getQuantity())*Integer.parseInt(item.getPrice());
                                    newCart.put(item.getName(),new CartItem(item.getPrice(),item.getQuantity()));
                                }
                            }
                            amount = amount*1.14f;
                            newOrder.setTotalAmount(String.valueOf(amount));
                            newOrder.setItemsOrdered(newCart);
                            newOrder.setDeliverer(child.getKey());
                            newOrder.setDelivererName(deliverer.getName());
                            deliverer.setIsFree("InProcess");
                            myRef.child("Deliverers").child(child.getKey()).setValue(deliverer);
                            Log.d("checkout",newOrder.toString());
                            break;
                        }
                        if(x==0)
                        {
                            Toast.makeText(restrauntPage.this, "No deliverer free right now!!", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });


            }
        });
    }

    private void loadMenu() {
        Log.d("Load Restraunts","Started Load");

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Menus").child(Id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<MenuItem> newMenu = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for (DataSnapshot nextLev : child.getChildren())
                    {
                        MenuItem newItem = nextLev.getValue(MenuItem.class);
                        newItem.setName(nextLev.getKey());
                        newItem.setType(child.getKey());
                        newItem.setQuantity("0");
                        //newItem.setIngredients(nextLev.get);
                        newMenu.add(newItem);
                    }
                }
                adapter.addAll(newMenu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
