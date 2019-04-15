package com.example.mealsonwheels.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.mealsonwheels.Interfaces.ItemClickListener;
import com.example.mealsonwheels.MainActivity;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.Review;
import com.example.mealsonwheels.Models.Vendor;
import com.example.mealsonwheels.R;
import com.example.mealsonwheels.ViewHolder.OrderViewHolder;
import com.example.mealsonwheels.ViewHolder.RestrauntViewHolder;
import com.example.mealsonwheels.restrauntPage;
import com.example.mealsonwheels.vendorHomePage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    List<Order> orderList;
    List<String>orderIds;
    Context context;
    List<Integer> type_order;

    public OrderAdapter(Context context) {
        this.orderList = new ArrayList<>();
        this.type_order = new ArrayList<>();
        this.orderIds = new ArrayList<>();
        this.context = context;
    }

    public void addAll(List<Order> newOrder, Integer type,List<String> newOrderIds)
    {
        int initsize = orderList.size();
        int siz = newOrder.size();
        for(int i=0;i<siz;i++)
        {
            type_order.add(type);
            orderList.add(newOrder.get(i));
            orderIds.add(newOrderIds.get(i));
        }
        notifyItemRangeChanged(initsize,siz);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, viewGroup, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int i) {
        final Order curr = orderList.get(i);
        final String currId = orderIds.get(i);

        holder.vendor_name.setText(curr.getVendorName());
        holder.orderList.setText(curr.getItemsOrdered().toString());
        holder.order_price.setText("Rs."+curr.getTotalAmount());
        holder.order_date.setText(curr.getDate());
        holder.mode_payment.setText(curr.getPaymentMode());

        if(type_order.get(i)==1) //Order is Delivered
        {
            holder.back_image.setImageResource(R.color.BaseOrdered);
            holder.mode_payment.setTextColor(Color.BLACK);
            holder.vendor_name.setTextColor(Color.BLACK);
            holder.order_date.setTextColor(Color.BLACK);
            holder.order_price.setTextColor(Color.BLACK);
            holder.orderList.setTextColor(Color.BLACK);
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    final Query query = FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Reviews").child(currId);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot child) {
                            int x = 0;

                            if (child.getChildrenCount()!=0) {
                                x = 1;
                                final Review rev = child.getValue(Review.class);
                                final Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.dialog_review);
                                final EditText reviewText = (EditText) dialog.findViewById(R.id.reviewEditText);
                                final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
                                Button submitButton = (Button) dialog.findViewById(R.id.SubmitButton);
                                reviewText.setText(rev.getReview());
                                ratingBar.setRating(Float.parseFloat(rev.getRating()));
                                dialog.setTitle("Review");

                                submitButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final Review newReview = new Review();
                                        newReview.setCustomer(rev.getCustomer());
                                        newReview.setVendor(rev.getVendor());
                                        newReview.setReview(reviewText.getText().toString());
                                        newReview.setRating(String.valueOf((int) ratingBar.getRating()));
                                        FirebaseDatabase.getInstance().getReference().child("Reviews").child(currId).setValue(newReview);
                                        Query query1 = FirebaseDatabase.getInstance().getReference().child("Vendors").child(curr.getVendor());
                                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Vendor currVen = dataSnapshot.getValue(Vendor.class);
                                                int noofRating = Integer.parseInt(currVen.getNoOfRatings());
                                                float rating = Float.parseFloat(currVen.getRating());
                                                rating+= (Float.parseFloat(newReview.getRating()) - Float.parseFloat(rev.getRating()))/noofRating;
                                                currVen.setRating(String.valueOf(rating));
                                                FirebaseDatabase.getInstance().getReference().child("Vendors").child(curr.getVendor()).setValue(currVen);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                            if(x==0)
                            {
                                final Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.dialog_review);
                                final EditText reviewText = (EditText) dialog.findViewById(R.id.reviewEditText);
                                final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
                                Button submitButton = (Button) dialog.findViewById(R.id.SubmitButton);
                                dialog.setTitle("Review");

                                submitButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final Review newReview = new Review();
                                        newReview.setCustomer(curr.getCustomer());
                                        newReview.setVendor(curr.getVendor());
                                        newReview.setReview(reviewText.getText().toString());
                                        newReview.setRating(String.valueOf((int) ratingBar.getRating()));
                                        FirebaseDatabase.getInstance().getReference().child("Reviews").child(currId).setValue(newReview);
                                        Query query1 = FirebaseDatabase.getInstance().getReference().child("Vendors").child(curr.getVendor());
                                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot child) {
                                                Vendor currVen = child.getValue(Vendor.class);
                                                int noofRating = Integer.parseInt(currVen.getNoOfRatings());
                                                float rating = Float.parseFloat(currVen.getRating());
                                                rating= ((rating*noofRating) + (Float.parseFloat(newReview.getRating())))/(noofRating+1);
                                                noofRating++;
                                                currVen.setNoOfRatings(String.valueOf(noofRating));
                                                currVen.setRating(String.valueOf(rating));
                                                FirebaseDatabase.getInstance().getReference().child("Vendors").child(curr.getVendor()).setValue(currVen);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    //Toast.makeText(context, "Review", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            holder.back_image.setImageResource(R.color.BaseNotOrdered);
            holder.mode_payment.setTextColor(Color.WHITE);
            holder.vendor_name.setTextColor(Color.WHITE);
            holder.order_date.setTextColor(Color.WHITE);
            holder.order_price.setTextColor(Color.WHITE);
            holder.orderList.setTextColor(Color.WHITE);
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Toast.makeText(context, "Tracking", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
