package com.example.mealsonwheels;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mealsonwheels.Adapter.VendorMenuAdapter;
import com.example.mealsonwheels.Adapter.OrderAdapter;
import com.example.mealsonwheels.Models.MenuItem;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.User;
import com.example.mealsonwheels.Models.Vendor;
import com.example.mealsonwheels.ViewHolder.MenuViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentVendorMenu extends Fragment {

    private Vendor vendor;
    private String vendor_id;
    private RecyclerView recycler;
    private VendorMenuAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        vendor_id = bundle.getString("vendorID");
        vendor = (Vendor) bundle.getSerializable("vendorinfo");

        final View view = inflater.inflate(R.layout.layout_vendor_menu, container, false);

       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Button btn = (Button) view.findViewById(R.id.btn_add_item);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                Intent myIntent = new Intent(getActivity(), VendorEditItem.class);
//
//                Bundle bundle = new Bundle();
//
//                bundle.putString("name", "");
//                bundle.putString("mark", "Veg");
//                bundle.putString("price", "0");
//                bundle.putString("category", "Beverages");
//                bundle.putString("isSpicy", "No");
//                bundle.putString("ingredients", "");
//
//                myIntent.putExtras(bundle);
//
//                getActivity().startActivity(myIntent);

                Intent myIntent = new Intent(getActivity(), VendorAddItem.class);
                getActivity().startActivity(myIntent);
            }
        });

        super.onViewCreated(view, savedInstanceState);
        recycler = (RecyclerView) view.findViewById(R.id.recycler_menu);
        recycler.hasFixedSize();
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new VendorMenuAdapter(getActivity(), vendor_id);
        adapter.setCurrVendor(vendor);
        recycler.setAdapter(adapter);

        loadMenu();
    }

    @Override
    public void onResume() {
        loadMenu();
        super.onResume();
    }

    private void loadMenu() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Menus").child(vendor_id);

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



