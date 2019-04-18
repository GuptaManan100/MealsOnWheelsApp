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
import com.example.mealsonwheels.Adapter.VendorCurrentOrderAdapter;
import com.example.mealsonwheels.Adapter.VendorReviewAdapter;
import com.example.mealsonwheels.Models.CartItem;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.Review;
import com.example.mealsonwheels.Models.User;
import com.example.mealsonwheels.Models.Vendor;
import com.example.mealsonwheels.ViewHolder.VendorReviewViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentVendorReviews extends Fragment {

    private Vendor vendor;
    private String vendor_id;
    private RecyclerView recycler_reviews;
    private VendorReviewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        //vendor_id = bundle.getString("vendorID");
        vendor_id = "hardikatyal2";
        vendor = (Vendor) bundle.getSerializable("vendorinfo");
        return inflater.inflate(R.layout.layout_vendor_reviews,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_reviews = (RecyclerView) view.findViewById(R.id.recycler_reviews);
        recycler_reviews.hasFixedSize();
        recycler_reviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new VendorReviewAdapter(getActivity());
        recycler_reviews.setAdapter(adapter);

        loadReviews();
    }

    private void loadReviews() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Reviews").orderByChild("vendor").equalTo(vendor_id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> NewReviews = new ArrayList<>();
                List<String> NewRatings = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Review curr = child.getValue(Review.class);
                    NewReviews.add(curr.getReview());
                    NewRatings.add(curr.getRating());
                    Log.d("Order History",curr.toString());
                }
                adapter.addAll(NewRatings, NewReviews);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
