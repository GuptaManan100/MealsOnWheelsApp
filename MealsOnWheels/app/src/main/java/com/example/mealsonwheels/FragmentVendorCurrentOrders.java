package com.example.mealsonwheels;

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
import android.widget.TextView;

import com.example.mealsonwheels.Adapter.OrderAdapter;
import com.example.mealsonwheels.Adapter.RestrauntAdapter;
import com.example.mealsonwheels.Models.CartItem;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.User;
import com.example.mealsonwheels.Models.Vendor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentVendorCurrentOrders extends Fragment {

    private Vendor vendor;
    private String vendor_id;
    private RecyclerView recycler_current_orders;
    private OrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        //vendor_id = bundle.getString("vendorID");
        vendor_id = "dscsdvcdscdsv";
        vendor = (Vendor) bundle.getSerializable("vendorinfo");
        return inflater.inflate(R.layout.layout_vendor_current_orders,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_current_orders = (RecyclerView) view.findViewById(R.id.recycler_current_orders);
        recycler_current_orders.hasFixedSize();
        recycler_current_orders.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new OrderAdapter(getActivity());
        recycler_current_orders.setAdapter(adapter);

        loadOrdersNotDelivred();
    }

    private void loadOrdersNotDelivred() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Transactions").child("notDelivered").orderByChild("vendor").equalTo(vendor_id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Order> NewOrdres = new ArrayList<>();
                List<String> NewOrdresIds = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Order curr = child.getValue(Order.class);
                    NewOrdres.add(curr);
                    NewOrdresIds.add(child.getKey());
                    Log.d("Order History",curr.toString());
                }
                adapter.addAll(NewOrdres,0,NewOrdresIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
