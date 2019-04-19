package com.example.mealsonwheels.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.mealsonwheels.FragmentVendorMenu;
import com.example.mealsonwheels.Models.MenuItem;
import com.example.mealsonwheels.R;
import com.example.mealsonwheels.VendorEditItem;
import com.example.mealsonwheels.ViewHolder.VendorMenuViewHolder;
import com.example.mealsonwheels.ViewHolder.VendorReviewViewHolder;
import com.example.mealsonwheels.restrauntPage;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VendorReviewAdapter extends RecyclerView.Adapter<VendorReviewViewHolder> {

    List<String> ratingList;
    List<String> reviewList;
    //List<Integer> quantity;
    Context context;

    public VendorReviewAdapter(Context context) {
        this.ratingList = new ArrayList<>();
        this.reviewList = new ArrayList<>();
        this.context = context;
    }

    public void addAll(List<String> new_ratingList, List <String> new_reviewList)
    {
        int initsize = ratingList.size();

        ratingList = new ArrayList<>();
        ratingList.addAll(new_ratingList);

        reviewList = new ArrayList<>();
        reviewList.addAll(new_reviewList);

        notifyItemRangeChanged(initsize,new_reviewList.size());
    }

    @NonNull
    @Override
    public VendorReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.vendor_review, viewGroup, false);
        return new VendorReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VendorReviewViewHolder holder, int i) {

        final String review = reviewList.get(i);
        final String rating = ratingList.get(i);

        holder.view_review.setText(review);
        holder.view_rating.setRating(Float.parseFloat(rating));

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}

