package com.example.mealsonwheels.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mealsonwheels.Interfaces.ItemClickListener;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.Vendor;
import com.example.mealsonwheels.R;
import com.example.mealsonwheels.ViewHolder.OrderViewHolder;
import com.example.mealsonwheels.ViewHolder.RestrauntViewHolder;
import com.example.mealsonwheels.restrauntPage;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    List<Order> orderList;
    Context context;
    List<Integer> type_order;

    public OrderAdapter(Context context) {
        this.orderList = new ArrayList<>();
        this.type_order = new ArrayList<>();
        this.context = context;
    }

    public void addAll(List<Order> newOrder, Integer type)
    {
        int initsize = orderList.size();
        int siz = newOrder.size();
        for(int i=0;i<siz;i++)
        {
            type_order.add(type);
            orderList.add(newOrder.get(i));
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
                    Toast.makeText(context, "Review", Toast.LENGTH_SHORT).show();
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
