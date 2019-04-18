package com.example.mealsonwheels.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealsonwheels.Interfaces.ItemClickListener;
import com.example.mealsonwheels.R;

public class VendorPastOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView transaction_id;
    public TextView order_date;
    public TextView customer_id;
    public TextView orderList;
    public TextView order_price;
    public ImageView back_image;

    private ItemClickListener itemClickListener;

    public VendorPastOrderViewHolder(View itemView)
    {
        super(itemView);
        order_date = (TextView) itemView.findViewById(R.id.darte_order);
        back_image = (ImageView) itemView.findViewById(R.id.imageView4);
        order_price = (TextView) itemView.findViewById(R.id.order_price);
        orderList = (TextView) itemView.findViewById(R.id.order_list);
        transaction_id = (TextView) itemView.findViewById(R.id.transaction_id);
        customer_id = (TextView) itemView.findViewById(R.id.customer_id);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
