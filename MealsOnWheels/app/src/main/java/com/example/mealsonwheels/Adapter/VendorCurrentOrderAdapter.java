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
import com.example.mealsonwheels.ViewHolder.VendorPastOrderViewHolder;
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

public class VendorCurrentOrderAdapter extends RecyclerView.Adapter<VendorPastOrderViewHolder> {

    List<Order> orderList;
    List<String>orderIds;
    Context context;

    public VendorCurrentOrderAdapter(Context context) {
        this.orderList = new ArrayList<>();
        this.orderIds = new ArrayList<>();
        this.context = context;
    }

    public void addAll(List<Order> newOrder, List<String> newOrderIds)
    {
        int initsize = orderList.size();
        int siz = newOrder.size();
        for(int i=0;i<siz;i++)
        {
            orderList.add(newOrder.get(i));
            orderIds.add(newOrderIds.get(i));
        }
        notifyItemRangeChanged(initsize,siz);
    }

    @NonNull
    @Override
    public VendorPastOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.vendor_past_order_item, viewGroup, false);
        return new VendorPastOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorPastOrderViewHolder holder, int i) {
        final Order curr = orderList.get(i);
        final String currId = orderIds.get(i);

        holder.orderList.setText(curr.getItemsOrdered().toString());
        holder.order_price.setText("Rs."+curr.getTotalAmount());
        holder.order_date.setText(curr.getDate());
        holder.transaction_id.setText(curr.getTransactionId());
        holder.customer_id.setText(curr.getCustomer());

        {
            holder.back_image.setImageResource(R.color.BaseNotOrdered);
            holder.transaction_id.setTextColor(Color.WHITE);
            holder.customer_id.setTextColor(Color.WHITE);
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