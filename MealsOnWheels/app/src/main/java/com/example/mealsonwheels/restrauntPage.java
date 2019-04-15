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
import com.example.mealsonwheels.Models.MenuItem;
import com.example.mealsonwheels.Models.Vendor;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class restrauntPage extends AppCompatActivity {

    private Vendor curr;
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
                finalList = adapter.getMenu();
                Log.d("checkout",finalList.toString());
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
