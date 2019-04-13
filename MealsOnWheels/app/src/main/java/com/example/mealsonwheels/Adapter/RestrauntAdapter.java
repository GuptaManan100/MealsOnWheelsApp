package com.example.mealsonwheels.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mealsonwheels.Interfaces.ItemClickListener;
import com.example.mealsonwheels.Models.Vendor;
import com.example.mealsonwheels.R;
import com.example.mealsonwheels.ViewHolder.RestrauntViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RestrauntAdapter extends RecyclerView.Adapter<RestrauntViewHolder> {

    List<Vendor> vendorList;
    Context context;

    public RestrauntAdapter(Context context) {
        this.vendorList = new ArrayList<>();
        this.context = context;
    }

    public void addAll(List<Vendor> newVend)
    {
        int initsize = vendorList.size();
        vendorList.addAll(newVend);
        notifyItemRangeChanged(initsize,newVend.size());
    }

    public String getLastItemEmail()
    {
        return vendorList.get(vendorList.size()-1).getEmail();
    }

    @NonNull
    @Override
    public RestrauntViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.restraunt_item, viewGroup, false);
        return new RestrauntViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestrauntViewHolder holder, int i) {
        holder.res_name.setText(vendorList.get(i).getName());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Toast.makeText(context, "It works!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendorList.size();
    }
}
